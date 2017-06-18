/*
 * Copyright (c) 2017. Antti Myllykoski.
 */

package com.amyllykoski.earthquakes.webservice;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

class EarthQuakeRecordDeserializer implements JsonDeserializer<EarthQuakeAPIRecord> {
  @Override
  public EarthQuakeAPIRecord deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context)
      throws JsonParseException {
    JsonElement content = json.getAsJsonObject();
    return new Gson().fromJson(content, EarthQuakeAPIRecord.class);
  }
}
