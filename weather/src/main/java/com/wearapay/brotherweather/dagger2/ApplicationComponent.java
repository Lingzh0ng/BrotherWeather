package com.wearapay.brotherweather.dagger2;

/**
 * Created by lyz on 2017/6/27.
 */

import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.common.BWBaseActivity;
import com.wearapay.brotherweather.ui.modules.city.CityListActivity;
import com.wearapay.brotherweather.ui.modules.main.MainActivity;
import com.wearapay.brotherweather.ui.modules.main.fragment.MainFragment;
import com.wearapay.brotherweather.ui.modules.photo.PhotoViewActivity;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = {
    ApplicationModule.class, RepositoryModule.class
}) public interface ApplicationComponent {
  void inject(App application);

  void inject(BWBaseActivity activity);

  void inject(MainActivity activity);

  void inject(CityListActivity activity);

  void inject(PhotoViewActivity activity);

  void inject(MainFragment fragment);
}
