package com.wearapay.brotherweather.dagger2;

import com.wearapay.brotherweather.api.ICityRestService;
import com.wearapay.brotherweather.api.IGankioRestService;
import com.wearapay.brotherweather.api.IWeatherRestService;
import com.wearapay.brotherweather.dagger2.db.WeatherDaoModule;
import com.wearapay.brotherweather.dagger2.net.WeatherRestModule;
import com.wearapay.brotherweather.db.BWGreenDaoService;
import com.wearapay.brotherweather.rep.CityRepository;
import com.wearapay.brotherweather.rep.DbRepository;
import com.wearapay.brotherweather.rep.GankioRepository;
import com.wearapay.brotherweather.rep.ICityRepository;
import com.wearapay.brotherweather.rep.IDbRepository;
import com.wearapay.brotherweather.rep.IGankioRepository;
import com.wearapay.brotherweather.rep.IWeatherRepository;
import com.wearapay.brotherweather.rep.WeatherRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by lyz on 2017/7/7.
 */
@Singleton @Module(includes = { WeatherRestModule.class, WeatherDaoModule.class })
public class RepositoryModule {
  @Provides @Singleton IDbRepository provideIDbRepository(BWGreenDaoService service) {
    return new DbRepository(service);
  }

  @Provides @Singleton ICityRepository provideICityRepository(ICityRestService service) {
    return new CityRepository(service);
  }

  @Provides @Singleton IGankioRepository provideIGankioRepository(IGankioRestService service) {
    return new GankioRepository(service);
  }

  @Provides @Singleton IWeatherRepository provideIWeatherRestService(IWeatherRestService service) {
    return new WeatherRepository(service);
  }
}
