package com.amyllykoski.earthquakes.model;

public class EarthQuakeRecord {
  private Time mTime;
  private Magnitude mMagnitude;
  private Place mPlace;
  private Boolean mIsTsunamiPotential;
  private Coordinates mCoordinates;

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
