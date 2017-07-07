package com.wearapay.brotherweather.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.domain.MainPager;
import java.util.List;

/**
 * Created by lyz on 2017/7/7.
 */
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {

  private List<MainPager> mainPagers;
  private Context context;
  private boolean editStatus = false;

  public SettingAdapter(List<MainPager> mainPagers, Context context) {
    this.mainPagers = mainPagers;
    this.context = context;
  }

  public boolean isEditStatus() {
    return editStatus;
  }

  public void setEditStatus(boolean editStatus) {
    this.editStatus = editStatus;
    notifyDataSetChanged();
  }

  public void setEditStatus(boolean editStatus,List<MainPager> mainPagers) {
    this.editStatus = editStatus;
    this.mainPagers = mainPagers;
    notifyDataSetChanged();
  }

  private OnDelClickListener onDelClickListener;

  public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
    this.onDelClickListener = onDelClickListener;
  }

  public interface OnDelClickListener {
    void onClick(int position, MainPager pager);

    void onitemClick(int position, MainPager pager);
  }

  @Override
  public SettingAdapter.SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflate = View.inflate(context, R.layout.setting_item, null);
    return new SettingAdapter.SettingViewHolder(inflate);
  }

  @Override public void onBindViewHolder(SettingViewHolder holder, int position) {
    holder.bindView(mainPagers.get(position));
  }

  @Override public int getItemCount() {
    return mainPagers.size();
  }

  class SettingViewHolder extends RecyclerView.ViewHolder {
    View rootView;
    @BindView(R.id.ivCancel) ImageView ivCancel;
    @BindView(R.id.tvName) TextView tvName;

    public SettingViewHolder(View itemView) {
      super(itemView);
      this.rootView = itemView;
      ButterKnife.bind(this, itemView);
    }

    public void bindView(final MainPager pager) {
      if (isEditStatus()) {
        ivCancel.setVisibility(View.VISIBLE);
      } else {
        ivCancel.setVisibility(View.GONE);
      }
      tvName.setText(pager.getType().getType());

      rootView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (onDelClickListener != null) {
            onDelClickListener.onitemClick(getLayoutPosition(), pager);
          }
        }
      });

      ivCancel.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (onDelClickListener != null) {
            onDelClickListener.onClick(getLayoutPosition(), pager);
          }
        }
      });
    }
  }
}
