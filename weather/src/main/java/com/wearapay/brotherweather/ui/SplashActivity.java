package com.wearapay.brotherweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.ui.modules.main.MainActivity;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyz on 2017/6/30.
 */
public class SplashActivity extends AppCompatActivity {
  @BindView(R.id.ivSplash) ImageView ivSplash;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //
    //Window window = getWindow();
    //window.getDecorView()
    //    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    //        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    //        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    //
    ////透明状态栏/导航栏
    //window.setStatusBarColor(Color.TRANSPARENT);
    //window.setNavigationBarColor(Color.TRANSPARENT);
    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    //if(Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
    //  Window window = getWindow();
    //  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    //      | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    //  window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    //      | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    //      | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    //  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    //  window.setStatusBarColor(Color.TRANSPARENT);
    //  window.setNavigationBarColor(Color.TRANSPARENT);
    //}
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    //StatusBarCompat.compat(this, 0x00000000);
    Glide.with(this).asBitmap().load(R.drawable.splash2).into(ivSplash);
    navigateUpToMain();
  }

  //@Override public void onWindowFocusChanged(boolean hasFocus) {
  //  super.onWindowFocusChanged(hasFocus);
  //  if (hasFocus && Build.VERSION.SDK_INT >= 19) {
  //    View decorView = getWindow().getDecorView();
  //    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
  //        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
  //        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
  //        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
  //        | View.SYSTEM_UI_FLAG_FULLSCREEN
  //        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
  //  }
  //}

  private void navigateUpToMain() {
    Observable.interval(0, 1, TimeUnit.SECONDS).take(3).subscribe(new Consumer<Long>() {
      @Override public void accept(@NonNull Long aLong) throws Exception {
        System.out.println("a = " + aLong);
        if (aLong == 2) {
          startActivity(new Intent(SplashActivity.this, MainActivity.class));
          finish();
        }
      }
    });
  }
}
