/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import com.amyllykoski.earthquakes.model.EarthQuakeRecord;

import java.util.List;

public interface EarthResponseReceiver {
  void onSuccess(final List<EarthQuakeRecord> records);
  void onFailure(final String message, final String details);
}
