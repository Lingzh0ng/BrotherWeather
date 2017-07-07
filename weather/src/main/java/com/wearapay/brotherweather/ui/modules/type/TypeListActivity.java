package com.wearapay.brotherweather.ui.modules.type;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.BWBaseActivity;
import com.wearapay.brotherweather.common.adapter.SettingAdapter;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.domain.MainPager;
import java.util.ArrayList;

/**
 * Created by lyz on 2017/6/29.
 */
public class TypeListActivity extends BWBaseActivity {

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) TwinklingRefreshLayout refreshLayout;
  @BindView(R.id.tvSave) TextView tvSave;
  @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;
  private SettingAdapter adapter;
  private ArrayList<MainPager> mainpagers;
  private ArrayList<MainPager> removeList;

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[] {};
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

  private void initView() {
    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setEnableRefresh(false);
    ivMenu.setVisibility(View.VISIBLE);
    Intent intent = getIntent();
    mainpagers = (ArrayList<MainPager>) intent.getSerializableExtra("mainpager");
    removeList = new ArrayList<>();
    removeList.addAll(mainpagers);
    if (mainpagers != null && mainpagers.size() > 0) {
      adapter = new SettingAdapter(removeList, TypeListActivity.this);
      adapter.setOnDelClickListener(new SettingAdapter.OnDelClickListener() {
        @Override public void onClick(int position, MainPager pager) {
          showMessage("删除:" + pager.getType().getType());
          removeList.remove(pager);
          adapter.notifyDataSetChanged();
        }

        @Override public void onitemClick(int position, MainPager pager) {
          if (!adapter.isEditStatus()) {
            Intent intent = new Intent();
            intent.putExtra("position", position);
            setResult(1, intent);
            finish();
          }
        }
      });
      recyclerView.setLayoutManager(new LinearLayoutManager(TypeListActivity.this));
      recyclerView.setAdapter(adapter);
    } else {
      showMessage("程序出错");
      finish();
    }
  }

  @Override protected CharSequence getActionBarTitle() {
    return "主页管理";
  }

  @Override protected void OnClickMenu() {
    super.OnClickMenu();
    tvSave.setVisibility(View.VISIBLE);
    if (adapter.isEditStatus()) {
      removeList.clear();
      removeList.addAll(mainpagers);
      adapter.setEditStatus(false);
      tvTitle.setText("主页管理");
      tranAnim(0f, 1f);
    } else {
      adapter.setEditStatus(true);
      tvTitle.setText("编辑");
      tranAnim(1f, 0f);
    }
  }

  @OnClick(R.id.tvSave) public void onViewClicked() {
    if (!adapter.isEditStatus()) return;
    mainpagers.clear();
    mainpagers.addAll(removeList);
    adapter.setEditStatus(false);
    tranAnim(0f, 1f);
  }

  private void tranAnim(float start, float end) {
    TranslateAnimation translateAnimation =
        new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
            Animation.RELATIVE_TO_SELF, start, Animation.RELATIVE_TO_SELF, end);
    translateAnimation.setDuration(300L);
    translateAnimation.setFillAfter(true);
    tvSave.setAnimation(translateAnimation);
  }
}
