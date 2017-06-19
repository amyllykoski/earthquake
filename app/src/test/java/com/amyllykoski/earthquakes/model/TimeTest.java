/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class TimeTest {

  private Time sut;

  @Test
  public void shouldReturnCorrectStringAM() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, 2000);
    cal.set(Calendar.MONTH, 7);
    cal.set(Calendar.DATE, 13);
    cal.set(Calendar.HOUR_OF_DAY, 11);
    cal.set(Calendar.MINUTE, 15);
    cal.set(Calendar.SECOND, 12);
    cal.set(Calendar.MILLISECOND, 123);
    sut = new Time(cal.getTimeInMillis() + "");
    assertEquals("11:15 AM 08/13", sut.getTime(null));
  }

  @Test
  public void shouldReturnCorrectStringPM() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, 2000);
    cal.set(Calendar.MONTH, 7);
    cal.set(Calendar.DATE, 13);
    cal.set(Calendar.HOUR_OF_DAY, 21);
    cal.set(Calendar.MINUTE, 20);
    cal.set(Calendar.SECOND, 12);
    cal.set(Calendar.MILLISECOND, 123);

    sut = new Time(cal.getTimeInMillis() + "");
    assertEquals("09:20 PM 08/13", sut.getTime(null));
  }


  @Test
  public void shouldApplyCustomFormatter() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, 2000);
    cal.set(Calendar.MONTH, 7);
    cal.set(Calendar.DATE, 13);
    cal.set(Calendar.HOUR_OF_DAY, 21);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 12);
    cal.set(Calendar.MILLISECOND, 123);

    sut = new Time(cal.getTimeInMillis() + "");
    assertEquals("2000-08-13 09:59 PM", sut.getTime("yyyy-MM-dd hh:mm aa"));
  }
}