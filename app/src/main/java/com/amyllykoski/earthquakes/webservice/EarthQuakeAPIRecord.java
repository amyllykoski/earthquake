package com.amyllykoski.earthquakes.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class EarthQuakeAPIRecord {

  @SerializedName("properties")
  @Expose
  private Properties properties;

  @SerializedName("geometry")
  @Expose
  private Geometry geometry;

  @Override
  public String toString() {
    return "EarthQuakeAPIRecord{" +
        "properties=" + properties.toString() +
        ", geometry=" + geometry.toString() +
        '}';
  }

  private static class Properties {

    @SerializedName("mag")
    @Expose
    private String magnitude;

    @Override
    public String toString() {
      return "Properties{" +
          "magnitude='" + magnitude + '\'' +
          ", place='" + place + '\'' +
          ", time='" + time + '\'' +
          ", tsunami='" + tsunami + '\'' +
          '}';
    }

    @SerializedName("place")
    @Expose
    private String place;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("tsunami")
    @Expose
    private String tsunami;
  }

  private static class Geometry {

    @Override
    public String toString() {
      return "Geometry{" +
          "coordinates=" + coordinates +
          '}';
    }

    @SerializedName("coordinates")
    @Expose
    private List<String> coordinates = new ArrayList<>();
  }
}
