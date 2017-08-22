package com.wearapay.brotherweather.weight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.listener.AppBarStateChangeListener;
import com.wearapay.brotherweather.utils.AppUtils;

/**
 * Created by lyz on 2017/7/4.
 */
public class CustomRefreshLayout extends FrameLayout {

  private float density;
  private View mHeaderView;
  private int mHeaderViewHeight;
  private int mHeaderViewPadding;
  private int currentPadding;
  private float v;
  private float lastMove;
  private ImageView ivArrow;
  private ImageView ivLoading;
  private TextView tv;
  private View mFooterView;
  private int mFooterViewHeight;
  private int mHFooterViewPadding;
  private boolean openLoadMore;
  private float lastY;

  public int getAppBarHeight() {
    return appBarHeight;
  }

  public void setAppBarHeight(int appBarHeight) {
    this.appBarHeight = appBarHeight;
  }

  private int appBarHeight;

  private float mDownY;

  private RecyclerView recyclerView;

  public void setRecyclerView(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
  }

  private AppBarStateChangeListener.State appBarStatus = AppBarStateChangeListener.State.EXPANDED;
  private AppBarStateChangeListener.State lastAppBarStatus =
      AppBarStateChangeListener.State.EXPANDED;

  private int recylerViewPosition;

  private RefreshStatus currentStatus = RefreshStatus.CLOSE;
  private int measuredWidth;

  enum RefreshStatus {
    REFRESHING, CLOSE, OPEN, COMPLETE, LOAD_MORE
  }

  private OnRefreshListener onRefreshListener;

