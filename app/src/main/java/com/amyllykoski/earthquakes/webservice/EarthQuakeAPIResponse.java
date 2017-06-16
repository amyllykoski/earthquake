package com.amyllykoski.earthquakes.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class EarthQuakeAPIResponse {
  @SerializedName("type")
  private Object featureCollection;
  @SerializedName("metadata")
  private Object metaData;
  @SerializedName("features")
  @Expose
  private List<EarthQuakeAPIRecord> records;

  public List<EarthQuakeAPIRecord> getRecords() {
    return records;
  }
}
