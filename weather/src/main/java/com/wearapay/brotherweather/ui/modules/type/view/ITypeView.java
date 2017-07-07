package com.wearapay.brotherweather.ui.modules.type.view;

import com.wearapay.brotherweather.common.mvp.IBaseView;
import com.wearapay.brotherweather.db.entity.MainSetting;

/**
 * Created by lyz on 2017/7/7.
 */
public interface ITypeView extends IBaseView {

  void displaySetting(MainSetting mainSetting);

}
