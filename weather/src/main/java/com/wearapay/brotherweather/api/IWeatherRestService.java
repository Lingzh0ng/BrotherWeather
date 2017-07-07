package com.wearapay.brotherweather.api;

import com.wearapay.brotherweather.domain.WeatherInfo;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lyz on 2017/6/27.
 */
public interface IWeatherRestService {

  @GET("open/api/weather/json.shtml") Observable<WeatherInfo> getWeatherInfo(@Query("city") String city);

  @GET("open/api/weather/json.shtml") Observable<WeatherInfo> getWeatherInfoForID(@Query("id") String id);
}
