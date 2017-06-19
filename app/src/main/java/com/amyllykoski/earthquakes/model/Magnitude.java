/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.model;

/**
 * Value object to hold the magnitude information.
 */
public class Magnitude {

  private String mMagnitude;

  /**
   * Constructs the magnitude object with given magnitude value.
   *
   * @param magnitude The magnitude.
   */
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
