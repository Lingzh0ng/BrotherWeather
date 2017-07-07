package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.domain.BaseResult;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioDayData;
import com.wearapay.brotherweather.domain.GankioType;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/7/3.
 */
public interface IGankioRepository {

  Observable<BaseResult<GankioData>> getAllGankioData(GankioType type, int count, int page);

  Observable<BaseResult<GankioData>> getRandomGankioData(GankioType type, int count);

  Observable<BaseResult<GankioData>> searchGankio(GankioType type, int count, int page);

  Observable<BaseResult<GankioDayData>> getSomeDayData(int count, int page);

  Observable<BaseResult<GankioDayData>> getOneDayData(String year, String month, String day);

  Observable<BaseResult<String>> getTimeList();
}
