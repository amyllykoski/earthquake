package com.amyllykoski.earthquakes.webservice;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class EarthQuakeAPIResponse {

  @SerializedName("features")
  @Expose
  private List<EarthQuakeAPIRecord> mRecords = new ArrayList<>();

  @NonNull List<EarthQuakeAPIRecord> getRecords() {
    if (mRecords == null) mRecords = new ArrayList<>();
    return mRecords;
  }
}
