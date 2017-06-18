package com.amyllykoski.earthquakes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amyllykoski.earthquakes.model.EarthQuakeRecord;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakeRecordListAdapter
    extends RecyclerView.Adapter<EarthQuakeRecordListAdapter.ViewHolder> {

  private RecyclerView mNonEmptyView;
  private TextView mEmptyView;

  private List<EarthQuakeRecord> mValues = new ArrayList<>();

  public EarthQuakeRecordListAdapter(final RecyclerView nonEmptyView, final TextView emptyView) {
    mNonEmptyView = nonEmptyView;
    mEmptyView = emptyView;
  }

  public void setItems(List<EarthQuakeRecord> items) {
    mValues = items;
    notifyDataSetChanged();
    updateListVisibility(items);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.earthquakerecord_list_content, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = mValues.get(position);
    holder.mDateOccurred.setText(mValues.get(position).mTime.getDate(null));
    holder.mPlaceOccurred.setText(mValues.get(position).mPlace.get());
    holder.mMagnitude.setText(mValues.get(position).mMagnitude.get());

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, EarthQuakeRecordDetailActivity.class);
        intent.putExtra(EarthQuakeRecordDetailFragment.ARG_ITEM_ID, holder.mItem.mTime.toString());
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mValues.size();
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
    EarthQuakeRecord mItem;

    ViewHolder(View view) {
      super(view);
      mView = view;
      mDateOccurred = (TextView) view.findViewById(R.id.date_occurred);
      mPlaceOccurred = (TextView) view.findViewById(R.id.place_occurred);
      mMagnitude = (TextView) view.findViewById(R.id.magnitude);
      mItem = null;
    }
  }
}