package com.wearapay.brotherweather.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

/**
 * Created by lyz54 on 2017/6/27.
 */

public class AppUtils {
  /**
   * 修复InputMethodManager内存泄露
   */
  public static void fixInputMethodManagerLeak(final Context destContext) {
    if (destContext == null) {
      return;
    }

    InputMethodManager imm =
        (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm == null) {
      return;
    }

    String[] arr = new String[] { "mCurRootView", "mServedView", "mNextServedView" };
    Field f = null;
    Object obj_get = null;
    for (int i = 0; i < arr.length; i++) {
      String param = arr[i];
      try {
        f = imm.getClass().getDeclaredField(param);
        if (f.isAccessible() == false) {
          f.setAccessible(true);
        } // author: sodino mail:sodino@qq.com
        obj_get = f.get(imm);
        if (obj_get != null && obj_get instanceof View) {
          View v_get = (View) obj_get;
          if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
            f.set(imm, null); // 置空，破坏掉path to gc节点
          } else {
            // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
            Log.d("PayPOS", "fixInputMethodManagerLeak break, context is not suitable, get_context="
                + v_get.getContext()
                + " dest_context="
                + destContext);
            break;
          }
        }
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }
  }

  public static boolean isEventInVIew(final View view, final MotionEvent event) {
    if (view != null) {
      int[] leftTop = { 0, 0 };
      //获取view当前的location位置
      view.getLocationInWindow(leftTop);
      int left = leftTop[0];
      int top = leftTop[1];
      int bottom = top + view.getHeight();
      int right = left + view.getWidth();
      if (event.getRawX() > left
          && event.getRawX() < right
          && event.getRawY() > top
          && event.getRawY() < bottom) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  /**
   * 隐藏键盘
   */
  public static boolean hideInput(final Context destContext, final View view) {
    InputMethodManager imm =
        (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      return true;
    }
    return false;
  }

  /**
   * 显示键盘
   */
  public static boolean showInput(final Context destContext, final View view) {
    view.requestFocus();
    InputMethodManager imm =
        (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      //imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
      imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
      return true;
    }
    return false;
  }

  public static float getDensity(final Context destContext) {
    return destContext.getResources().getDisplayMetrics().density;
  }

  public static void endWithAnimation(final View view, final Supplier<Void> func) {
    Animation animation = view.getAnimation();
    if (animation != null && animation.hasStarted()) {
      animation.setAnimationListener(new Animation.AnimationListener() {
        @Override public void onAnimationStart(Animation animation) {

        }

        @Override public void onAnimationEnd(Animation animation) {
          view.clearAnimation();
          func.get();
        }

        @Override public void onAnimationRepeat(Animation animation) {

        }
      });
      animation.cancel();
    } else {
      view.clearAnimation();
      func.get();
    }
  }

  public static String formatUTCData(String utc) {
    //String ts = "2007-10-23T17:15:44.000Z";
    //System.out.println("ts = " + utc);
    utc = utc.replace("Z", " UTC");
    //System.out.println("ts = " + utc);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    try {
      Date dt = sdf.parse(utc);
      utc = format.format(dt);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return utc;
  }

  public static int getScreenWidth(Context context) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    return displayMetrics.widthPixels;
  }

  public static int dip2px(Context context, float dpVale) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpVale * scale + 0.5f);
  }

  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }
}
