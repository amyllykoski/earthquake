/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EarthquakeAPI {
  @GET("/fdsnws/event/1/query?")
  Call<EarthQuakeAPIResponse> getEarthQuakes(
      @Query("format") String format,
      @Query("minmagnitude") String minMagnitude,
      @Query("starttime") String startTime,
      @Query("orderby") String orderBy);
}
