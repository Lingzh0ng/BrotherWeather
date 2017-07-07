package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.BWConts;
import com.wearapay.brotherweather.db.BWGreenDaoService;
import com.wearapay.brotherweather.db.entity.BrowseHistory;
import com.wearapay.brotherweather.db.entity.BrowseHistoryDao;
import com.wearapay.brotherweather.db.entity.DaoSession;
import com.wearapay.brotherweather.db.entity.MainSetting;
import com.wearapay.brotherweather.db.entity.MainSettingDao;
import com.wearapay.brotherweather.db.rx.DbObservable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by lyz on 2017/7/6.
 */
@Singleton public class DbRepository implements IDbRepository {
  private BWGreenDaoService service;
  private final DaoSession daoSession;

  @Inject public DbRepository(BWGreenDaoService service) {
    this.service = service;
    daoSession = service.getDaoSession();
  }

  @Override public Observable<Boolean> addBrowseHistory(final String gankioId) {
    return DbObservable.apply(daoSession.getBrowseHistoryDao(),
        new Function<BrowseHistoryDao, Boolean>() {
          @Override public Boolean apply(@NonNull BrowseHistoryDao browseHistoryDao)
              throws Exception {
            List<BrowseHistory> list = browseHistoryDao.queryBuilder()
                .where(BrowseHistoryDao.Properties.GankioId.eq(gankioId))
                .list();
            if (list != null && list.size() == 0) {
              daoSession.getBrowseHistoryDao()
                  .insert(new BrowseHistory(null, gankioId, System.currentTimeMillis()));
            }
            return true;
          }
        });
  }

  @Override public Observable<Boolean> queryBrowseHistory(final String gankioId) {
    return DbObservable.apply(daoSession.getBrowseHistoryDao(),
        new Function<BrowseHistoryDao, Boolean>() {
          @Override public Boolean apply(@NonNull BrowseHistoryDao browseHistoryDao)
              throws Exception {
            List<BrowseHistory> list = browseHistoryDao.queryBuilder()
                .where(BrowseHistoryDao.Properties.GankioId.eq(gankioId))
                .list();
            if (list != null && list.size() > 0) {
              return true;
            } else {
              return false;
            }
          }
        });
  }

  @Override public Observable<Boolean> updateMainSetting(final MainSetting mainSetting) {
    return DbObservable.apply(daoSession.getMainSettingDao(),
        new Function<MainSettingDao, Boolean>() {
          @Override public Boolean apply(@NonNull MainSettingDao mainSettingDao) throws Exception {
            mainSetting.setId(BWConts.MAIN_SETTING_ID);
            mainSettingDao.update(mainSetting);
            return true;
          }
        });
  }

  @Override public Observable<MainSetting> queryMainSetting() {

    return DbObservable.apply(daoSession.getMainSettingDao(),
        new Function<MainSettingDao, MainSetting>() {
          @Override public MainSetting apply(@NonNull MainSettingDao mainSettingDao)
              throws Exception {
            List<MainSetting> list = mainSettingDao.queryBuilder()
                .where(MainSettingDao.Properties.Id.eq(BWConts.MAIN_SETTING_ID))
                .build()
                .list();
            if (list == null || list.size() == 0) {
              MainSetting mainSetting =
                  new MainSetting(BWConts.MAIN_SETTING_ID, 3, "Android,IOS,Web");
              updateMainSetting(mainSetting);
              return mainSetting;
            } else {
              return list.get(0);
            }
          }
        });
  }
}
