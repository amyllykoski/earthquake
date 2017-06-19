/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.amyllykoski.earthquakes.model.EarthQuakeRecord;

import net.danlew.android.joda.JodaTimeAndroid;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class EarthQuakeAPIClientTest {

  private String okResponse = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1497894476000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?&format=geojson&minmagnitude=4.9&starttime=2017-06-18T10:28:55-07:00&orderby=time\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.5.8\",\"count\":3},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":5.8,\"place\":\"139km WNW of Havelu, Tonga\",\"time\":1497863743570,\"updated\":1497883591040,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20009nju\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20009nju&format=geojson\",\"felt\":4,\"cdi\":3.1,\"mmi\":3.06,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":519,\"net\":\"us\",\"code\":\"20009nju\",\"ids\":\",us20009nju,\",\"sources\":\",us,\",\"types\":\",dyfi,geoserve,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":5.96,\"rms\":1.48,\"gap\":51,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 5.8 - 139km WNW of Havelu, Tonga\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-176.4767,-20.7303,263.52]},\"id\":\"us20009nju\"},\n" +
      "{\"type\":\"Feature\",\"properties\":{\"mag\":4.9,\"place\":\"94km NNE of Hihifo, Tonga\",\"time\":1497856388400,\"updated\":1497858384040,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20009njc\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20009njc&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":369,\"net\":\"us\",\"code\":\"20009njc\",\"ids\":\",us20009njc,\",\"sources\":\",us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":2.081,\"rms\":0.89,\"gap\":83,\"magType\":\"mb\",\"type\":\"earthquake\",\"title\":\"M 4.9 - 94km NNE of Hihifo, Tonga\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-173.5304,-15.1206,10]},\"id\":\"us20009njc\"},\n" +
      "{\"type\":\"Feature\",\"properties\":{\"mag\":5.1,\"place\":\"113km SSE of Raoul Island, New Zealand\",\"time\":1497811635790,\"updated\":1497812787040,\"tz\":-720,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20009ngx\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20009ngx&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":400,\"net\":\"us\",\"code\":\"20009ngx\",\"ids\":\",us20009ngx,\",\"sources\":\",us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":1.048,\"rms\":0.72,\"gap\":130,\"magType\":\"mb\",\"type\":\"earthquake\",\"title\":\"M 5.1 - 113km SSE of Raoul Island, New Zealand\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-177.5208,-30.2347,10]},\"id\":\"us20009ngx\"}],\"bbox\":[-177.5208,-30.2347,10,-173.5304,-15.1206,263.52]}";

  private String brokenJSON = "{\"type\":\"FeatureCollection\",";

  private String emptyResponse = "{}";

  EarthQuakeAPIClient sut;

  private class Helper implements EarthResponseReceiver {

    private List<EarthQuakeRecord> mRecords;

    @Override
    public void setRecords(List<EarthQuakeRecord> records) {
      mRecords = records;
    }

    @Override
    public void onFailure(final String message, final String details) {
      Log.d("Paska", message);
      Log.d("Paska", details);
    }

    public List<EarthQuakeRecord> getRecords() {
      return mRecords;
    }
  }

  @Before
  public void setUp() throws Exception {
    Context appContext = InstrumentationRegistry.getTargetContext();
    JodaTimeAndroid.init(appContext);
    sut = new EarthQuakeAPIClient();
  }

  @Test
  public void should_receiveResponseCorrectly() throws Exception {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody(okResponse));
    server.start();
    HttpUrl baseUrl = server.url("/");
    Helper helper = new Helper();
    sut.execute(helper, "100.0", baseUrl.toString());
  }

  @Test
  public void should_receiveOnFailureDueToBrokenJSON() throws Exception {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody(brokenJSON));
    server.start();
    HttpUrl baseUrl = server.url("/");
    Helper helper = new Helper();
    sut.execute(helper, "100.0", baseUrl.toString());
  }

  @Test
  public void should_receiveOnFailureDueToEmptyJSON() throws Exception {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody(emptyResponse));
    server.start();
    HttpUrl baseUrl = server.url("/");
    Helper helper = new Helper();
    sut.execute(helper, "100.0", baseUrl.toString());
  }
}