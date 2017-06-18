package com.amyllykoski.earthquakes;

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

import com.amyllykoski.earthquakes.webservice.EarthQuakeClient;

public class EarthQuakeRecordListActivity extends AppCompatActivity implements
    MinimumMagnitudeSettingListener {

  private int mMinMagnitude = 30; // Divided by 10 before querying

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_earthquakerecord_list);

    setupToolbar();
    setupFAB();
//    setupRecyclerView((RecyclerView) findViewById(R.id.earthquakerecord_list));
  }

  @Override
  protected void onResume() {
    super.onResume();
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

      default:
        return super.onOptionsItemSelected(item);
    }
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
    EarthQuakeClient client = new EarthQuakeClient();
    RecyclerView r = (RecyclerView) findViewById(R.id.earthquakerecord_list);
    r.addItemDecoration(new DividerItemDecoration(r.getContext(),
        new LinearLayoutManager(this).getOrientation()));
    EarthQuakeRecordListAdapter a = new EarthQuakeRecordListAdapter();
    r.setAdapter(a);
    client.execute(a, mMinMagnitude / 10.0 + "");
  }

  private void setupToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());
  }
}
