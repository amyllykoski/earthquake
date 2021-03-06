/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

/**
 * Value object to hold coordinate (lat/lon) information.
 */
public class Coordinates {

  private String mLatitude;
  private String mLongitude;

  /**
   * Constructs a coordinate object with given lat/lon values.
   *
   * @param latitude  The latitude.
   * @param longitude The longitude.
   */
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
