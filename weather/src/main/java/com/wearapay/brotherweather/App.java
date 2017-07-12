package com.wearapay.brotherweather;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.wearapay.brotherweather.dagger2.ApplicationComponent;
import com.wearapay.brotherweather.dagger2.ApplicationModule;
import com.wearapay.brotherweather.dagger2.DaggerApplicationComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/6/27.
 */
public class App extends Application {

  private ApplicationComponent component;

  public static App app;

  private List<Activity> activityList = new ArrayList<>();
  private int activityStatus = 0;

  @Override public void onCreate() {
    super.onCreate();
    app = this;
    component =
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    component.inject(this);
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityList.add(activity);
      }

      @Override public void onActivityStarted(Activity activity) {
        activityStatus++;
      }

      @Override public void onActivityResumed(Activity activity) {

      }

      @Override public void onActivityPaused(Activity activity) {

      }

      @Override public void onActivityStopped(Activity activity) {
        activityStatus--;
      }

      @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);
      }
    });

    TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
  }

  public ApplicationComponent getComponent() {
    return component;
  }

  public List<Activity> getActivityList() {
    return activityList;
  }

  public Activity getCurrentActivity() {
    return activityList.get(activityList.size() - 1);
  }

  public boolean isAppHide() {
    return activityStatus == 1;
  }

  public void exitApp() {
    for (int i = 0; i < activityList.size(); i++) {
      activityList.get(i).finish();
    }
  }

  public static RequestOptions options = new RequestOptions().centerCrop()
      .placeholder(R.drawable.ui_ad)
      .error(R.drawable.ui_ad)
      .priority(Priority.NORMAL)
      .centerCrop()
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .skipMemoryCache(true);

  public static RequestOptions getRequestOptions() {
    return options;
  }
}