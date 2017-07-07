package com.wearapay.brotherweather.net;

import com.google.gson.Gson;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Leo Ren(leo.ren@paypos.ca) on 10/8/15.
 */
final class PPRestResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Gson gson;
    private Type type;

    public PPRestResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String responseStr = value.string();
        System.out.println("responseStr = " + responseStr);
        return gson.fromJson(responseStr, type);

//    PPResultBean result = gson.fromJson(responseStr, PPResultBean.class);
//    if (result.getStatus().equalsIgnoreCase(PPResultBean.SUCCESS)) {
//      if (result.getData() == null) return null;
//      return gson.fromJson(gson.toJson(result.getData()), type);
//    } else {
//      throw new PPCodedException(result.getErrors());
//    }
    }
}