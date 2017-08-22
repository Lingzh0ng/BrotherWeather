package com.wearapay.brotherweather.ui.modules.main.view;

import com.wearapay.brotherweather.common.mvp.IBaseView;
import com.wearapay.brotherweather.domain.MainPager;
import java.util.List;

/**
 * Created by lyz54 on 2017/6/28.
 */

public interface IMainSettingView extends IBaseView {
    void displayMainPager(List<MainPager> mainPagerList);
}
