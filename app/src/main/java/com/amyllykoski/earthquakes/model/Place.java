package com.amyllykoski.earthquakes.model;

public class Place {

  private String mPlace;

  public Place(final String place) {
    mPlace = place;
  }

  public String getmMagnitude() {
    return mPlace;
  }

  @Override
  public String toString() {
    return "Place{" +
        "mPlace='" + mPlace + '\'' +
        '}';
  }

}
