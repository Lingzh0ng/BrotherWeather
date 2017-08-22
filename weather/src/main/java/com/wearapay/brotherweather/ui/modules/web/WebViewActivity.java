package com.wearapay.brotherweather.ui.modules.web;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.BWBaseActivity;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.common.utils.ToastUtils;
import com.wearapay.brotherweather.weight.GradientProgressBar;
import java.util.LinkedList;

/**
 * Created by lyz on 2017/7/5.
 */
public class WebViewActivity extends BWBaseActivity {
  @BindView(R.id.my_action_bar) LinearLayout myActionBar;
  @BindView(R.id.appbar) AppBarLayout appbar;
  @BindView(R.id.webView) WebView wvContent;
  @BindView(R.id.refreshLayout) TwinklingRefreshLayout refreshLayout;
  @BindView(R.id.progressBar) GradientProgressBar wvProgressBar;
  @BindView(R.id.floatingActionButton) FloatingActionButton floatingActionButton;

  private String currentUrl;
  private boolean showDocumentTitle = true;
  private LinkedList<String> titleStack = new LinkedList<>();
  private boolean forceClose = false;
  private boolean failing = false;

  @Override protected CharSequence getActionBarTitle() {
    return "正在加载中...";
  }

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[0];
  }

  @Override protected int getLayoutView() {
    return R.layout.activity_webview;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    String url = intent.getStringExtra("url");
    ivMenu.setImageResource(R.drawable.cancel);
    //initSonic(url);
    initWebView(url);
    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
      @Override public void onRefresh(TwinklingRefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        wvContent.loadUrl(currentUrl);
      }
    });
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "加入收藏夹?", Snackbar.LENGTH_LONG)
            .setAction("收藏", new View.OnClickListener() {
              @Override public void onClick(View view) {
                ToastUtils.showShort("成功");
              }
            })
            .show();
      }
    });
  }

  @Override protected void OnClickMenu() {
    super.OnClickMenu();
    finish();
  }

  private void initWebView(String url) {
    WebSettings webSettings = wvContent.getSettings();
    webSettings.setLoadWithOverviewMode(true);
    webSettings.setUseWideViewPort(true);
    webSettings.setJavaScriptEnabled(true);
    webSettings.setAllowFileAccess(true);
    webSettings.setDomStorageEnabled(true);

    webSettings.setAllowContentAccess(true);
    webSettings.setDatabaseEnabled(true);
    webSettings.setAppCacheEnabled(true);
    webSettings.setSavePassword(false);
    webSettings.setSaveFormData(false);

    wvContent.setWebChromeClient(new WebChromeClient() {
      @Override public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (showDocumentTitle && title != null && !failing) {
          if (!titleStack.contains(title)) {
            titleStack.addLast(title);
          }
        }
        failing = false;
      }

      @Override public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (wvProgressBar == null) {
          return;
        }
        if (newProgress == 100) {
          wvProgressBar.setCurrentProgress(100);
          new Handler().postDelayed(new Runnable() {
            @Override public void run() {
              if (wvProgressBar != null) {
                wvProgressBar.setVisibility(View.INVISIBLE);
                refreshLayout.finishRefreshing();
              }
            }
          }, 300);
        } else {
          if (View.INVISIBLE == wvProgressBar.getVisibility()) {
            wvProgressBar.setVisibility(View.VISIBLE);
          }
          wvProgressBar.setCurrentProgress(newProgress);
        }
      }
    });
    wvContent.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (wvContent != null) {
          currentUrl = url;
        }
        return false;
      }

      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (showDocumentTitle && titleStack.size() > 0) {
          tvTitle.setText(titleStack.getLast());
        }

        if (view.canGoBack()) {
          ivMenu.setVisibility(View.VISIBLE);
        } else {
          ivMenu.setVisibility(View.GONE);
        }
      }
    });
    currentUrl = url;
    wvContent.loadUrl(url);
  }

  @Override public void onBackPressed(boolean isFromKey) {
    if (!forceClose && wvContent.canGoBack()) {
      wvContent.goBack();
      if (showDocumentTitle) {
        if (titleStack.size() > 0) {
          titleStack.removeLast();
        } else {
          super.onBackPressed(isFromKey);
        }
      }
      return;
    }
    super.onBackPressed(isFromKey);
  }

  @Override protected void onDestroy() {
    if (null != wvContent) {
      wvContent.destroy();
      wvContent = null;
    }
    super.onDestroy();
  }
}
