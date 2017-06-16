package com.amyllykoski.earthquakes.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class EarthQuakeAPIRecord {

  @SerializedName("mag")
  @Expose
  private String magnitude;

  @SerializedName("place")
  @Expose
  private String place;

  @SerializedName("time")
  @Expose
  private String time;

  @SerializedName("coordinates")
  @Expose
  private String[] coordinates;

  public String getMagnitude() {
    return magnitude;
  }

  public void setMagnitude(String magnitude) {
    this.magnitude = magnitude;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(String[] coordinates) {
    this.coordinates = coordinates;
  }

  @Override
  public String toString() {
    return "EarthQuakeAPIRecord{" +
        "magnitude='" + magnitude + '\'' +
        ", place='" + place + '\'' +
        ", time='" + time + '\'' +
        ", coordinates=" + Arrays.toString(coordinates) +
        '}';
  }
}
