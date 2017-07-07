package com.wearapay.brotherweather.weight;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lyz on 2017/7/5.
 */
public class PhotoViewPager extends ViewPager {
  public PhotoViewPager(Context context) {
    super(context);
  }

  public PhotoViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    try {
      return super.onInterceptTouchEvent(ev);
    } catch (IllegalArgumentException e) {
      //uncomment if you really want to see these errors
      //e.printStackTrace();
      return false;
    }
  }
}
