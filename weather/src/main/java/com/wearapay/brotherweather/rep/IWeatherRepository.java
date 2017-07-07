package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.domain.WeatherInfo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Query;

/**
 * Created by lyz54 on 2017/6/27.
 */

public interface IWeatherRepository {
    Observable<WeatherInfo> getWeatherInfo(String city);
}
