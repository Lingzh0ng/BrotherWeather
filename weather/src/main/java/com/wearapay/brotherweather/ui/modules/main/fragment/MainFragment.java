package com.wearapay.brotherweather.ui.modules.main.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.adapter.MainItemAdapter;
import com.wearapay.brotherweather.common.base.BaseLazyFragment;
import com.wearapay.brotherweather.common.listener.AppBarStateChangeListener;
import com.wearapay.brotherweather.common.utils.StatusBarTextColorUtils;
import com.wearapay.brotherweather.common.utils.ToastUtils;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.MainPager;
import com.wearapay.brotherweather.ui.presenter.GankioAllPresenter;
import com.wearapay.brotherweather.ui.view.IGankioView;
import com.wearapay.brotherweather.ui.modules.main.MainActivity;
import com.wearapay.brotherweather.ui.modules.photo.PhotoViewActivity;
import com.wearapay.brotherweather.ui.modules.web.WebViewActivity;
import com.wearapay.brotherweather.utils.StatusBarCompat;
import com.wearapay.brotherweather.weight.CustomRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class MainFragment extends BaseLazyFragment implements IGankioView {
  @BindView(R.id.backdrop) ImageView backdrop;
  @BindView(R.id.tvTitle) TextView tvTitle;
  @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
  @BindView(R.id.appbar) AppBarLayout appbar;
  @BindView(R.id.main_content) CoordinatorLayout mainContent;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) CustomRefreshLayout refreshLayout;
  Unbinder unbinder;

  private MainPager pager;
  private List<GankioData> gankioDatas;

  @Inject GankioAllPresenter gankioAllPresenter;
  private MainItemAdapter recyclerAdapter;
  private LinearLayoutManager linearLayoutManager;

  public static MainFragment MainCityName(MainPager pager) {
    MainFragment fragment = new MainFragment();
    fragment.pager = pager;
    return fragment;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((App) getActivity().getApplication()).getComponent().inject(this);
    gankioDatas = new ArrayList<>();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View inflate = inflater.inflate(R.layout.fragment_main, null);
    unbinder = ButterKnife.bind(this, inflate);
    //final int initialToolbarHeight = toolbar.getLayoutParams().height;
    final int initialToolbarHeight = (int) (getResources().getDisplayMetrics().density * 44);
    final int initialStatusBarHeight = StatusBarCompat.getDefaultStatusHeight(getContext());
    toolbar.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            System.out.println(pager.getType().getType());
            if (toolbar == null) return;
            toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            int[] locToolbar = new int[2];
            toolbar.getLocationOnScreen(locToolbar);
            int yToolbar = locToolbar[1];
            int topPaddingToolbar = toolbar.getPaddingTop();
            if (isAdded()) {
              //normal case : system status bar padding on toolbar : yToolbar = initialStatusBarHeight && topPaddingToolbar = 0
              //abnormal case : no system status bar padding on toolbar -> toolbar behind status bar => add custom padding
              if (yToolbar != initialStatusBarHeight && topPaddingToolbar == 0) {
                toolbar.setPadding(0, initialStatusBarHeight, 0, 0);
                toolbar.getLayoutParams().height = initialToolbarHeight + initialStatusBarHeight;
              }
              //abnormal case : system status bar padding and custom padding on toolbar -> toolbar with padding too large => remove custom padding
              else if (yToolbar == initialStatusBarHeight
                  && topPaddingToolbar == initialStatusBarHeight) {
                toolbar.setPadding(0, 0, 0, 0);
                toolbar.getLayoutParams().height = initialToolbarHeight;
              }
            }
          }
        });
    appbar.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            appbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            int measuredHeight = appbar.getMeasuredHeight();
            refreshLayout.setAppBarHeight(measuredHeight);
          }
        });
    return inflate;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    gankioAllPresenter.setView(this);
    super.onViewCreated(view, savedInstanceState);
    //mainContent = (CoordinatorLayout) view.findViewById(R.id.main_content);
    //ViewCompat.requestApplyInsets(mainContent);
    tvTitle.setText(pager.getType().getType());
  }

  @Override public void fetchData() {
    System.out.println(pager.getType().getType());
    //weatherPresenter.getWeatherInfo(city.getCounty());
    gankioAllPresenter.getGankioData(pager.getType(), 20, 1);
  }

  @Override protected void initView() {
    appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
      @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
      public void onStateChanged(AppBarLayout appBarLayout, State state) {
        Log.d("STATE", state.name());
        refreshLayout.setAppBarStatus(state);
        if (state == State.EXPANDED) {

          //展开状态
          tvTitle.setVisibility(View.GONE);
          StatusBarTextColorUtils.StatusBarLightMode(getActivity(), false);
        } else if (state == State.COLLAPSED) {
          StatusBarTextColorUtils.StatusBarLightMode(getActivity(), true);

          //折叠状态
          tvTitle.setVisibility(View.VISIBLE);
          //setMiuiStatusBarDarkMode(CheeseDetailActivity.this, true);
          //setWindowLightStatusBar(true);
        } else {
          //中间状态°

        }
      }
    });
    Glide.with(this).asBitmap().load(pager.getMeiziUrl()).into(backdrop);
    recyclerAdapter = new MainItemAdapter(gankioDatas, getActivity());
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    //recyclerView.addItemDecoration(new DividerItemDecoration());
    recyclerView.setAdapter(recyclerAdapter);

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //recyclerView.
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        refreshLayout.setRecylerViewPosition(
            linearLayoutManager.findFirstCompletelyVisibleItemPosition());
      }
    });
    refreshLayout.setOnRefreshListener(new CustomRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        gankioAllPresenter.getGankioData(pager.getType(), 20, i++);
      }
    });
    recyclerAdapter.setOnItemClickListener(new MainItemAdapter.OnItemClickListener() {
      @Override public void onClick(int position, GankioData gankioData) {
        if (TextUtils.isEmpty(gankioData.getUrl())) {
          ToastUtils.showShort("链接不存在");
          return;
        }
        gankioData.setBrowseHistory(true);
        recyclerAdapter.notifyItemChanged(position);
        gankioAllPresenter.setBrowseHistory(gankioData.get_id());
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", gankioData.getUrl());
        getActivity().startActivity(intent);
      }
    });

    //refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
    //  @Override public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
    //    super.onLoadMore(refreshLayout);
    //    gankioAllPresenter.getGankioData(pager.getType(), 20, 2);
    //  }
    //
    //  @Override public void onRefresh(TwinklingRefreshLayout refreshLayout) {
    //    super.onRefresh(refreshLayout);
    //    gankioAllPresenter.getGankioData(pager.getType(), 20, 3);
    //  }
    //});
    //
    //refreshLayout.setEnableRefresh(false);
    //refreshLayout.setEnableLoadmore(false);
  }

  @Override public void onCleanBeforeDetach() {

  }

  private int i = 1;

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override public void display(List<GankioData> gankioDatas) {
    finishRefresh();
    this.gankioDatas.addAll(gankioDatas);
    recyclerAdapter.notifyDataSetChanged();
  }

  @Override public void displayError() {
    finishRefresh();
  }

  private void finishRefresh() {
    //refreshLayout.finishRefreshing();
    //refreshLayout.finishLoadmore();
    refreshLayout.finishRefresh();
  }

  @OnClick(R.id.backdrop) public void onClick() {
    Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
    intent.putExtra("currentPage", pager.getCurrentPager());
    ArrayList<GankioData> gankioDatas =
        (ArrayList<GankioData>) ((MainActivity) getActivity()).getGankioDatas();
    intent.putExtra("gankioDatas", gankioDatas);
    startActivity(intent);
  }
}














