/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Time {

  private static final String DEFAULT_FORMAT = "hh:mm aa MM/dd";
  private String mMillis;

  public Time(final String millis) {
    mMillis = millis;
  }

  public String getTime(String timeFormat) {
    SimpleDateFormat formatter = new SimpleDateFormat(timeFormat == null ?
        DEFAULT_FORMAT : timeFormat, Locale.getDefault());

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(Long.parseLong(mMillis));
    return formatter.format(calendar.getTime());
  }
}
