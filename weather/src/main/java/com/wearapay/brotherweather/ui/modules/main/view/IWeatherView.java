package com.wearapay.brotherweather.ui.modules.main.view;

import com.wearapay.brotherweather.common.mvp.IBaseView;
import com.wearapay.brotherweather.domain.WeatherInfo;

/**
 * Created by lyz54 on 2017/6/28.
 */

public interface IWeatherView extends IBaseView {
    void display(WeatherInfo weatherInfo);
}
