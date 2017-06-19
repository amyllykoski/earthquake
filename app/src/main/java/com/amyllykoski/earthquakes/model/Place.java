/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

public class Place {

  private String mPlace;

  public Place(final String place) {
    mPlace = place;
  }

  public String get() {
    return mPlace;
  }

  @Override
  public String toString() {
    return "Place{" +
        "mPlace='" + mPlace + '\'' +
        '}';
  }
}
