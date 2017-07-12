package com.wearapay.brotherweather.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by leo on 4/18/16.
 */
public class UIUtil {

  public static Toast showCancelToast(Context context, String message) {
    Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
    return toast;
  }

  public static Toast showCancelToast(Context context, int messageId) {
    Toast toast = Toast.makeText(context, messageId, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
    return toast;
  }

  public static void showToast(Context context, String message) {
    Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
  }

  public static void showToast(Context context, int messageId) {
    Toast toast = Toast.makeText(context, messageId, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
  }

  public static int[] getScreenSize(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics metrics = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(metrics);
    return new int[] { metrics.widthPixels, metrics.heightPixels };
  }

  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static int dp2px(Context context, float dp) {
    //		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());

    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  public static float dp2pxF(Context context, float dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return dp * scale;
  }

  public static int px2dp(Context context, float px) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (px / scale + 0.5f);
  }

  public static int px2sp(Context context, float px) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (px / fontScale + 0.5f);
  }

  public static float px2spF(Context context, float px) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return px / fontScale;
  }

  public static int sp2px(Context context, float sp) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (sp * fontScale + 0.5f);
  }

  /**
   * @param px must be larger than 0
   */
  public static void setTextViewSize(Context context, TextView textView, int px) {
    if (px > 0) {
      textView.setTextSize(px2sp(context, px));
    }
  }

  public static void setTextViewColor(Context context, TextView textView, int colorId) {
    if (colorId != 0) {
      textView.setTextColor(colorId);
    }
  }

  public static void setTextViewColorStateList(Context context, TextView textView, int colorId) {
    if (colorId != 0) {
      textView.setTextColor(context.getResources().getColorStateList(colorId));
    }
  }

  public static boolean setStatusBarDarkMode(Activity activity, boolean darkMode) {
    //L.o(activity, "Build.MANUFACTURER = " + Build.MANUFACTURER + "  Build.MODEL = " + Build.MODEL);
    if (Build.MANUFACTURER.equals("Xiaomi")) {
      return setMiuiStatusBarDarkMode(activity, darkMode);
    } else {
      try {
        // Invoke Build.hasSmartBar()
        final Method method = Build.class.getMethod("hasSmartBar");
        if (method != null) {
          return setMeizuStatusBarDarkIcon(activity, darkMode);
        }
      } catch (Exception e) {
      }
    }
    return false;
  }

  //MIUI6+
  public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkMode) {

    Class<? extends Window> clazz = activity.getWindow().getClass();
    try {
      int darkModeFlag = 0;
      Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
      Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
      darkModeFlag = field.getInt(layoutParams);
      Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
      extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
      return true;
    } catch (Exception e) {
    }
    return false;
  }

  //Flyme4+
  public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
    boolean result = false;
    if (activity != null) {
      try {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
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
        activity.getWindow().setAttributes(lp);
        result = true;
      } catch (Exception e) {
      }
    }
    return result;
  }
}
