package com.amyllykoski.earthquakes.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class EarthQuakeAPIRecord {

  @SuppressWarnings("unused")
  @SerializedName("properties")
  @Expose
  private Properties mProperties;

  @SuppressWarnings("unused")
  @SerializedName("geometry")
  @Expose
  private Geometry mGeometry;

  @Override
  public String toString() {
    return "EarthQuakeAPIRecord{" +
        "properties=" + mProperties.toString() +
        ", geometry=" + mGeometry.toString() +
        '}';
  }

  private static class Properties {

    @SuppressWarnings("unused")
    @SerializedName("mag")
    @Expose
    private String mMagnitude;

    @Override
    public String toString() {
      return "Properties{" +
          "mMagnitude='" + mMagnitude + '\'' +
          ", mPlace='" + mPlace + '\'' +
          ", mTime='" + mTime + '\'' +
          ", mTsunami='" + mTsunami + '\'' +
          '}';
    }

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
  }

  private static class Geometry {

    @Override
    public String toString() {
      return "Geometry{" +
          "mCoordinates=" + mCoordinates +
          '}';
    }

    @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
    @SerializedName("coordinates")
    @Expose
    private List<String> mCoordinates = new ArrayList<>();
  }
}
