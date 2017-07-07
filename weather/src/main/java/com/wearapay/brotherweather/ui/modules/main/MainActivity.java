package com.wearapay.brotherweather.ui.modules.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.wearapay.brotherweather.ui.modules.city.CityListActivity;
import com.wearapay.brotherweather.ui.modules.main.adapter.MainAdapter;
import com.wearapay.brotherweather.ui.presenter.GankioAllPresenter;
import com.wearapay.brotherweather.ui.view.IGankioView;
import com.wearapay.brotherweather.weight.MainIndicator;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BWBaseActivity implements IGankioView {

  @Inject GankioAllPresenter presenter;
  @Inject LocalRepository localRepository;
  @BindView(R.id.llTab) LinearLayout llTab;
  @BindView(R.id.vp) ViewPager vp;
  @BindView(R.id.indicator) MainIndicator indicator;
  @BindView(R.id.ivCities) ImageView ivCities;
  @BindView(R.id.ivSetting) ImageView ivSetting;
  private MainAdapter adapter;
  private List<MainPager> cityList;
  private List<GankioData> gankioDatas;
  private int mainPageCount;

  public List<GankioData> getGankioDatas() {
    return gankioDatas;
  }

  protected void initView() {
    SharedPreferences sharedPreferences = getSharedPreferences("bw_sp", MODE_PRIVATE);
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
        //startActivity(new Intent(MainActivity.this, CityListActivity.class));
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
    indicator.setNum(mainPageCount);
    vp.setOffscreenPageLimit(mainPageCount - 1);

    presenter.getGankioData(GankioType.Fuli, 10, 1);
  }

  @Override protected int getLayoutView() {
    return R.layout.activity_main;
  }

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[] { presenter };
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    ((App) getApplication()).getComponent().inject(this);
    super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    ButterKnife.bind(MainActivity.this);
    //        vp.setOffscreenPageLimit(1);
    initView();
    initData();
  }

  @Override protected CharSequence getActionBarTitle() {
    return null;
  }

  protected void initData() {

  }

  @OnClick({ R.id.ivCities, R.id.ivSetting }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ivCities:
        startActivity(new Intent(MainActivity.this, CityListActivity.class));
        break;
      case R.id.ivSetting:
        break;
    }
  }

  @Override public void display(List<GankioData> gankioDatas) {
    this.gankioDatas = gankioDatas;
    cityList.add(new MainPager(GankioType.Android, gankioDatas.get(0).getUrl(), 0));
    cityList.add(new MainPager(GankioType.IOS, gankioDatas.get(1).getUrl(), 1));
    cityList.add(new MainPager(GankioType.Web, gankioDatas.get(2).getUrl(), 2));
    adapter.notifyDataSetChanged();
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
}
