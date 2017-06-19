/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.amyllykoski.earthquakes.R;

public class EarthQuakeRecordDetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_earthquakerecord_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    if (savedInstanceState == null) showEarthQuakeRecordDetails();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showEarthQuakeRecordDetails() {
    Bundle arguments = new Bundle();
    arguments.putString(EarthQuakeRecordDetailFragment.ARG_ITEM_ID,
        getIntent().getStringExtra(EarthQuakeRecordDetailFragment.ARG_ITEM_ID));
    EarthQuakeRecordDetailFragment fragment = new EarthQuakeRecordDetailFragment();
    fragment.setArguments(arguments);
    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.earthquakerecord_detail_container, fragment)
        .commit();
  }
}
