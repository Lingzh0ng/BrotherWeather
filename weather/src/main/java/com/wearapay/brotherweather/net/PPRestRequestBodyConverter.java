package com.wearapay.brotherweather.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by Leo Ren(leo.ren@paypos.ca) on 10/8/15.
 */
final class PPRestRequestBodyConverter<T> implements Converter<T, RequestBody> {
  private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private final Gson gson;
  private final TypeAdapter<T> adapter;

  PPRestRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
  }

  @Override
  public RequestBody convert(T value) throws IOException {
    //if (String.class.getName().equals(value.getClass().getName())) {
    //  return RequestBody.create(MediaType.parse("text/plain"), value.toString());
    //}

    Buffer buffer = new Buffer();
    Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
    JsonWriter jsonWriter = gson.newJsonWriter(writer);
    try {
      adapter.write(jsonWriter, value);
      jsonWriter.flush();
    } catch (IOException e) {
      throw new AssertionError(e); // Writing to Buffer does no I/O.
    } finally {
      jsonWriter.close();
    }

    return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
  }
}
