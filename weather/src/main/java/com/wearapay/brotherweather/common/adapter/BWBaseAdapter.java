package com.wearapay.brotherweather.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wearapay.brotherweather.R;
import java.util.List;

/**
 * Created by lyz on 2017/6/29.
 */
public class BWBaseAdapter<T> extends RecyclerView.Adapter<BWBaseAdapter.BaseViewHolder> {
  private Context mContext;

  private List<T> list;

  public BWBaseAdapter(Context context, List<T> list) {
    this.mContext = context;
    this.list = list;
  }

  @Override public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    BaseViewHolder holder =
        new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    return holder;
  }

  public void onBindViewHolder(BWBaseAdapter.BaseViewHolder holder, int position) {
    holder.tv.setText(list.get(position).toString());
  }

  @Override public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }

  @Override public int getItemCount() {
    return list.size();
  }

  class BaseViewHolder extends RecyclerView.ViewHolder {
    TextView tv;

    public BaseViewHolder(View itemView) {
      super(itemView);
      tv = (TextView) itemView.findViewById(R.id.tv);
    }
  }
}
