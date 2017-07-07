package com.wearapay.brotherweather.weight;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import com.wearapay.brotherweather.utils.StatusBarCompat;

/**
 * Created by Kindy on 2016-05-31.
 */
public class CustomTransparentStatusBar extends View {
  public CustomTransparentStatusBar(Context context) {
    this(context, null);
  }

  public CustomTransparentStatusBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomTransparentStatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomTransparentStatusBar(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    //setBackgroundColor(Color.TRANSPARENT);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //if (isInEditMode()) {
    //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //  return;
    //}
    setMeasuredDimension(widthMeasureSpec,
        MeasureSpec.makeMeasureSpec(StatusBarCompat.getDefaultStatusHeight(getContext()),
            MeasureSpec.getMode(heightMeasureSpec)));
  }
}
