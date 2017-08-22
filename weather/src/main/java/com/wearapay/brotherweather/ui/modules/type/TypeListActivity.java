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
import com.wearapay.brotherweather.db.entity.MainSetting;
import com.wearapay.brotherweather.domain.GankioType;
import com.wearapay.brotherweather.domain.MainPager;
import com.wearapay.brotherweather.ui.modules.type.presenter.TypeListPresenter;
import com.wearapay.brotherweather.ui.modules.type.view.ITypeView;
import com.wearapay.brotherweather.weight.dialog.TypeListDialog;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/6/29.
 */
public class TypeListActivity extends BWBaseActivity implements ITypeView {

  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) TwinklingRefreshLayout refreshLayout;
  @BindView(R.id.tvSave) TextView tvSave;
  @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;
  private SettingAdapter adapter;
  private ArrayList<MainPager> mainpagers;
  private ArrayList<MainPager> removeList;
  private String[] typeLists;
  private MainSetting mainSetting;

  @Inject TypeListPresenter typeListPresenter;
  private Intent resultIntent;
  private boolean isListChange = false;

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[] { typeListPresenter };
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
    resultIntent = new Intent();
    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setEnableRefresh(false);
    ivMenu.setVisibility(View.VISIBLE);
    Intent intent = getIntent();
    mainpagers = (ArrayList<MainPager>) intent.getSerializableExtra("mainpager");
    removeList = new ArrayList<>();
    removeList.addAll(mainpagers);
    typeListPresenter.queryMainSetting();
    if (mainpagers != null && mainpagers.size() > 0) {
      adapter = new SettingAdapter(removeList, TypeListActivity.this);
      adapter.setOnDelClickListener(new SettingAdapter.OnDelClickListener() {
        @Override public void onClick(int position, MainPager pager) {
          if (removeList.size() <= 3) {
            showMessage("你应该留点东西好好学习");
            return;
          }
          //showMessage("删除:" + pager.getType().getType());
          removeList.remove(pager);
          adapter.notifyDataSetChanged();
        }

        @Override public void onItemClick(int position, MainPager pager) {
          if (!adapter.isEditStatus()) {
            resultIntent.putExtra("positionType", removeList.get(position).getType());
            setResult(1, resultIntent);
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

  @Override protected void onDestroy() {
    setResult(1, resultIntent);
    super.onDestroy();
  }

  @OnClick(R.id.tvSave) public void onViewClicked() {
    if (!adapter.isEditStatus()) return;
    if (handleMainSetting()) typeListPresenter.updateMainSetting(mainSetting);
    mainpagers.clear();
    mainpagers.addAll(removeList);
    adapter.setEditStatus(false);
    tranAnim(0f, 1f);
  }

  @OnClick(R.id.floatingActionButton) public void onClick() {
    typeLists = getResources().getStringArray(R.array.types);
    List<MainPager> list = new ArrayList<>();
    MainPager mainPager;
    for (int i = 0; i < typeLists.length; i++) {
      mainPager = new MainPager(GankioType.valueOf((typeLists[i])), null, 0);
      if(!mainpagers.contains(mainPager)){
        mainPager.setSelect(false);
      }
      list.add(mainPager);
    }
    TypeListDialog typeListDialog = TypeListDialog.show(getSupportFragmentManager(), list, "添加");
    typeListDialog.setOnTypeOKListener(new TypeListDialog.OnTypeOKListener() {
      @Override public void onOK(List<MainPager> list) {
        removeList.clear();
        for (int i = 0; i < list.size(); i++) {
          MainPager mainPager = list.get(i);
          if (mainPager.isSelect()) {
            removeList.add(mainPager);
          }
        }
        adapter.notifyDataSetChanged();
        if (handleMainSetting()) typeListPresenter.updateMainSetting(mainSetting);
      }
    });
    typeListDialog.setCancelable(false);
  }

  private boolean handleMainSetting() {
    if (mainSetting == null) {
      mainSetting = new MainSetting();
    }
    mainSetting.setPagerCount(removeList.size());
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < removeList.size(); i++) {
      sb.append(removeList.get(i).getType().name());
      if (i != removeList.size() - 1) {
        sb.append(",");
      }
    }
    String gankioTypes = mainSetting.getGankioTypes();
    if (!sb.toString().equals(gankioTypes)) {
      mainSetting.setGankioTypes(sb.toString());
      return true;
    }
    return false;
  }

  private void tranAnim(float start, float end) {
    TranslateAnimation translateAnimation =
        new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
            Animation.RELATIVE_TO_SELF, start, Animation.RELATIVE_TO_SELF, end);
    translateAnimation.setDuration(300L);
    translateAnimation.setFillAfter(true);
    tvSave.setAnimation(translateAnimation);
  }

  @Override public void displaySetting(MainSetting mainSetting) {
    this.mainSetting = mainSetting;
    tvTitle.setText("主页管理");
  }

  @Override public void updateSetting(boolean success) {
    showMessage("设置成功");
    tvTitle.setText("主页管理");
    System.out.println(success);
    isListChange = success;
    resultIntent.putExtra("isListChange", isListChange);
    setResult(1,resultIntent);
  }
}
