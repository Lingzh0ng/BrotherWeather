package com.wearapay.brotherweather.ui.modules.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.wearapay.brotherweather.domain.MainPager;
import com.wearapay.brotherweather.ui.modules.main.fragment.MainFragment;
import java.util.List;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class MainAdapter extends FragmentPagerAdapter {
  List<MainPager> list;

  public MainAdapter(FragmentManager fm, List<MainPager> list) {
    super(fm);
    this.list = list;
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return ((Fragment) object).getView() == view;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    return super.instantiateItem(container, position);
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    super.destroyItem(container, position, object);
  }

  @Override public Fragment getItem(int position) {
    MainFragment fragment = MainFragment.MainCityName(list.get(position));
    return fragment;
  }

  @Override public int getCount() {
    return list.size();
  }
}
