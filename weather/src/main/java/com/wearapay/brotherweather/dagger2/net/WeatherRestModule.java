package com.wearapay.brotherweather.dagger2.net;

import android.content.Context;
import com.google.gson.Gson;
import com.wearapay.brotherweather.BWConts;
import com.wearapay.brotherweather.BuildConfig;
import com.wearapay.brotherweather.api.ICityRestService;
import com.wearapay.brotherweather.api.IGankioRestService;
import com.wearapay.brotherweather.api.IWeatherRestService;
import com.wearapay.brotherweather.net.PPRestConverterFactory;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.io.IOException;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lyz54 on 2017/6/27.
 */
@Module @Singleton public class WeatherRestModule {
  @Provides @Singleton IWeatherRestService provideIWeatherRestService(OkHttpClient client) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BWConts.URL + "/")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .addConverterFactory(PPRestConverterFactory.create())
        .build();
    return retrofit.create(IWeatherRestService.class);
  }

  @Provides @Singleton IGankioRestService provideIGankioRestService(OkHttpClient client) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BWConts.GANKIO_HSOT)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .addConverterFactory(PPRestConverterFactory.create())
        .build();
    return retrofit.create(IGankioRestService.class);
  }

  @Provides @Singleton ICityRestService provideICityRestService(OkHttpClient client) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BWConts.URL_CITY + "/")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .addConverterFactory(PPRestConverterFactory.create())
        .build();
    return retrofit.create(ICityRestService.class);
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(Context context) {
    return getOkHttpClient(context);
  }

  protected OkHttpClient getOkHttpClient(Context context) {
    OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
    okHttpBuilder.cache(new Cache(new File(context.getCacheDir().getAbsolutePath()), 100));
    okHttpBuilder.addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        //
        Request build = builder.build();
        //
        return chain.proceed(build);
      }
    });
    if (BuildConfig.DEBUG) {
      // Log信息拦截器
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

      //设置 Debug Log 模式
      okHttpBuilder.addInterceptor(loggingInterceptor);
    }
    return okHttpBuilder.build();
  }
}
