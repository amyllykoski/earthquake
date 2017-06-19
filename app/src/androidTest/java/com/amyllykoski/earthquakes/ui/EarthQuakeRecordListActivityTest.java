/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.amyllykoski.earthquakes.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EarthQuakeRecordListActivityTest {

  @Rule
  public ActivityTestRule<EarthQuakeRecordListActivity> mActivityTestRule = new ActivityTestRule<>(EarthQuakeRecordListActivity.class);

  @Test
  public void earthQuakeRecordListActivityTest() {
    ViewInteraction textView = onView(
        allOf(withText("EarthQuakes"),
            childAtPosition(
                allOf(withId(R.id.toolbar),
                    childAtPosition(
                        withId(R.id.app_bar),
                        0)),
                0),
            isDisplayed()));
    textView.check(matches(withText("EarthQuakes")));

    ViewInteraction textView2 = onView(
        allOf(withId(R.id.action_refresh), withContentDescription("Refresh List"),
            childAtPosition(
                childAtPosition(
                    withId(R.id.toolbar),
                    1),
                0),
            isDisplayed()));
    textView2.check(matches(isDisplayed()));

    ViewInteraction textView3 = onView(
        allOf(withId(R.id.action_set_magnitude), withContentDescription("Set Minimum Magnitude"),
            childAtPosition(
                childAtPosition(
                    withId(R.id.toolbar),
                    1),
                1),
            isDisplayed()));
    textView3.check(matches(isDisplayed()));

    ViewInteraction linearLayout = onView(
        allOf(childAtPosition(
            allOf(withId(R.id.frameLayout),
                childAtPosition(
                    IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                    1)),
            0),
            isDisplayed()));
    linearLayout.check(matches(isDisplayed()));

  }

  private static Matcher<View> childAtPosition(
      final Matcher<View> parentMatcher, final int position) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
      }

      @Override
      public boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent)
            && view.equals(((ViewGroup) parent).getChildAt(position));
      }
    };
  }
}
