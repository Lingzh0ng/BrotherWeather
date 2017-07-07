package com.wearapay.brotherweather.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.wearapay.brotherweather.R;

/**
 * Created by lyz on 2017/6/29.
 */
public class MainIndicator extends View {
  private Paint mPaint;
  private Paint mSPaint;
  private int dColor;
  private int sColor;
  private float density;
  private int padding;
  private int width;
  private int docWidth;
  private int startR;
  private int top;

  private int select = 0;

  private int num = 2;
  private Path mPath;

  public MainIndicator(Context context) {
    this(context, null);
  }

  public MainIndicator(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MainIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    init();
  }

  public void setSelect(int select) {
    this.select = select;
    invalidate();
  }

  public void setNum(int num) {
    this.num = num;
    invalidate();
  }

  private void init() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mSPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPath = new Path();
    mPaint.setStyle(Paint.Style.FILL);
    mSPaint.setStyle(Paint.Style.FILL);
    dColor = getContext().getColor(R.color.hui);
    sColor = getContext().getColor(R.color.hei);
    mPaint.setColor(dColor);
    mPaint.setColor(sColor);
    density = getResources().getDisplayMetrics().density;
    padding = (int) (density * 10);
    docWidth = (int) (density * 5);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    width = getMeasuredWidth();
    //startR = width / 2 - padding / 2 - docWidth;
    startR = width / 2 - (padding * (num - 1) + docWidth * num) / 2;
    top = getMeasuredHeight() / 2;
    //System.out.println(width);
    //System.out.println(startR);
    for (int i = 0; i < num; i++) {
      Paint paint = mPaint;
      if (select == i) {
        paint = mSPaint;
      }
      if (i == 0) {
        mPath.reset();
        mPath.moveTo(startR + docWidth / 2, top);
        mPath.lineTo(startR - docWidth / 2, top);
        mPath.lineTo(startR + docWidth / 2 + docWidth - 1, top - docWidth + 1);
        mPath.lineTo(startR + docWidth / 2, top + docWidth);
        mPath.close();
        canvas.drawPath(mPath, paint);
        //System.out.println(startR + docWidth / 2);
      } else {
        canvas.drawCircle(startR + padding * i + docWidth / 2, top, docWidth / 2, paint);
        //System.out.println(startR + padding * i + docWidth / 2);
      }
    }
    //if (select == 0) {
    //  mPaint.setColor(sColor);
    //} else {
    //  mPaint.setColor(dColor);
    //}
    //mPath.reset();
    //mPath.moveTo(startR + docWidth / 2, top);
    //mPath.lineTo(startR - docWidth / 2, top);
    //mPath.lineTo(startR + docWidth / 2 + docWidth - 1, top - docWidth + 1);
    //mPath.lineTo(startR + docWidth / 2, top + docWidth);
    //mPath.close();
    //canvas.drawPath(mPath, mPaint);
    ////canvas.drawCircle(startR + docWidth / 2, top, docWidth / 2, mPaint);
    //System.out.println(startR + docWidth / 2);
    //if (select == 0) {
    //  mPaint.setColor(dColor);
    //} else {
    //  mPaint.setColor(sColor);
    //}
    //canvas.drawCircle(width / 2 + padding / 2 + docWidth / 2, top, docWidth / 2, mPaint);
    //System.out.println(width / 2 + padding / 2 + docWidth / 2);
  }
}
