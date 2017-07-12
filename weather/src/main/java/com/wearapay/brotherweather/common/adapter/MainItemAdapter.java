package com.wearapay.brotherweather.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.utils.AppUtils;
import java.util.List;

/**
 * Created by lyz on 2017/7/4.
 */
public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.MainViewHolder> {

  private List<GankioData> list;
  private Context context;
  private OnItemClickListener onItemClickListener;
  private final LayoutInflater layoutInflater;

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {
    void onClick(int position, GankioData gankioData);
  }

  public MainItemAdapter(List<GankioData> list, Context context) {
    this.list = list;
    this.context = context;
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflate = layoutInflater.inflate(R.layout.main_item, parent, false);
    return new MainViewHolder(inflate);
  }

  @Override public void onBindViewHolder(MainViewHolder holder, int position) {
    holder.bindView(list.get(position));
  }

  @Override public int getItemCount() {
    return list.size();
  }

  class MainViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ivIcon) ImageView ivIcon;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvSource) TextView tvSource;
    @BindView(R.id.tvTime) TextView tvTime;
    @BindView(R.id.tvName) TextView tvName;
    private View rootView;

    public MainViewHolder(View itemView) {
      super(itemView);
      rootView = itemView;
      ButterKnife.bind(this, itemView);
    }

    public void bindView(final GankioData gankioData) {
      if (gankioData.getImages() != null && gankioData.getImages().size() > 0) {
        ivIcon.setVisibility(View.VISIBLE);
        Glide.with(context)
            .applyDefaultRequestOptions(App.getRequestOptions())
            .load(gankioData.getImages().get(0) + "?imageView2/0/w/" + AppUtils.getScreenWidth(
                context) + "/" + "h/" + AppUtils.dip2px(context, 200))
            .into(ivIcon);
      } else {
        ivIcon.setVisibility(View.GONE);
      }
      if (gankioData.isBrowseHistory()) {
        tvTitle.setTextColor(
            context.getResources().getColor(R.color.upgrade_firmware_progress_end));
      } else {
        tvTitle.setTextColor(context.getResources().getColor(R.color.text_color_black));
      }
      tvTitle.setText(gankioData.getDesc());
      tvSource.setText(gankioData.getSource());
      tvName.setText(gankioData.getWho());
      tvTime.setText(AppUtils.formatUTCData(gankioData.getCreatedAt()));
      rootView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (onItemClickListener != null) {
            onItemClickListener.onClick(getLayoutPosition(), gankioData);
          }
        }
      });
    }
  }
}
