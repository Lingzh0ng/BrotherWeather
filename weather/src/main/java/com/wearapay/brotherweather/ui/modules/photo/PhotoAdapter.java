package com.wearapay.brotherweather.ui.modules.photo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.wearapay.brotherweather.R;
import com.wearapay.brotherweather.domain.GankioData;
import java.util.List;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by lyz on 2017/7/5.
 */
public class PhotoAdapter extends PagerAdapter {

  private List<GankioData> gankioDatas;
  private Context context;

  private View.OnClickListener onClickListener;

  public void setOnClickListener(View.OnClickListener onClickListener) {
    this.onClickListener = onClickListener;
  }

  public PhotoAdapter(List<GankioData> gankioDatas, Context context) {
    this.gankioDatas = gankioDatas;
    this.context = context;
  }

  @Override public int getCount() {
    return gankioDatas.size() + 1;
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    if (position == getCount() - 1) {
      View inflate = View.inflate(context, R.layout.add_more_view, null);
      container.addView(inflate);
      if (onClickListener != null) {
        inflate.findViewById(R.id.btn_add).setOnClickListener(onClickListener);
      }
      return inflate;
    } else {
      PhotoView photoView = new PhotoView(context);
      //ViewGroup.LayoutParams layoutParams = photoView.getLayoutParams();
      container.addView(photoView);
      Glide.with(context).load(gankioDatas.get(position).getUrl()).into(photoView);
      return photoView;
    }
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    //super.destroyItem(container, position, object);
    container.removeView((View) object);
  }

  @Override public CharSequence getPageTitle(int position) {
    return gankioDatas.get(position).getDesc();
  }
}
