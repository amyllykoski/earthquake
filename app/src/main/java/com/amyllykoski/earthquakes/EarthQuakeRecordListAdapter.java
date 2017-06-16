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

import com.amyllykoski.earthquakes.dummy.DummyContent;

import java.util.List;

class EarthQuakeRecordListAdapter
    extends RecyclerView.Adapter<EarthQuakeRecordListAdapter.ViewHolder> {

  private final List<DummyContent.DummyItem> mValues;
  private boolean mTwoPane;
  private FragmentManager mFragmentManager;

  EarthQuakeRecordListAdapter(final FragmentManager fragmentManager, List<DummyContent.DummyItem> items) {
    mValues = items;
    mFragmentManager = fragmentManager;
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
    holder.mIdView.setText(mValues.get(position).id);
    holder.mContentView.setText(mValues.get(position).content);

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mTwoPane) {
          Bundle arguments = new Bundle();
          arguments.putString(EarthQuakeRecordDetailFragment.ARG_ITEM_ID, holder.mItem.id);
          EarthQuakeRecordDetailFragment fragment = new EarthQuakeRecordDetailFragment();
          fragment.setArguments(arguments);
          mFragmentManager.beginTransaction()
              .replace(R.id.earthquakerecord_detail_container, fragment)
              .commit();
        } else {
          Context context = v.getContext();
          Intent intent = new Intent(context, EarthQuakeRecordDetailActivity.class);
          intent.putExtra(EarthQuakeRecordDetailFragment.ARG_ITEM_ID, holder.mItem.id);

          context.startActivity(intent);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    final View mView;
    final TextView mIdView;
    final TextView mContentView;
    DummyContent.DummyItem mItem;

    ViewHolder(View view) {
      super(view);
      mView = view;
      mIdView = (TextView) view.findViewById(R.id.id);
      mContentView = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + mContentView.getText() + "'";
    }
  }
}