package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.db.entity.MainSetting;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/7/6.
 */
public interface IDbRepository {
  Observable<Boolean> addBrowseHistory(String gankioId);

  Observable<Boolean> queryBrowseHistory(String gankioId);

  Observable<Boolean> updateMainSetting(MainSetting mainSetting);

  Observable<MainSetting> queryMainSetting();
}
