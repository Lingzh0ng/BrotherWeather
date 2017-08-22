package com.wearapay.brotherweather.ui.modules.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.wearapay.brotherweather.App;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.utils.AppUtils;
import java.util.List;

/**
 * Created by lyz on 2017/7/7.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziViewHolder> {

  private List<GankioData> mainPagers;
  private Context context;
  private final LayoutInflater layoutInflater;
  private final int width;

  public MeiziAdapter(List<GankioData> mainPagers, Context context) {
    this.mainPagers = mainPagers;
    this.context = context;
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    width = AppUtils.getScreenWidth(context) / 2;
  }

  private OnMeiziClickListener onMeiziClickListener;

  public void setOnMeiziClickListener(OnMeiziClickListener onMeiziClickListener) {
    this.onMeiziClickListener = onMeiziClickListener;
  }

  public interface OnMeiziClickListener {
    void onClick(int position, GankioData data, View view);
  }

  @Override public MeiziAdapter.MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflate = layoutInflater.inflate(R.layout.image_item, parent, false);
    return new MeiziAdapter.MeiziViewHolder(inflate);
  }

  @Override public void onBindViewHolder(MeiziViewHolder holder, int position) {
    holder.bindView(mainPagers.get(position));
  }

  @Override public int getItemCount() {
    return mainPagers.size();
  }

  class MeiziViewHolder extends RecyclerView.ViewHolder {
    View rootView;
    @BindView(R.id.iv) ImageView imageView;

    public MeiziViewHolder(View itemView) {
      super(itemView);
      this.rootView = itemView;
      ButterKnife.bind(this, itemView);
    }

    public void bindView(final GankioData pager) {

      Glide.with(context)
          .applyDefaultRequestOptions(App.getRequestOptions())
          .asBitmap()
          .thumbnail(0.1f)
          .load(pager.getUrl() + "?imageView2/0/w/" + width + "/" + "h/" + AppUtils.dip2px(context,
              300))
          .into(imageView/*new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
              //原始图片宽高
              int imageWidth = resource.getWidth();
              int imageHeight = resource.getHeight();
              //按比例收缩图片
              float ratio =
                  (float) ((imageWidth * 1.0) / (AppUtils.getScreenWidth(context) / 2 * 1.0));
              int height = (int) (imageHeight * 1.0 / ratio);
              ViewGroup.LayoutParams params = imageView.getLayoutParams();
              params.width = width;
              params.height = height;
              imageView.setImageBitmap(resource);
            }
          }*/);
      imageView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onMeiziClickListener != null) {
            onMeiziClickListener.onClick(getLayoutPosition(), pager, imageView);
          }
        }
      });
    }
  }

  @Override public void onViewDetachedFromWindow(MeiziViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    //Glide.with(context).clear();
  }
}
