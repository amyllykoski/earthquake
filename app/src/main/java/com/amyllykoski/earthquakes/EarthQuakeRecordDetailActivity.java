package com.amyllykoski.earthquakes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class EarthQuakeRecordDetailActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_earthquakerecord_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    if (savedInstanceState == null) {
      Bundle arguments = new Bundle();
      arguments.putString(EarthQuakeRecordDetailFragment.ARG_ITEM_ID,
          getIntent().getStringExtra(EarthQuakeRecordDetailFragment.ARG_ITEM_ID));
      EarthQuakeRecordDetailFragment fragment = new EarthQuakeRecordDetailFragment();
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.earthquakerecord_detail_container, fragment)
          .commit();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      navigateUpTo(new Intent(this, EarthQuakeRecordListActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
