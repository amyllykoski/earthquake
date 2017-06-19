/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.ui;

/**
 * Interface the MagnitudeSettingDialog calls when user has set a new minimum
 * magnitude value.
 */
interface MinimumMagnitudeSettingListener {
  void onMinimumMagnitudeSet(final int magnitude);
}
