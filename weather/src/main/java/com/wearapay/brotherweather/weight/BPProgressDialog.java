package com.wearapay.brotherweather.weight;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.wearapay.brotherweather.R;

/**
 * Created by swang on 5/10/16.
 */
public class BPProgressDialog extends ProgressDialog {

  ImageView ivProgress;
  Animation animation;
  TextView tvMessage;
  String message;

  public BPProgressDialog(Context context, String msg) {
    super(context);
    setCanceledOnTouchOutside(false);
    this.message = msg;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.progress_dialog);
    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    ivProgress = (ImageView) findViewById(R.id.ivProgress);
    //tvMessage = (TextView) findViewById(R.id.tvProgressMsg);
    //if (!StringUtil.isEmpty(message)) {
    //  tvMessage.setVisibility(View.VISIBLE);
    //  tvMessage.setText(this.message);
    //} else {
    //  tvMessage.setVisibility(View.GONE);
    //}
    animation =
        new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f);
    animation.setInterpolator(new LinearInterpolator());
    animation.setRepeatCount(Animation.INFINITE);
    animation.setDuration(500);
  }

  @Override public void show() {
    super.show();
    ivProgress.startAnimation(animation);
  }

  @Override public void dismiss() {
    super.dismiss();
    ivProgress.setAnimation(null);
  }
}
