/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amyllykoski.earthquakes.R;
import com.amyllykoski.earthquakes.model.EarthQuakeRecord;
import com.amyllykoski.earthquakes.webservice.EarthQuakeResponseReceiver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A RecyclerView adapter for EarthQuakeRecords.
 */
class EarthQuakeRecordListAdapter
    extends RecyclerView.Adapter<EarthQuakeRecordListAdapter.ViewHolder>
    implements EarthQuakeResponseReceiver {

  private RecyclerView mNonEmptyView;
  private TextView mEmptyView;

  private List<EarthQuakeRecord> mRecords = new ArrayList<>();

  /**
   * Constructs the adapter. The empty list / failure case is handled by
   * changing the visibility of the emptyView and nonEmptyView.
   *
   * @param nonEmptyView This view will display the list of records.
   * @param emptyView    This view will be displayed in case there's no data or
   *                     the query returned a failure.
   */
  EarthQuakeRecordListAdapter(final RecyclerView nonEmptyView, final TextView emptyView) {
    mNonEmptyView = nonEmptyView;
    mEmptyView = emptyView;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.earthquakerecord_list_content, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mDateOccurred.setText(mRecords.get(position).mTime.getTime(null));
    holder.mPlaceOccurred.setText(mRecords.get(position).mPlace.get());
    holder.mMagnitude.setText(mRecords.get(position).mMagnitude.get());

    final int pos = position;
    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, EarthQuakeRecordDetailActivity.class);
        Gson gson = new Gson();
        intent.putExtra(EarthQuakeRecordDetailFragment.ARG_ITEM_ID,
            gson.toJson(mRecords.get(pos)));
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mRecords.size();
  }

  @Override
  public void onSuccess(List<EarthQuakeRecord> records) {
    mRecords = records;
    notifyDataSetChanged();
    updateListVisibility(records);
  }

  @Override
  public void onFailure(final String message, final String details) {
    updateListVisibility(new ArrayList<EarthQuakeRecord>());
    mEmptyView.setText(message);
  }

  private void updateListVisibility(List<EarthQuakeRecord> items) {
    if (items.isEmpty()) {
      mNonEmptyView.setVisibility(View.GONE);
      mEmptyView.setVisibility(View.VISIBLE);
    } else {
      mNonEmptyView.setVisibility(View.VISIBLE);
      mEmptyView.setVisibility(View.GONE);
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    final View mView;
    final TextView mDateOccurred;
    final TextView mPlaceOccurred;
    final TextView mMagnitude;

    ViewHolder(View view) {
      super(view);
      mView = view;
      mDateOccurred = (TextView) view.findViewById(R.id.date_occurred);
      mPlaceOccurred = (TextView) view.findViewById(R.id.place_occurred);
      mMagnitude = (TextView) view.findViewById(R.id.magnitude);
    }
  }
}