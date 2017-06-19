/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a record of raw (string) data received from the EarthQuakeAPI webservice.
 */
class EarthQuakeAPIRecord {

  private static final String DEFAULT_LAT_LON = "0.0";

  @SuppressWarnings("unused")
  @SerializedName("properties")
  @Expose
  private Properties mProperties;

  @SuppressWarnings("unused")
  @SerializedName("geometry")
  @Expose
  private Geometry mGeometry;

  public String getMagnitude() {
    return mProperties.mMagnitude;
  }

  String getPlace() {
    return mProperties.mPlace;
  }

  String getTime() {
    return mProperties.mTime;
  }

  String getTsunami() {
    return mProperties.mTsunami;
  }

  String getLongitude() {
    return mGeometry.mCoordinates.size() > 0 ?
        mGeometry.mCoordinates.get(0) : DEFAULT_LAT_LON;
  }

  String getLatitude() {
    return mGeometry.mCoordinates.size() > 1 ?
        mGeometry.mCoordinates.get(1) : DEFAULT_LAT_LON;
  }

  private static class Properties {

    @SuppressWarnings("unused")
    @SerializedName("mag")
    @Expose
    private String mMagnitude;

    @SuppressWarnings("unused")
    @SerializedName("place")
    @Expose
    private String mPlace;

    @SuppressWarnings("unused")
    @SerializedName("time")
    @Expose
    private String mTime;

    @SuppressWarnings("unused")
    @SerializedName("tsunami")
    @Expose
    private String mTsunami;

    @Override
    public String toString() {
      return "Properties{" +
          "mMagnitude='" + mMagnitude + '\'' +
          ", mPlace='" + mPlace + '\'' +
          ", mTime='" + mTime + '\'' +
          ", mTsunami='" + mTsunami + '\'' +
          '}';
    }
  }

  private static class Geometry {

    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
    @SerializedName("coordinates")
    @Expose
    private List<String> mCoordinates = new ArrayList<>();

    @Override
    public String toString() {
      return "Geometry{" +
          "mCoordinates=" + mCoordinates +
          '}';
    }
  }

  @Override
  public String toString() {
    return "EarthQuakeAPIRecord{" +
        "properties=" + mProperties.toString() +
        ", geometry=" + mGeometry.toString() +
        '}';
  }
}
