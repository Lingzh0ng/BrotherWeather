package com.wearapay.brotherweather.ui.modules.city;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.BWBaseActivity;
import com.wearapay.brotherweather.common.adapter.MainItemAdapter;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.common.utils.ToastUtils;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioType;
import com.wearapay.brotherweather.ui.modules.web.WebViewActivity;
import com.wearapay.brotherweather.ui.presenter.GankioAllPresenter;
import com.wearapay.brotherweather.ui.view.IGankioView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/6/29.
 */
public class CityListActivity extends BWBaseActivity implements IGankioView {
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.appbar) AppBarLayout appBarLayout;
  @BindView(R.id.refreshLayout) TwinklingRefreshLayout refreshLayout;
  @Inject GankioAllPresenter gankioAllPresenter;

  private List<GankioData> gankioDatas;
  private MainItemAdapter adapter;

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[] { gankioAllPresenter };
  }

  @Override protected int getLayoutView() {
    return R.layout.activity_city_list;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    ((App) getApplication()).getComponent().inject(this);
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
    initView();
  }

  @Override protected CharSequence getActionBarTitle() {
    return "精品推荐";
  }

  private void initView() {
    gankioDatas = new ArrayList<>();
    adapter = new MainItemAdapter(gankioDatas, this);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    ivMenu.setVisibility(View.VISIBLE);
    refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
      @Override public void onRefresh(TwinklingRefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        isOnRefresh = true;
        gankioAllPresenter.getGankioData(GankioType.Android, 20, 1);
      }

      @Override public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        gankioAllPresenter.getGankioData(GankioType.Android, 20, i++);
      }

      @Override public void onLoadmoreCanceled() {
        super.onLoadmoreCanceled();
        //gankioAllPresenter.getGankioData(GankioType.Android, 20, 2);
      }
    });
    recyclerView.postDelayed(new Runnable() {
      @Override public void run() {
        refreshLayout.startRefresh();
      }
    }, 500L);

    adapter.setOnItemClickListener(new MainItemAdapter.OnItemClickListener() {
      @Override public void onClick(int position, GankioData gankioData) {
        if (TextUtils.isEmpty(gankioData.getUrl())) {
          ToastUtils.showShort("链接不存在");
          return;
        }
        gankioData.setBrowseHistory(true);
        adapter.notifyItemChanged(position);
        gankioAllPresenter.setBrowseHistory(gankioData.get_id());
        Intent intent = new Intent(CityListActivity.this, WebViewActivity.class);
        intent.putExtra("url", gankioData.getUrl());
        startActivity(intent);
      }
    });
    //gankioAllPresenter.getGankioData(GankioType.Android, 20, 1);
  }

  private boolean isOnRefresh = false;
  private int i = 1;

  @Override protected void OnClickMenu() {
    super.OnClickMenu();
    ToastUtils.showShort("菜单");
  }

  @Override public void display(List<GankioData> gankioDatas) {
    //this.gankioDatas.clear();
    if (isOnRefresh) {
      isOnRefresh = false;
      this.gankioDatas.clear();
      this.gankioDatas.addAll(gankioDatas);
    } else {
      this.gankioDatas.addAll(gankioDatas);
    }
    adapter.notifyDataSetChanged();
    finishRefresh();
  }

  @Override public void displayError() {
    isOnRefresh = false;
    ToastUtils.showShort("出错");
    finishRefresh();
  }

  private void finishRefresh() {
    refreshLayout.finishRefreshing();
    refreshLayout.finishLoadmore();
  }
}