  public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
    this.onRefreshListener = onRefreshListener;
  }

  public interface OnRefreshListener {
    void onRefresh();

    void onLoadMore();
  }

  private void updateStatus() {
    System.out.println("currentStatus : " + currentStatus);
    if (currentStatus == RefreshStatus.OPEN && recylerViewPosition == 0) {
      if (v > 0 && v <= mHeaderViewHeight) {
        //TODO
        tv.setText("下拉刷新");
        ivArrow.setVisibility(VISIBLE);
        ivLoading.setVisibility(GONE);
      } else if (v > mHeaderViewHeight && v <= mHeaderViewPadding) {
        ivArrow.setVisibility(VISIBLE);
        ivLoading.setVisibility(GONE);
        tv.setText("释放刷新");
      }
      rotateImageView(ivArrow, v);
    } else if (currentStatus == RefreshStatus.CLOSE) {
      v = 0;
      //getChildAt(0).setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
      getChildAt(1).setPadding(0, mFooterViewHeight, 0, 0);
      getChildAt(2).setPadding(0, (int) v, 0, 0);
      rotateImageView(ivArrow, v);
    } else if (currentStatus == RefreshStatus.REFRESHING) {
      v = mHeaderViewHeight;
      ivArrow.setVisibility(GONE);
      ivLoading.setVisibility(VISIBLE);
      startLoadingAnimation(ivLoading);
      tv.setText("正在刷新");
      //getChildAt(0).setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
      getChildAt(2).setPadding(0, (int) v, 0, 0);
      if (onRefreshListener != null) {
        onRefreshListener.onRefresh();
      }
    } else if (currentStatus == RefreshStatus.COMPLETE) {
      currentStatus = RefreshStatus.CLOSE;
      ivArrow.setVisibility(GONE);
      ivLoading.setVisibility(GONE);
      ivLoading.clearAnimation();
      tv.setText("刷新成功");
      updateStatus();
    } else if (currentStatus == RefreshStatus.LOAD_MORE) {
      if (onRefreshListener != null) {
        onRefreshListener.onLoadMore();
      }
    }
  }

  public void finishRefresh() {
    if (currentStatus == RefreshStatus.REFRESHING || currentStatus == RefreshStatus.LOAD_MORE) {
      currentStatus = RefreshStatus.COMPLETE;
      updateStatus();
    }
  }

  private void rotateImageView(ImageView imageView, float spend) {
    imageView.setPivotX(imageView.getWidth() / 2);
    imageView.setPivotY(imageView.getHeight() / 2);
    imageView.setRotation(spend / mHeaderViewPadding * 180);
  }

  private void startLoadingAnimation(ImageView imageView) {
    RotateAnimation loadingAnimation =
        new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f);
    loadingAnimation.setDuration(1000);
    loadingAnimation.setRepeatCount(-1);
    loadingAnimation.setInterpolator(new LinearInterpolator());
    loadingAnimation.setFillAfter(false);
    imageView.setAnimation(loadingAnimation);
    loadingAnimation.start();
  }

  public AppBarStateChangeListener.State getAppBarStatus() {
    return appBarStatus;
  }

  public void setAppBarStatus(AppBarStateChangeListener.State appBarStatus) {
    this.appBarStatus = appBarStatus;
  }

  public int getRecylerViewPosition() {
    return recylerViewPosition;
  }

  public void setRecylerViewPosition(int recylerViewPosition) {
    this.recylerViewPosition = recylerViewPosition;
  }

  public CustomRefreshLayout(Context context) {
    this(context, null);
  }

  public CustomRefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    density = AppUtils.getDensity(getContext());
    mHeaderView = View.inflate(getContext(), R.layout.view_header, null);
    ViewGroup.LayoutParams layoutParams =
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    layoutParams.height = (int) (density * 66);
    mHeaderView.setLayoutParams(layoutParams);
    this.addView(mHeaderView);
    ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
    ivLoading = (ImageView) mHeaderView.findViewById(R.id.iv_loading);
    tv = (TextView) mHeaderView.findViewById(R.id.tv);
    mHeaderViewHeight = layoutParams.height;
    mHeaderViewPadding = (int) (mHeaderViewHeight);
    mFooterView = View.inflate(getContext(), R.layout.add_more_footer, null);
    FrameLayout.LayoutParams footerLayoutParams =
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT);
    footerLayoutParams.height = (int) (density * 44);
    footerLayoutParams.gravity = Gravity.BOTTOM;
    mFooterViewHeight = footerLayoutParams.height;
    mHFooterViewPadding = footerLayoutParams.height;
    mFooterView.setLayoutParams(footerLayoutParams);
    this.addView(mFooterView);
    //mHeaderView.setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
    mFooterView.setPadding(0, mFooterViewHeight, 0, 0);
    //getChildAt(2).setPadding(0, (int) v, 0, 0);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    measuredWidth = getMeasuredWidth();
  }

  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (getChildCount() != 3) throw new IllegalArgumentException("must 3 child");
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      lastAppBarStatus = getAppBarStatus();
      mDownY = ev.getY();
    }
    if (appBarStatus == AppBarStateChangeListener.State.EXPANDED
        && recylerViewPosition == 0
        && currentStatus != RefreshStatus.REFRESHING) {

      switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
          mDownY = ev.getY();
          break;
        case MotionEvent.ACTION_MOVE:
          if (lastY == 0) {
            lastY = ev.getY();
          }
          v = v + (ev.getY() - lastY);
          lastY = ev.getY();
          //v = ev.getY() - mDownY;
          if (v > 0 && v <= mHeaderViewPadding) {
            currentStatus = RefreshStatus.OPEN;
            if (lastAppBarStatus != AppBarStateChangeListener.State.COLLAPSED) {
              //getChildAt(0).setPadding(0, (int) (-mHeaderViewHeight + v), 0, 0);
              getChildAt(2).setPadding(0, (int) v, 0, 0);
              System.out.println("x: " + getChildAt(0).getPaddingTop());
            } else {
              //getChildAt(0).setPadding(0, (int) (-mHeaderViewHeight + v + appBarHeight), 0, 0);
              getChildAt(2).setPadding(0, (int) v + appBarHeight, 0, 0);
            }
            System.out.println(v);

            updateStatus();
            return true;
          }
          if (v > mHeaderViewPadding) {
            v = mHeaderViewPadding;
          }
          break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
          mDownY = 0;
          lastY = 0;
          if (v > 0 && v <= mHeaderViewPadding / 2) {
            currentStatus = RefreshStatus.CLOSE;
            updateStatus();
          } else if (v > mHeaderViewPadding / 2 && v <= mHeaderViewPadding) {
            currentStatus = RefreshStatus.REFRESHING;
            updateStatus();
          }
          return super.dispatchTouchEvent(ev);
      }
    } /*else {
      if (appBarStatus == AppBarStateChangeListener.State.IDLE
          && recylerViewPosition == 0
          && currentStatus == RefreshStatus.OPEN) {
        //System.out.println("111111111111");
        switch (ev.getAction()) {
          case MotionEvent.ACTION_MOVE:
            v = lastMove - ev.getY();
            System.out.println(v + "   ");
            if (v > 0 && v <= mHeaderViewHeight || lastMove != 0) {
              getChildAt(0).layout(0, (int) (-mHeaderViewHeight + v), measuredWidth, (int) v);
              getChildAt(1).layout(0, (int) v, measuredWidth, (int) (getMeasuredHeight() + v));
              lastMove = ev.getY();
              return true;
            }
            lastMove = ev.getY();
            break;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            lastMove = 0;
            if (v > 0 && v <= mHeaderViewHeight / 2) {
              currentStatus = RefreshStatus.CLOSE;
            } else if (v > mHeaderViewHeight / 2 && v <= mHeaderViewHeight) {
              currentStatus = RefreshStatus.REFRESHING;
            }
            v = 0;
            getChildAt(0).layout(0, (int) (-mHeaderViewHeight + v), measuredWidth, (int) v);
            getChildAt(1).layout(0, (int) v, measuredWidth, (int) (getMeasuredHeight() + v));
            break;
        }
      }
    }*/

    if (isSlideToBottom(recyclerView) && currentStatus != RefreshStatus.LOAD_MORE) {
      switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
          mDownY = ev.getY();
          break;
        case MotionEvent.ACTION_MOVE:
          if (lastY == 0) {
            lastY = ev.getY();
          }
          openLoadMore = true;

          v = v + (lastY - ev.getY());
          //if (lastY - ev.getY() > 10) {//向下
          //} else {
          //  v = mDownY - ev.getY();
          //}
          lastY = ev.getY();
          System.out.println(v);
          if (v > 0 && v <= mFooterViewHeight) {
            currentStatus = RefreshStatus.OPEN;
            getChildAt(2).setPadding(0, 0, 0, (int) (v));
            getChildAt(1).setPadding(0, (int) (mFooterViewHeight - v), 0, 0);
            System.out.println("mDownY: " + mDownY);
            updateStatus();
            return true;
          }
          if (v > mFooterViewHeight) {
            v = mFooterViewHeight;
          }
          if (v < 0) {
            v = 0;
          }
          break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
          openLoadMore = false;
          mDownY = 0;
          lastY = 0;
          if (v > 0 && v <= mFooterViewHeight / 2) {
            currentStatus = RefreshStatus.CLOSE;
            updateStatus();
          } else if (v > mFooterViewHeight / 2 && v <= mFooterViewHeight) {
            currentStatus = RefreshStatus.LOAD_MORE;
            updateStatus();
          }
          return super.dispatchTouchEvent(ev);
      }
    }
    if ((ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL)
        && currentStatus == RefreshStatus.OPEN) {
      mDownY = 0;
      if (recylerViewPosition == 0) {
        if (v > 0 && v <= mHeaderViewPadding / 2) {
          currentStatus = RefreshStatus.CLOSE;
          updateStatus();
        } else if (v > mHeaderViewPadding / 2 && v <= mHeaderViewPadding) {
          currentStatus = RefreshStatus.REFRESHING;
          updateStatus();
        }
      } else {
        openLoadMore = false;
        if (v > 0 && v <= mFooterViewHeight / 2) {
          currentStatus = RefreshStatus.CLOSE;
          updateStatus();
        } else if (v > mFooterViewHeight / 2 && v <= mFooterViewHeight) {
          currentStatus = RefreshStatus.LOAD_MORE;
          updateStatus();
        }
      }
      if (currentStatus != RefreshStatus.LOAD_MORE
          || currentStatus != RefreshStatus.REFRESHING) {
        openLoadMore = false;
        v = 0;
        post(new Runnable() {
          @Override public void run() {
            getChildAt(1).setPadding(0, mFooterViewHeight, 0, 0);
            getChildAt(2).setPadding(0, 0, 0, 0);
          }
        });
      }
    }
    if (currentStatus == RefreshStatus.CLOSE) {
      openLoadMore = false;
      v = 0;
      post(new Runnable() {
        @Override public void run() {
          getChildAt(1).setPadding(0, mFooterViewHeight, 0, 0);
          getChildAt(2).setPadding(0, 0, 0, 0);
        }
      });
    }
    return super.dispatchTouchEvent(ev);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    return super.onInterceptTouchEvent(ev);
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    return true;
  }

  protected boolean isSlideToBottom(RecyclerView recyclerView) {
    if (recyclerView == null) return false;
    if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
        >= recyclerView.computeVerticalScrollRange()) {
      return true;
    }
    return openLoadMore;
  }
}
