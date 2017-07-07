package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.api.IGankioRestService;
import com.wearapay.brotherweather.domain.BaseResult;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioDayData;
import com.wearapay.brotherweather.domain.GankioType;
import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by lyz on 2017/7/3.
 */
@Singleton
public class GankioRepository implements IGankioRepository {
  IGankioRestService service;

  @Inject public GankioRepository(IGankioRestService service) {
    this.service = service;
  }

  @Override
  public Observable<BaseResult<GankioData>> getAllGankioData(GankioType type, int count, int page) {
    return service.getAllGankioData(type.getType(), count, page);
  }

  @Override
  public Observable<BaseResult<GankioData>> getRandomGankioData(GankioType type, int count) {
    return service.getRandomGankioData(type.getType(), count);
  }

  @Override
  public Observable<BaseResult<GankioData>> searchGankio(GankioType type, int count, int page) {
    return service.searchGankio(type.getType(), count, page);
  }

  @Override public Observable<BaseResult<GankioDayData>> getSomeDayData(int count, int page) {
    return service.getSomeDayData(count, page);
  }

  @Override public Observable<BaseResult<GankioDayData>> getOneDayData(String year, String month,
      String day) {
    return service.getOneDayData(year, month, day);
  }

  @Override public Observable<BaseResult<String>> getTimeList() {
    return service.getTimeList();
  }
}
