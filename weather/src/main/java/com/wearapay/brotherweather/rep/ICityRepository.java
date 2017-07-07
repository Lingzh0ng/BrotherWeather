package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.domain.City;
import com.wearapay.brotherweather.domain.WeatherInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.ResponseBody;
import retrofit2.http.Query;

/**
 * Created by lyz54 on 2017/6/27.
 */

public interface ICityRepository {
    Observable<List<City>> getCityInfo(String id);
}
