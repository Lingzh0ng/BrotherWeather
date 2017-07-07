package com.wearapay.brotherweather.dagger2.db;

import android.content.Context;
import com.wearapay.brotherweather.db.BWGreenDaoService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by lyz on 2017/7/6.
 */
@Module public class WeatherDaoModule {

  @Provides BWGreenDaoService provideBWGreenDaoService(Context context) {
    BWGreenDaoService instance = BWGreenDaoService.getInstance();
    instance.init(context);
    return instance;
  }
}
