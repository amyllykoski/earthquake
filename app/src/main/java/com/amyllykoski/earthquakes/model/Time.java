package com.amyllykoski.earthquakes.model;

public class Time {

  private String mMillis;

  public Time(final String millis) {
    mMillis = millis;
  }

  public String getMillis() {
    return mMillis;
  }

  @Override
  public String toString() {
    return "Time{" +
        "mMillis='" + mMillis + '\'' +
        '}';
  }
}
