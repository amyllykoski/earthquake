/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import com.amyllykoski.earthquakes.model.EarthQuakeRecord;

import java.util.List;

public interface EarthQuakeRecordReceiver {
  void setRecords(final List<EarthQuakeRecord> records);
}
