/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amyllykoski.earthquakes.R;

public class MagnitudeSettingDialog extends DialogFragment {
  private static final String KEY_INITIAL_VALUE = "INITIAL_VALUE";
  public static final String KEY_CURRENT_VALUE = "CURRENT_VALUE";
  private static final int DEFAULT_VALUE = 30;
  private static final int NOT_SET = -1;

  private int mCurrentValue = NOT_SET;

  private MinimumMagnitudeSettingListener mListener;

  public static MagnitudeSettingDialog newInstance(int initialValue) {
    Bundle args = new Bundle();
    MagnitudeSettingDialog dialog = new MagnitudeSettingDialog();
    args.putInt(KEY_INITIAL_VALUE, initialValue);
    dialog.setArguments(args);
    return dialog;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      mCurrentValue = savedInstanceState.getInt(KEY_CURRENT_VALUE);
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(R.string.dialog_set_minimum_magnitude)
        .setView(R.layout.magnitude_setting)
        .setPositiveButton(R.string.dialog_set_button, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            mListener.onMinimumMagnitudeSet(mCurrentValue);
          }
        })
        .setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
          }
        });
    return builder.create();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    try {
      mListener = (MinimumMagnitudeSettingListener) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString()
          + " must implement MinimumMagnitudeSettingListener!");
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    SeekBar seekBar = (SeekBar) getDialog().findViewById(R.id.seekbar);
    final TextView currentValue = (TextView) getDialog().findViewById(R.id.current_value);
    seekBar.setMax(100);
    seekBar.setKeyProgressIncrement(1);
    mCurrentValue = mCurrentValue == NOT_SET ?
        getArguments().getInt(KEY_INITIAL_VALUE, DEFAULT_VALUE) : mCurrentValue;
    seekBar.setProgress(mCurrentValue);
    currentValue.setText(String.format("%s", mCurrentValue / 10.0));

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mCurrentValue = progress;
        currentValue.setText(String.format("%s", progress / 10.0));
      }

      public void onStartTrackingTouch(SeekBar arg0) {
      }

      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(KEY_CURRENT_VALUE, mCurrentValue);
  }
}
