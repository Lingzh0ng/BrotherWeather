package com.wearapay.brotherweather.common.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import android.view.Window;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.utils.UIUtil;

/**
 * Created by Kindy on 2016-05-11.
 */
public class BaseDialogFragment extends DialogFragment {
  public static final int CLICK_CANCEL = -1;

  protected static void showDialogFragment(FragmentManager manager, BaseDialogFragment fragment) {
    FragmentTransaction ft = manager.beginTransaction();
    //ft.setCustomAnimations(R.anim.popup_bottom_open_enter, R.anim.popup_bottom_open_exit,
    //    R.anim.popup_bottom_close_enter, R.anim.popup_bottom_close_exit);
    fragment.show(ft, null);
  }

  @Override public void onStart() {
    super.onStart();

    Window window = getDialog().getWindow();
    window.setLayout(
        UIUtil.getScreenSize(getActivity())[0] - 2 * getResources().getDimensionPixelOffset(
            R.dimen.picker_dialog_margin_lr), ViewGroup.LayoutParams.WRAP_CONTENT);
    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
  }
}
