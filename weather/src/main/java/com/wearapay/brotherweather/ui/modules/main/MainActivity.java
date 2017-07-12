package com.wearapay.brotherweather.ui.modules.main;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.BWBaseActivity;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioType;
import com.wearapay.brotherweather.domain.MainPager;
import com.wearapay.brotherweather.rep.LocalRepository;
import com.wearapay.brotherweather.ui.modules.main.adapter.MainAdapter;
import com.wearapay.brotherweather.ui.modules.main.presenter.MainSettingPresenter;
import com.wearapay.brotherweather.ui.modules.main.view.IMainSettingView;
import com.wearapay.brotherweather.ui.modules.type.TypeListActivity;
import com.wearapay.brotherweather.ui.presenter.GankioAllPresenter;
import com.wearapay.brotherweather.ui.view.IGankioView;
import com.wearapay.brotherweather.weight.MainIndicator;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BWBaseActivity implements IGankioView, IMainSettingView {

  @Inject GankioAllPresenter presenter;
  @Inject MainSettingPresenter mainSettingPresenter;
  @Inject LocalRepository localRepository;
  @BindView(R.id.llTab) LinearLayout llTab;
  @BindView(R.id.vp) ViewPager vp;
  @BindView(R.id.indicator) MainIndicator indicator;
  @BindView(R.id.ivCities) ImageView ivCities;
  @BindView(R.id.ivSetting) ImageView ivSetting;
  private MainAdapter adapter;
  private ArrayList<MainPager> cityList;
  private List<GankioData> gankioDatas;
  private int mainPageCount;
  private GankioType type = null;

  public List<GankioData> getGankioDatas() {
    return gankioDatas;
  }

  protected void initView() {
    mainPageCount = localRepository.getMainPageCount();
    cityList = new ArrayList<>(mainPageCount);

    adapter = new MainAdapter(getSupportFragmentManager(), cityList);
    //https://stackoverflow.com/questions/31368781/coordinatorlayout-status-bar-padding-disappears-from-viewpager-2nd-page
    ViewCompat.setOnApplyWindowInsetsListener(vp, new OnApplyWindowInsetsListener() {
      @Override public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        insets = ViewCompat.onApplyWindowInsets(v, insets);
        if (insets.isConsumed()) {
          return insets;
        }

        boolean consumed = false;
        for (int i = 0, count = vp.getChildCount(); i < count; i++) {
          ViewCompat.dispatchApplyWindowInsets(vp.getChildAt(i), insets);
          if (insets.isConsumed()) {
            consumed = true;
          }
        }
        return consumed ? insets.consumeSystemWindowInsets() : insets;
      }
    });
    vp.setAdapter(adapter);
    vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        indicator.setSelect(position);
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
    indicator.setNum(mainPageCount);
    vp.setOffscreenPageLimit(1);

    presenter.getGankioData(GankioType.Fuli, 10, 1);
  }

  @Override protected int getLayoutView() {
    return R.layout.activity_main;
  }

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[] { presenter, mainSettingPresenter };
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    ((App) getApplication()).getComponent().inject(this);
    super.onCreate(savedInstanceState);
    ButterKnife.bind(MainActivity.this);
    initView();
    initData();
  }

  @Override protected CharSequence getActionBarTitle() {
    return null;
  }

  protected void initData() {

  }

  private static int REQ_CODE_SETTING = 1;

  @OnClick({ R.id.ivCities, R.id.ivSetting }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ivCities:
        Intent intent = new Intent(MainActivity.this, TypeListActivity.class);
        intent.putExtra("mainpager", cityList);
        startActivityForResult(intent, REQ_CODE_SETTING);
        break;
      case R.id.ivSetting:
        //Bitmap btm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher1);
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher1)
                //.setColor(Color.parseColor("#FB793A"))
                .setContentTitle("标题")
                .setContentText("我是内容");
        mBuilder.setTicker("有通知来了");
        //mBuilder.setLargeIcon(btm);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
            (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        break;
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (REQ_CODE_SETTING == requestCode) {
      //TODO
      if (data != null) {
        boolean isListChange = data.getBooleanExtra("isListChange", false);
        if (data.hasExtra("positionType")) {
          type = (GankioType) data.getSerializableExtra("positionType");
        }
        if (isListChange) {
          mainSettingPresenter.getMainSetting(gankioDatas);
        } else {
          setViewPageCurrentItem(type);
        }
      }
    }
  }

  private void setViewPageCurrentItem(GankioType type) {
    if (type != null) {
      for (int i = 0; i < cityList.size(); i++) {
        if (cityList.get(i).getType().equals(type)) {
          vp.setCurrentItem(i, false);
        }
      }
    } else {
      vp.setCurrentItem(0, false);
    }
  }

  @Override public void display(List<GankioData> gankioDatas) {
    this.gankioDatas = gankioDatas;
    mainSettingPresenter.getMainSetting(gankioDatas);
  }

  @Override public void displayError() {

  }

  private long touchTime;

  @Override public void onBackPressed() {
    long currentTime = System.currentTimeMillis();
    if ((currentTime - touchTime) >= 3000) {
      showMessage("再按一次退出应程序");
      touchTime = currentTime;
    } else {
      ((App) getApplication()).exitApp();
    }
  }

  @Override public void displayMainPager(List<MainPager> mainPagerList) {
    indicator.setNum(mainPagerList.size());
    cityList.clear();
    cityList = (ArrayList<MainPager>) mainPagerList;
    adapter.notifyDataSetChanged(cityList);
    setViewPageCurrentItem(type);
  }
}
