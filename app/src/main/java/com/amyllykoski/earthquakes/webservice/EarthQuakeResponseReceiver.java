/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import com.amyllykoski.earthquakes.model.EarthQuakeRecord;

import java.util.List;

/**
 * An interface which must be implemented by the webservice clients.
 */
public interface EarthQuakeResponseReceiver {
  /**
   * In case the query returns successfully, the earthquake records are parsed
   * and delivered as a list.
   *
   * @param records Earthquake records received from the web query.
   */
  void onSuccess(final List<EarthQuakeRecord> records);

  /**
   * In case the query fails, the user readable message and details are returned
   * to the caller.
   *
   * @param message User readable message explaining the failure.
   * @param details More detailed information about the failure.
   */
  void onFailure(final String message, final String details);
}
