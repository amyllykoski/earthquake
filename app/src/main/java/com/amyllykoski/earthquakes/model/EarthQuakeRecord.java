/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

public class EarthQuakeRecord {
  public Time mTime;
  public Magnitude mMagnitude;
  public Place mPlace;
  public Boolean mIsTsunamiPotential;
  public Coordinates mCoordinates;

  public EarthQuakeRecord(
      final Time time,
      final Magnitude magnitude,
      final Place place,
      final Boolean isTsunamiPotential,
      final Coordinates coordinates
  ) {
    mTime = time;
    mMagnitude = magnitude;
    mPlace = place;
    mIsTsunamiPotential = isTsunamiPotential;
    mCoordinates = coordinates;
  }
}
