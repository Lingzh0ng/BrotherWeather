package com.wearapay.brotherweather.ui.modules.main.presenter;

import android.content.Context;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.rep.WeatherRepository;
import com.wearapay.brotherweather.ui.modules.main.view.IWeatherView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class WeatherPresenter extends BasePresenter<IWeatherView> {
    WeatherRepository weatherRepository;

    @Inject
    public WeatherPresenter(Context mContext, WeatherRepository weatherRepository) {
        super(mContext);
        this.weatherRepository = weatherRepository;
    }

    public void getWeatherInfo(String city) {

    }

    protected <T> Observable<T> wrap(Observable<T> origin) {
        return origin
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
