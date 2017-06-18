package com.amyllykoski.earthquakes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amyllykoski.earthquakes.webservice.EarthQuakeClient;

import static com.amyllykoski.earthquakes.MagnitudeSettingDialog.*;

public class EarthQuakeRecordListActivity extends AppCompatActivity implements
    MinimumMagnitudeSettingListener {

  private int mMinMagnitude = 30; // Divided by 10 before querying

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_earthquakerecord_list);
    if (savedInstanceState != null) {
      mMinMagnitude = savedInstanceState.getInt(KEY_CURRENT_VALUE);
    }
    setupToolbar();
    setupFAB();
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
            newInstance(mMinMagnitude);
        dialog.show(getSupportFragmentManager(), "MagnitudeSetting");
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(KEY_CURRENT_VALUE, mMinMagnitude);
  }

  @Override
  public void onMinimumMagnitudeSet(int magnitude) {
    mMinMagnitude = magnitude;
    load();
  }

  private void setupFAB() {
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
        load();
      }
    });
  }

  private void load() {
    View view = findViewById(R.id.earthquakerecord_list);
    EarthQuakeClient client = new EarthQuakeClient();
    RecyclerView earthQuakeList = (RecyclerView) findViewById(R.id.earthquakerecord_list);
    earthQuakeList.addItemDecoration(new DividerItemDecoration(earthQuakeList.getContext(),
        new LinearLayoutManager(this).getOrientation()));
    EarthQuakeRecordListAdapter adapter = new EarthQuakeRecordListAdapter(
        (RecyclerView) findViewById(R.id.earthquakerecord_list),
        (TextView) findViewById(R.id.empty_view));
    earthQuakeList.setAdapter(adapter);
    if (!isConnectedToNetwork()) {
      Snackbar.make(view, "No network connection.", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
    } else {
      client.execute(adapter, mMinMagnitude / 10.0 + "");
    }
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
