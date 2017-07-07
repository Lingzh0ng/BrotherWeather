package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.api.IWeatherRestService;
import com.wearapay.brotherweather.domain.WeatherInfo;
import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by lyz54 on 2017/6/27.
 */
@Singleton
public class WeatherRepository implements IWeatherRepository {

    private IWeatherRestService weatherRestService;

    @Inject
    public WeatherRepository(IWeatherRestService weatherRestService) {
        this.weatherRestService = weatherRestService;
    }

    @Override
    public Observable<WeatherInfo> getWeatherInfo(String city) {
        return weatherRestService.getWeatherInfo(city);
    }
}
