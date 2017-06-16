package com.amyllykoski.earthquakes.model;

public class Magnitude {

  private String mMagnitude;

  public Magnitude(final String magnitude) {
    mMagnitude = magnitude;
  }

  public String getmMagnitude() {
    return mMagnitude;
  }

  @Override
  public String toString() {
    return "Magnitude{" +
        "mMagnitude='" + mMagnitude + '\'' +
        '}';
  }
}
