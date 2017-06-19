/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Value object to hold time information on when the event happened.
 */
public class Time {

  private static final String DEFAULT_FORMAT = "hh:mm aa MM/dd";
  private String mMillis;

  /**
   * constructs a Time object with given timestamp in millis. This count starts
   * at the Unix Epoch on January 1st, 1970 at UTC.
   *
   * @param millis Timestamp in milliseconds.
   */
  public Time(final String millis) {
    mMillis = millis;
  }

  /**
   * Returns the timestamp converted to given time format. Uses hh:mm aa MM/dd
   * as a default if timeFormat parameter is null.
   *
   * @param timeFormat The timeformat string as understood by SimpleDateFormat.
   * @return Milliseconds converted to the given time format.
   */
  public String getTime(String timeFormat) {
    SimpleDateFormat formatter = new SimpleDateFormat(timeFormat == null ?
        DEFAULT_FORMAT : timeFormat, Locale.getDefault());

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(Long.parseLong(mMillis));
    return formatter.format(calendar.getTime());
  }

  @Override
  public String toString() {
    return "Time{" +
        "mMillis='" + mMillis + '\'' +
        "asTime=" + getTime(null) +
        '}';
  }
}
