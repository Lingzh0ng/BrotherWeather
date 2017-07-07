package com.wearapay.brotherweather.dagger2;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * Created by lyz on 2017/6/27.
 */
@Module public final class ApplicationModule {
  private final Context mContext;

  public ApplicationModule(Context context) {
    mContext = context;
  }

  @Provides Context provideContext() {
    return mContext;
  }
}
