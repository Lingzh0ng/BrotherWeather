package com.wearapay.brotherweather.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lyz on 2017/6/29.
 */
public class StatusBarTextColorUtils {
  public static int StatusBarLightMode(Activity activity,Boolean dark) {
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (MIUISetStatusBarLightMode(activity.getWindow(), dark)) {//判断是不是小米系统
        result = 1;
      } else if (FlymeSetStatusBarLightMode(activity.getWindow(), dark)) {//判断是不是魅族系统
        result = 2;
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前是不是6.0以上的系统
        setWindowLightStatusBar(activity,dark);
        result = 3;
      }
    }
    return result;
  }

  ////带有透明颜色的状态栏
  //public static void setTranslucentForCoordinatorLayout(Activity activity, int statusBarAlpha) {
  //  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
  //    return;
  //  }
  //  transparentStatusBar(activity);//先将状态栏设置为完全透明
  //  //addTranslucentView(activity, statusBarAlpha);//添加一个自定义透明度的矩形状态栏
  //}

  public static void setWindowLightStatusBar(Activity activity, Boolean lightStatusBar) {
    Window window = activity.getWindow();
    View decor = window.getDecorView();
    //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    int ui = decor.getSystemUiVisibility();
    if (lightStatusBar) {
      ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
    } else {
      ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
    }
    decor.setSystemUiVisibility(ui);
  }

  /**
   * 使状态栏透明
   */
  @TargetApi(Build.VERSION_CODES.KITKAT) private static void transparentStatusBar(
      Activity activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    } else {
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }
  /**
   * 添加半透明矩形条
   *
   * @param activity       需要设置的 activity
   * @param statusBarAlpha 透明值
   */
  //private static void addTranslucentView(Activity activity, int statusBarAlpha) {
  //  ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
  //  if (contentView.getChildCount() > 1) {
  //    contentView.getChildAt(1).setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
  //  } else {
  //    contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
  //  }
  //}

  ///**
  // * 创建半透明矩形 View
  // *
  // * @param alpha 透明值
  // * @return 半透明 View
  // */
  //private static XStatusBar.StatusBarView createTranslucentStatusBarView(Activity activity, int alpha) {
  //  // 绘制一个和状态栏一样高的矩形
  //  StatusBarView statusBarView = new StatusBarView(activity);
  //  LinearLayout.LayoutParams params =
  //      new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
  //  statusBarView.setLayoutParams(params);
  //  statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
  //  return statusBarView;
  //}

  /**
   * 获取状态栏高度
   *
   * @param context context
   * @return 状态栏高度
   */
  private static int getStatusBarHeight(Context context) {
    // 获得状态栏高度
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    return context.getResources().getDimensionPixelSize(resourceId);
  }

  /**
   * 修改小米手机系统的
   */
  public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
      Class clazz = window.getClass();
      try {
        int darkModeFlag = 0;
        Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
        darkModeFlag = field.getInt(layoutParams);
        Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
        if (dark) {
          extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
        } else {
          extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
        }
        result = true;
      } catch (Exception e) {

      }
    }
    return result;
  }

  /**
   * 魅族手机修改该字体颜色
   */
  public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
    boolean result = false;
    if (window != null) {
      try {
        WindowManager.LayoutParams lp = window.getAttributes();
        Field darkFlag =
            WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
        Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
        darkFlag.setAccessible(true);
        meizuFlags.setAccessible(true);
        int bit = darkFlag.getInt(null);
        int value = meizuFlags.getInt(lp);
        if (dark) {
          value |= bit;
        } else {
          value &= ~bit;
        }
        meizuFlags.setInt(lp, value);
        window.setAttributes(lp);
        result = true;
      } catch (Exception e) {

      }
    }
    return result;
  }
}
