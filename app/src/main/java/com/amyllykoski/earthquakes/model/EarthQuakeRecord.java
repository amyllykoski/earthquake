/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

/**
 * An aggregate root class for the domain classes contained within the model.
 */
public class EarthQuakeRecord {
  public Time mTime;
  public Magnitude mMagnitude;
  public Place mPlace;
  public Boolean mIsTsunamiPotential;
  public Coordinates mCoordinates;

  /**
   * Constructs a EarthQuakeRecord (aggregate root) object, which will be the
   * main vehicle to deliver earthquake information within the app UI.
   *
   * @param time               Event time.
   * @param magnitude          Earthquake magnitude.
   * @param place              Place, where the earthquake took place.
   * @param isTsunamiPotential True if there's a potential for a tsunami.
   * @param coordinates        Coordinates object holding the location information.
   */
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

  @Override
  public String toString() {
    return "EarthQuakeRecord{" +
        "mTime=" + mTime +
        ", mMagnitude=" + mMagnitude +
        ", mPlace=" + mPlace +
        ", mIsTsunamiPotential=" + mIsTsunamiPotential +
        ", mCoordinates=" + mCoordinates +
        '}';
  }
}
