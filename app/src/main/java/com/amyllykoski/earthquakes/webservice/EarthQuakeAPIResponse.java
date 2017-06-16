package com.amyllykoski.earthquakes.webservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class EarthQuakeAPIResponse {

  @SerializedName("features")
  @Expose
  private List<EarthQuakeAPIRecord> mRecords = new ArrayList<>();

  List<EarthQuakeAPIRecord> getRecords() {
    if (mRecords == null) mRecords = new ArrayList<>();
    return mRecords;
  }
}
