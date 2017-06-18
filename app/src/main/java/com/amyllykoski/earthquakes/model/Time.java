package com.amyllykoski.earthquakes.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Time {

  private String mMillis;

  public Time(final String millis) {
    mMillis = millis;
  }

  @SuppressWarnings("unused")
  public String getMillis() {
    return mMillis;
  }

  public String getTime(String timeFormat) {
    SimpleDateFormat formatter =
        new SimpleDateFormat(timeFormat == null ? "hh:mm aa" : timeFormat,
            Locale.getDefault());
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(Long.parseLong(mMillis));
    return formatter.format(calendar.getTime());
  }

  @Override
  public String toString() {
    return "Time{" +
        "mMillis='" + mMillis + '\'' +
        '}';
  }
}
