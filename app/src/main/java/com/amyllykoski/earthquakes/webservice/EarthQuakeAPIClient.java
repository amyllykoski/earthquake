/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import android.support.annotation.NonNull;

import com.amyllykoski.earthquakes.model.Coordinates;
import com.amyllykoski.earthquakes.model.EarthQuakeRecord;
import com.amyllykoski.earthquakes.model.Magnitude;
import com.amyllykoski.earthquakes.model.Place;
import com.amyllykoski.earthquakes.model.Time;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EarthQuakeAPIClient implements Callback<EarthQuakeAPIResponse> {

  private static final String BASE_URL = "https://earthquake.usgs.gov/";
  private static final String FORMAT = "geojson";
  private static final String ORDER_BY_TIME = "time";
  private static final String WEB_FAILURE_MESSAGE = "Failed to communicate with USGS Earthquakes.";

  private EarthResponseReceiver mRecordReceiver;

  void execute(final EarthResponseReceiver recordReceiver,
               final String minMagnitude,
               final String baseURL) {
    mRecordReceiver = recordReceiver;

    Gson gson = new GsonBuilder()
        .setLenient()
        .registerTypeAdapter(EarthQuakeAPIRecord.class,
            new EarthQuakeRecordDeserializer())
        .create();

    OkHttpClient.Builder httpClient = setLogLevel(HttpLoggingInterceptor.Level.BASIC);
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build();

    EarthQuakeAPI earthquakeAPI = retrofit.create(EarthQuakeAPI.class);
    Call<EarthQuakeAPIResponse> call =
        earthquakeAPI.getEarthQuakes(FORMAT, minMagnitude, startTime(), ORDER_BY_TIME);
    call.enqueue(this);
  }

  public void execute(final EarthResponseReceiver recordReceiver,
                      final String minMagnitude) {
    execute(recordReceiver, minMagnitude, BASE_URL);
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onResponse(@NonNull Call<EarthQuakeAPIResponse> call,
                         @NonNull Response<EarthQuakeAPIResponse> response) {
    if (response.isSuccessful()) {
      updateRecordReceiver(response);
    } else {
      if (response.errorBody() != null) {
        mRecordReceiver.onFailure(WEB_FAILURE_MESSAGE, response.errorBody().toString());
      }
    }
  }

  @Override
  public void onFailure(@NonNull Call<EarthQuakeAPIResponse> call, @NonNull Throwable t) {
    mRecordReceiver.onFailure(WEB_FAILURE_MESSAGE, t.getLocalizedMessage());
  }

  @SuppressWarnings("ConstantConditions")
  private void updateRecordReceiver(@NonNull Response<EarthQuakeAPIResponse> response) {
    ArrayList<EarthQuakeRecord> items = new ArrayList<>();
    List<EarthQuakeAPIRecord> earthQuakeRecordList;
    if (response.body() == null) {
      earthQuakeRecordList = new ArrayList<>();
    } else {
      earthQuakeRecordList = response.body().getRecords();
    }

    for (EarthQuakeAPIRecord record : earthQuakeRecordList) {
      items.add(convertFrom(record));
    }
    mRecordReceiver.onSuccess(items);
  }

  private String startTime() {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ")
        .withLocale(Locale.getDefault());
    DateTime start = DateTime.now().minusDays(1);
    return formatter.print(start);
  }

  private EarthQuakeRecord convertFrom(EarthQuakeAPIRecord rec) {
    return new EarthQuakeRecord(
        new Time(rec.getTime()),
        new Magnitude(rec.getMagnitude()),
        new Place(rec.getPlace()),
        !rec.getTsunami().equals("0"),
        new Coordinates(rec.getLatitude(), rec.getLongitude()));
  }

  @NonNull
  private OkHttpClient.Builder setLogLevel(HttpLoggingInterceptor.Level level) {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(level);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(logging);
    return httpClient;
  }
}
