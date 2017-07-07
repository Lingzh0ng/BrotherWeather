package com.wearapay.brotherweather.ui.view;

import com.wearapay.brotherweather.common.mvp.IBaseView;
import com.wearapay.brotherweather.domain.GankioData;
import java.util.List;

/**
 * Created by lyz54 on 2017/6/28.
 */

public interface IGankioView extends IBaseView {
    void display(List<GankioData> gankioDatas);

    void displayError();
}
