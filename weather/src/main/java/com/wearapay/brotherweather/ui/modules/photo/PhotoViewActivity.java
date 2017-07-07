package com.wearapay.brotherweather.ui.modules.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.BWBaseActivity;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.common.utils.ToastUtils;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioType;
import com.wearapay.brotherweather.ui.presenter.GankioAllPresenter;
import com.wearapay.brotherweather.ui.view.IGankioView;
import com.wearapay.brotherweather.weight.PhotoViewPager;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/7/5.
 */
public class PhotoViewActivity extends BWBaseActivity implements IGankioView {

  @BindView(R.id.my_action_bar) LinearLayout myActionBar;
  @BindView(R.id.appbar) AppBarLayout appbar;
  @BindView(R.id.photoView) PhotoViewPager photoViewPager;
  @BindView(R.id.tvContent) TextView tvContent;
  @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;
  private int currentPage;
  private ArrayList<GankioData> gankioDatas;
  private PhotoAdapter photoAdapter;

  private int i = 2;

  @Inject GankioAllPresenter presenter;

  @Override protected CharSequence getActionBarTitle() {
    return "加载中...";
  }

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[] { presenter };
  }

  @Override protected int getLayoutView() {
    return R.layout.activity_photoview;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    ((App) getApplication()).getComponent().inject(this);
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    currentPage = intent.getIntExtra("currentPage", 0);
    gankioDatas = (ArrayList<GankioData>) intent.getSerializableExtra("gankioDatas");
    if (gankioDatas != null && gankioDatas.size() >= currentPage) {
      photoAdapter = new PhotoAdapter(gankioDatas, this);
      photoViewPager.setAdapter(photoAdapter);
      photoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override public void onPageSelected(int position) {
          if (position == gankioDatas.size()) {
            tvTitle.setText("加载更多");
            tvContent.setText("");
          } else {
            tvTitle.setText(String.format("%d/%d", position + 1, gankioDatas.size()));
            tvContent.setText(gankioDatas.get(position).getDesc());
          }
        }

        @Override public void onPageScrollStateChanged(int state) {

        }
      });
      photoViewPager.setCurrentItem(currentPage);
      tvTitle.setText(String.format("%d/%d", currentPage + 1, gankioDatas.size()));
      tvContent.setText(gankioDatas.get(currentPage).getDesc());
      photoAdapter.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          presenter.getGankioData(GankioType.Fuli, 10, i++);
        }
      });
    } else {
      ToastUtils.showShort("内容为空");
    }

    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "把这个妹子存到相册?", Snackbar.LENGTH_LONG)
            .setAction("存", new View.OnClickListener() {
              @Override public void onClick(View view) {
                ToastUtils.showShort("色狼");
              }
            }).show();
      }
    });
  }

  @Override public void display(List<GankioData> gankioDatas) {
    this.gankioDatas.addAll(gankioDatas);
    photoAdapter.notifyDataSetChanged();
    int currentItem = photoViewPager.getCurrentItem();
    photoViewPager.setCurrentItem(0, false);
    photoViewPager.setCurrentItem(currentItem, false);
  }

  @Override public void displayError() {
    ToastUtils.showShort("出错");
  }
}
