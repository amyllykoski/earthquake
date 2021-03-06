/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.amyllykoski.earthquakes.R;
import com.amyllykoski.earthquakes.webservice.EarthQuakeAPIClient;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Main activity from which the application execution starts. Shows a list of
 * earthquake events obtained from the web.
 */
public class EarthQuakeRecordListActivity extends AppCompatActivity implements
    MinimumMagnitudeSettingListener {

  public static final int DEFAULT_MINIMUM_MAGNITUDE = 30;
  private int mMinMagnitude = DEFAULT_MINIMUM_MAGNITUDE; // Divided by 10 before querying

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_earthquakerecord_list);
    JodaTimeAndroid.init(this);
    setupToolbar();
    if (savedInstanceState != null) {
      mMinMagnitude = savedInstanceState.getInt(MagnitudeSettingDialog.KEY_CURRENT_VALUE);
    }
    load();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.action_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_set_magnitude:
        MagnitudeSettingDialog dialog =
            MagnitudeSettingDialog.newInstance(mMinMagnitude);
        dialog.show(getSupportFragmentManager(), "MagnitudeSetting");
        return true;
      case R.id.action_refresh:
        load();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(MagnitudeSettingDialog.KEY_CURRENT_VALUE, mMinMagnitude);
  }

  @Override
  public void onMinimumMagnitudeSet(int magnitude) {
    mMinMagnitude = magnitude;
    load();
  }

  private void load() {
    RecyclerView earthQuakeList = (RecyclerView) findViewById(R.id.earthquakerecord_list);
    if (!isConnectedToNetwork()) {
      Snackbar.make(earthQuakeList, R.string.no_network_connection, Snackbar.LENGTH_LONG)
          .show();
      return;
    }

    EarthQuakeRecordListAdapter adapter = setEarthQuakeRecordListAdapter(earthQuakeList);
    queryWebService(earthQuakeList, adapter);
  }

  private void queryWebService(RecyclerView earthQuakeList, EarthQuakeRecordListAdapter adapter) {
    EarthQuakeAPIClient client = new EarthQuakeAPIClient();
    client.execute(adapter, String.format("%s", mMinMagnitude / 10.0));
    Snackbar.make(earthQuakeList, R.string.refreshing_list, Snackbar.LENGTH_LONG).show();
  }

  @NonNull
  private EarthQuakeRecordListAdapter setEarthQuakeRecordListAdapter(RecyclerView earthQuakeList) {
    earthQuakeList.addItemDecoration(new DividerItemDecoration(earthQuakeList.getContext(),
        new LinearLayoutManager(this).getOrientation()));
    EarthQuakeRecordListAdapter adapter = new EarthQuakeRecordListAdapter(
        (RecyclerView) findViewById(R.id.earthquakerecord_list),
        (TextView) findViewById(R.id.empty_view));
    earthQuakeList.setAdapter(adapter);
    return adapter;
  }

  private void setupToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());
  }

  private boolean isConnectedToNetwork() {
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnected();
  }
}
