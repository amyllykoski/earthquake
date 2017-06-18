package com.amyllykoski.earthquakes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.amyllykoski.earthquakes.webservice.EarthQuakeClient;

public class EarthQuakeRecordListActivity extends AppCompatActivity {

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
        Log.d("Paska", "clicked");
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
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
    client.execute(a, "3.1");
  }

  private void setupToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());
  }
}
