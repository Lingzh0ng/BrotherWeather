package com.wearapay.brotherweather.rep;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by lyz on 2017/7/3.
 */
@Singleton
public class LocalRepository {

  private final SharedPreferences sharedPreferences;

  @Inject public LocalRepository(Context context) {
    sharedPreferences = context.getSharedPreferences("bw_sp", Context.MODE_PRIVATE);
  }

  public int getMainPageCount() {
    return sharedPreferences.getInt("mainPageCount", 3);
  }

  public void setMainPageCount(int count) {
    SharedPreferences.Editor edit = sharedPreferences.edit();
    edit.putInt("mainPageCount", count);
    edit.apply();
  }
}
