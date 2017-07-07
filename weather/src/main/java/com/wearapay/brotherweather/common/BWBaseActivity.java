package com.wearapay.brotherweather.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.base.BaseActivity;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.common.mvp.IBaseView;
import com.wearapay.brotherweather.common.utils.ToastUtils;
import com.wearapay.brotherweather.weight.BPProgressDialog;

/**
 * Created by lyz54 on 2017/6/28.
 */

public abstract class BWBaseActivity extends BaseActivity implements IBaseView {

  protected BasePresenter[] presenters;
  protected ImageView ivBack;
  protected ImageView ivMenu;
  protected TextView tvTitle;
  private View myActBar;
  private BPProgressDialog progressDialog;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (presenters == null) {
      presenters = initPresenters();
      if (presenters != null && presenters.length > 0) {
        for (int i = 0; i < presenters.length; i++) {
          System.out.println("presenters");
          presenters[i].setView(this);
        }
      }
    }

    myActBar = findViewById(R.id.my_action_bar);
    if (myActBar != null) {
      ivBack = (ImageView) myActBar.findViewById(R.id.ivBack);
      ivMenu = (ImageView) myActBar.findViewById(R.id.ivMenu);
      tvTitle = (TextView) myActBar.findViewById(R.id.tvTitle);
      tvTitle.setText(getActionBarTitle());
      ivBack.setVisibility(View.VISIBLE);
      ivBack.setOnClickListener(innerOnClickListener);
      ivMenu.setOnClickListener(innerOnClickListener);
    }
  }

  private View.OnClickListener innerOnClickListener = new View.OnClickListener() {
    @Override public void onClick(View view) {
      if (view == ivBack) {
        onBackPressed(true);
      } else if (view == ivMenu) {
        OnClickMenu();
      }
    }
  };

  protected void OnClickMenu() {
  }

  protected abstract CharSequence getActionBarTitle();

  @Override protected void onStart() {
    super.onStart();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (presenters != null && presenters.length > 0) {
      for (int i = 0; i < presenters.length; i++) {
        presenters[i].onDestroy();
      }
    }
  }

  protected abstract BasePresenter[] initPresenters();

  @Override public void showMessage(String message) {
    ToastUtils.showShort(message);
  }

  @Override public void showMessage(int messageId) {
    ToastUtils.showShort(messageId);
  }

  @Override public void showDiglog(String message) {

  }

  @Override public void showProgress(String message) {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, message);
    }
    progressDialog.show();
  }

  @Override public void showProgress(int messageResourceId) {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, getString(messageResourceId));
    }
    progressDialog.show();
  }

  @Override public void hideProgress() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  @Override public void processFail(Throwable t, String errorMessage) {

  }

  @Override public void navToHomePage() {

  }
}
