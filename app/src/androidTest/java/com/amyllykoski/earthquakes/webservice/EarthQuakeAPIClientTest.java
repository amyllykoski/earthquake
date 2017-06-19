/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.amyllykoski.earthquakes.model.EarthQuakeRecord;

import net.danlew.android.joda.JodaTimeAndroid;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

public class EarthQuakeAPIClientTest {

  EarthQuakeAPIClient sut;

  private class Helper implements EarthQuakeRecordReceiver {

    private List<EarthQuakeRecord> mRecords;

    @Override
    public void setRecords(List<EarthQuakeRecord> records) {
      mRecords = records;
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
  public void execute() throws Exception {
    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody("hello, world!"));
    server.start();
    HttpUrl baseUrl = server.url("/");
    Helper helper = new Helper();
    sut.execute(helper, "100.0", baseUrl.toString());
  }
}