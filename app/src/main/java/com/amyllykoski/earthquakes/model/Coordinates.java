package com.amyllykoski.earthquakes.model;

public class Coordinates {

  private String mLatitude;
  private String mLongitude;

  public Coordinates(final String latitude, final String longitude) {
    mLatitude = latitude;
    mLongitude = longitude;
  }

  public String getLatitude() {
    return mLatitude;
  }

  public String getLongitude() {
    return mLongitude;
  }

  @Override
  public String toString() {
    return "Coordinates{" +
        "mLatitude='" + mLatitude + '\'' +
        ", mLongitude='" + mLongitude + '\'' +
        '}';
  }
}
