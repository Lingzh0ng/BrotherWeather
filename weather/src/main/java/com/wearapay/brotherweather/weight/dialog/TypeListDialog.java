package com.wearapay.brotherweather.weight.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.common.base.BaseDialogFragment;
import com.wearapay.brotherweather.domain.MainPager;
import java.util.List;

/**
 * Created by lyz on 2017/7/10.
 */

public class TypeListDialog extends BaseDialogFragment {
  @BindView(R.id.tvTitle) TextView tvTitle;
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  private List<MainPager> types;
  private String title;
  private static TypeListDialog typeListDialog;
  private TypeListAdapter adapter;

  public static TypeListDialog show(FragmentManager fragmentManager, List<MainPager> types,
      String title) {
    if (typeListDialog == null) {
      typeListDialog = new TypeListDialog();
    }
    typeListDialog.title = title;
    typeListDialog.types = types;
    showDialogFragment(fragmentManager, typeListDialog);
    return typeListDialog;
  }

  @Override public void onStart() {
    super.onStart();
    final Window window = getDialog().getWindow();
    window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    window.setWindowAnimations(R.style.PopupWindow_Animation_Bottom);
    final WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.gravity = Gravity.CENTER;
    window.setAttributes(layoutParams);
    final ViewGroup content = (ViewGroup) window.findViewById(android.R.id.content);
    FrameLayout.LayoutParams contentParams =
        (FrameLayout.LayoutParams) content.getChildAt(0).getLayoutParams();
    contentParams.gravity = Gravity.CENTER;
    content.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view != content.getChildAt(0)) {
          getDialog().dismiss();
          return true;
        }
        return false;
      }
    });
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View inflate = inflater.inflate(R.layout.type_list_view, null);
    ButterKnife.bind(this, inflate);
    builder.setView(inflate);
    AlertDialog dialog = builder.create();
    dialog.setCanceledOnTouchOutside(true);
    init();
    return dialog;
  }

  private void init() {
    tvTitle.setText(title);
    adapter = new TypeListAdapter(types, getContext());
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
    adapter.setOnTypeItemClickListener(new TypeListAdapter.OnTypeItemClickListener() {
      @Override public void onItemClick(int position, MainPager mainPager) {
        mainPager.setSelect(!mainPager.isSelect());
      }

      @Override public void onOKClick(List<MainPager> list) {
        if (onTypeOKListener != null) {
          onTypeOKListener.onOK(list);
        }
        dismiss();
      }
    });
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    typeListDialog = null;
    types = null;
    onTypeOKListener = null;
  }

  private OnTypeOKListener onTypeOKListener;

  public void setOnTypeOKListener(OnTypeOKListener onTypeOKListener) {
    this.onTypeOKListener = onTypeOKListener;
  }

  public interface OnTypeOKListener {
    void onOK(List<MainPager> list);
  }
}
