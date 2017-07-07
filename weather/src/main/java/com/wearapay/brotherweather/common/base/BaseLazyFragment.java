package com.wearapay.brotherweather.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by lyz on 2017/7/3.
 */
public abstract class BaseLazyFragment extends BaseFragment {

  protected boolean isViewInitiated;
  protected boolean isVisibleToUser;
  protected boolean isDataInitiated;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.isViewInitiated = true;
    initView();
    onLazyLoad(false);
  }

  protected abstract void initView();

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    this.isVisibleToUser = isVisibleToUser;
    onLazyLoad(false);
  }

  private void onLazyLoad(boolean forceUpdate) {
    if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
      fetchData();
      isDataInitiated = true;
    }
  }

  public abstract void fetchData();
}
