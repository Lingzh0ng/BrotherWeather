package com.wearapay.brotherweather.db;

import android.content.Context;
import com.wearapay.brotherweather.db.entity.DaoMaster;
import com.wearapay.brotherweather.db.entity.DaoSession;
import org.greenrobot.greendao.database.Database;

/**
 * Created by lyz on 2017/7/6.
 */
public class BWGreenDaoService {

  private Context appContext;
  private DaoSession daoSession;

  private BWGreenDaoService() {
  }

  public static BWGreenDaoService getInstance() {
    return Holder.instance;
  }

  private static class Holder {
    private static BWGreenDaoService instance = new BWGreenDaoService();
  }

  public void init(Context appContext) {
    this.appContext = appContext;
    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.appContext, "notes-db");
    Database db = helper.getWritableDb();
    daoSession = new DaoMaster(db).newSession();
  }

  public DaoSession getDaoSession() {
    return daoSession;
  }
}
