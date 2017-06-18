package com.amyllykoski.earthquakes;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amyllykoski.earthquakes.model.EarthQuakeRecord;
import com.google.gson.Gson;

public class EarthQuakeRecordDetailFragment extends Fragment {
  public static final String ARG_ITEM_ID = "item_id";
  private EarthQuakeRecord mEarthQuakeRecord;

  public EarthQuakeRecordDetailFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!getArguments().containsKey(ARG_ITEM_ID)) {
      return;
    }

    Gson gson = new Gson();
    mEarthQuakeRecord = gson.fromJson((String) getArguments().get(ARG_ITEM_ID),
        EarthQuakeRecord.class);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.earthquakerecord_detail, container, false);
    if (mEarthQuakeRecord != null) {
      ((TextView) rootView.findViewById(R.id.detail_time))
          .setText(mEarthQuakeRecord.mTime.getTime(getString(R.string.date_format_full)));
      ((TextView) rootView.findViewById(R.id.detail_place))
          .setText(mEarthQuakeRecord.mPlace.get());
      ((TextView) rootView.findViewById(R.id.detail_tsunami))
          .setText(mEarthQuakeRecord.mIsTsunamiPotential ?
              getString(R.string.is_tsunami_yes) : getString(R.string.is_tsunami_no));
      ((TextView) rootView.findViewById(R.id.detail_magnitude))
          .setText(mEarthQuakeRecord.mMagnitude.get());
      ((TextView) rootView.findViewById(R.id.detail_latitude))
          .setText(mEarthQuakeRecord.mCoordinates.getLatitude());
      ((TextView) rootView.findViewById(R.id.detail_longitude))
          .setText(mEarthQuakeRecord.mCoordinates.getLongitude());
    }
    setAppBarTitle();
    return rootView;
  }

  private void setAppBarTitle() {
    Activity activity = getActivity();
    CollapsingToolbarLayout appBarLayout =
        (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
    if (appBarLayout != null) {
      appBarLayout.setTitle(mEarthQuakeRecord
          .mTime.getTime(getString(R.string.date_format_full)));
    }
  }
}
