package com.amyllykoski.earthquakes;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

  private List<EarthQuakeRecord> mValues = new ArrayList<>();
  private boolean mTwoPane;
  private FragmentManager mFragmentManager;

  EarthQuakeRecordListAdapter(final FragmentManager fragmentManager) {
    mFragmentManager = fragmentManager;
  }

  public void setItems(List<EarthQuakeRecord> items) {
    mValues = items;
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.earthquakerecord_list_content, parent, false);
    mTwoPane = view.findViewById(R.id.earthquakerecord_detail_container) != null;
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = mValues.get(position);
    holder.mDateOccurred.setText(mValues.get(position).mTime.getMillis());
    holder.mPlaceOccurred.setText(mValues.get(position).mPlace.get());
    holder.mMagnitude.setText(mValues.get(position).mMagnitude.get());

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mTwoPane) {
          handleTwoPane();
        } else {
          handleOnePane(v);
        }
      }

      private void handleOnePane(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, EarthQuakeRecordDetailActivity.class);
        intent.putExtra(EarthQuakeRecordDetailFragment.ARG_ITEM_ID, holder.mItem.mTime.toString());

        context.startActivity(intent);
      }

      private void handleTwoPane() {
        Bundle arguments = new Bundle();
        arguments.putString(EarthQuakeRecordDetailFragment.ARG_ITEM_ID, holder.mItem.mTime.toString());
        EarthQuakeRecordDetailFragment fragment = new EarthQuakeRecordDetailFragment();
        fragment.setArguments(arguments);
        mFragmentManager.beginTransaction()
            .replace(R.id.earthquakerecord_detail_container, fragment)
            .commit();
      }
    });
  }

  @Override
  public int getItemCount() {
    return mValues.size();
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

    @Override
    public String toString() {
      return "ViewHolder{" +
          "mDateOccurred=" + mDateOccurred.getText() +
          ", mPlaceOccurred=" + mPlaceOccurred.getText() +
          ", mMagnitude=" + mMagnitude.getText() +
          '}';
    }
  }
}