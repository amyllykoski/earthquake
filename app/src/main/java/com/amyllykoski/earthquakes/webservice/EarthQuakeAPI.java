/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * HTTP GET interface to USGS earthquake web service. The query structure is
 * query?&format=geojson&minmagnitude=3.0&starttime=2017-06-18T14:55:08-07:00&orderby=time
 */
interface EarthQuakeAPI {
  @GET("/fdsnws/event/1/query?")
  Call<EarthQuakeAPIResponse> getEarthQuakes(
      @Query("format") String format,
      @Query("minmagnitude") String minMagnitude,
      @Query("starttime") String startTime,
      @Query("orderby") String orderBy);
}
