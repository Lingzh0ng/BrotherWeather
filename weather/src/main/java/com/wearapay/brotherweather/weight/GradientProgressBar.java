package com.wearapay.brotherweather.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.utils.AppUtils;

/**
 * Created by lyz on 2017/3/17.
 */
public class GradientProgressBar extends View {

  private Paint pbPaint;

  private Paint bgPaint;

  private RectF pbRect;
  private RectF bgRect;
  private RectF borderRect;

  private float mWidth;
  private float mHeight;

  private boolean isRoundRect = true;

  private float currentProgress;
  private float maxProgress = 100f;//最大进度默认100

  private int borderColor = getContext().getResources().getColor(R.color.colorAccent);
  private int bgColor = getContext().getResources().getColor(R.color.hui);
  private int[] colors = getContext().getResources().getIntArray(R.array.gradients);
  private float radius;

  public float getMaxProgress() {
    return maxProgress;
  }

  public void setMaxProgress(float maxProgress) {
    this.maxProgress = maxProgress;
    invalidate();
  }

  public float getCurrentProgress() {
    return currentProgress;
  }

  public void setCurrentProgress(float currentProgress) {
    this.currentProgress = currentProgress;
    invalidate();
  }

  public GradientProgressBar(Context context) {
    this(context, null);
  }

  public GradientProgressBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public GradientProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientProgressBar);
    radius = a.getFloat(R.styleable.GradientProgressBar_pp_round_radius, 0f);
    maxProgress = a.getFloat(R.styleable.GradientProgressBar_pp_max_progress, 100f);
    currentProgress = a.getFloat(R.styleable.GradientProgressBar_pp_current_progress, 0f);
    isRoundRect = a.getBoolean(R.styleable.GradientProgressBar_pp_is_round, true);
    a.recycle();
    init();
  }

  private void init() {
    pbPaint = new Paint();
    pbPaint.setAntiAlias(true);
    pbPaint.setStyle(Paint.Style.FILL);

    bgPaint = new Paint();
    bgPaint.setAntiAlias(true);
  }

  @Override protected void onDraw(Canvas canvas) {
    initCurrentRect();
    canvas.save();
    if (radius == 0 && isRoundRect) {
      radius = mHeight / 2;
    } else {
      radius = 0;
    }
    canvas.drawRoundRect(borderRect, radius, radius, bgPaint);
    bgPaint.setStyle(Paint.Style.STROKE);
    bgPaint.setColor(borderColor);

    bgPaint.setStyle(Paint.Style.FILL);
    bgPaint.setColor(bgColor);
    canvas.drawRoundRect(bgRect, radius, radius, bgPaint);

    canvas.drawRoundRect(pbRect, radius, radius, pbPaint);

    canvas.restore();
  }

  private void initCurrentRect() {
    float ratio = currentProgress / maxProgress;
    pbRect = new RectF(1, 1, (mWidth - 1) * ratio, mHeight - 1);
    LinearGradient linearGradient =
        new LinearGradient(1, 1, (mWidth - 1) * ratio, mHeight - 1, colors, null,
            Shader.TileMode.MIRROR);
    pbPaint.setShader(linearGradient);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) {
      mWidth = widthSize;
    }

    if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.UNSPECIFIED) {
      mHeight = AppUtils.getDensity(getContext()) * 3;
    } else {
      mHeight = heightSize;
    }
    setMeasuredDimension((int) mWidth, (int) mHeight);

    borderRect = new RectF(0, 0, mWidth, mHeight);
    bgRect = new RectF(1, 1, mWidth - 1, mHeight - 1);
  }
}
