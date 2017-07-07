package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.db.BWGreenDaoService;
import com.wearapay.brotherweather.db.entity.BrowseHistory;
import com.wearapay.brotherweather.db.entity.BrowseHistoryDao;
import com.wearapay.brotherweather.db.entity.DaoSession;
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

  @Override public void addBrowseHistory(String gankioId) {
    List<BrowseHistory> list = daoSession.getBrowseHistoryDao()
        .queryBuilder()
        .where(BrowseHistoryDao.Properties.GankioId.eq(gankioId))
        .list();
    if (list != null && list.size() == 0) {
      daoSession.getBrowseHistoryDao()
          .insert(new BrowseHistory(null, gankioId, System.currentTimeMillis()));
    }
  }

  @Override public boolean queryBrowseHistory(String gankioId) {
    List<BrowseHistory> list = daoSession.getBrowseHistoryDao()
        .queryBuilder()
        .where(BrowseHistoryDao.Properties.GankioId.eq(gankioId))
        .list();
    if (list != null && list.size() > 0) {
      return true;
    } else {
      return false;
    }
  }
}
