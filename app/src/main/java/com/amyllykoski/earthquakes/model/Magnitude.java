/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

public class Magnitude {

  private String mMagnitude;

  public Magnitude(final String magnitude) {
    mMagnitude = magnitude;
  }

  public String get() {
    return mMagnitude;
  }

  @Override
  public String toString() {
    return "Magnitude{" +
        "mMagnitude='" + mMagnitude + '\'' +
        '}';
  }
}
