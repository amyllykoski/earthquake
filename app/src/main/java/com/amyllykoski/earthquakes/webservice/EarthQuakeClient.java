package com.amyllykoski.earthquakes.webservice;

import android.support.annotation.NonNull;
import android.util.Log;

import com.amyllykoski.earthquakes.EarthQuakeRecordListAdapter;
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

public class EarthQuakeClient implements Callback<EarthQuakeAPIResponse> {

  private static final String BASE_URL = "https://earthquake.usgs.gov/";
  private static final String FORMAT = "geojson";
  private static final String ORDER_BY_TIME = "time";
  private final String TAG = this.getClass().getSimpleName();

  private EarthQuakeRecordListAdapter mAdapter;

  public void execute(final EarthQuakeRecordListAdapter adapter,
                      final String minMagnitude) {
    mAdapter = adapter;

    Gson gson = new GsonBuilder()
        .setLenient()
        .registerTypeAdapter(EarthQuakeAPIRecord.class,
            new EarthQuakeRecordDeserializer())
        .create();

    OkHttpClient.Builder httpClient = setLogLevel(HttpLoggingInterceptor.Level.BASIC);
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient.build())
        .build();

    EarthquakeAPI earthquakeAPI = retrofit.create(EarthquakeAPI.class);
    Call<EarthQuakeAPIResponse> call =
        earthquakeAPI.getEarthQuakes(FORMAT, minMagnitude, startTime(), ORDER_BY_TIME);
    call.enqueue(this);
  }

  private String startTime() {
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ")
        .withLocale(Locale.getDefault());
    DateTime start = DateTime.now().minusDays(1);
    return formatter.print(start);
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public void onResponse(@NonNull Call<EarthQuakeAPIResponse> call,
                         @NonNull Response<EarthQuakeAPIResponse> response) {
    if (response.isSuccessful()) {
      updateAdapter(response);
    } else {
      if (response.errorBody() != null)
        Log.e(TAG, response.errorBody().toString());
    }
  }

  @SuppressWarnings("ConstantConditions")
  private void updateAdapter(@NonNull Response<EarthQuakeAPIResponse> response) {
    ArrayList<EarthQuakeRecord> items = new ArrayList<>();
    List<EarthQuakeAPIRecord> earthQuakeRecordList;
    if (response.body() == null) {
      earthQuakeRecordList = new ArrayList<>();
    } else {
      earthQuakeRecordList = response.body().getRecords();
    }

    for (EarthQuakeAPIRecord record : earthQuakeRecordList) {
      items.add(convert(record));
    }
    mAdapter.setItems(items);
  }

  private EarthQuakeRecord convert(EarthQuakeAPIRecord rec) {
    return new EarthQuakeRecord(
        new Time(rec.getTime()),
        new Magnitude(rec.getMagnitude()),
        new Place(rec.getPlace()),
        !rec.getTsunami().equals("0"),
        new Coordinates(rec.getLatitude(), rec.getLongitude()));
  }

  @Override
  public void onFailure(@NonNull Call<EarthQuakeAPIResponse> call, @NonNull Throwable t) {
    Log.e(TAG, "Failure in getting earthquake records: " + t.getLocalizedMessage());
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
