package com.wearapay.brotherweather.weight.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
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
 * Created by lyz on 2017/7/10.
 */

public class TypeListAdapter extends RecyclerView.Adapter<TypeListAdapter.TypeListViewHolder> {

  private List<MainPager> list;
  private Context context;
  private final LayoutInflater layoutInflater;
  private OnTypeItemClickListener onTypeItemClickListener;

  public void setOnTypeItemClickListener(OnTypeItemClickListener onTypeItemClickListener) {
    this.onTypeItemClickListener = onTypeItemClickListener;
  }

  public interface OnTypeItemClickListener {
    void onItemClick(int position, MainPager mainPager);

    void onOKClick(List<MainPager> list);
  }

  public TypeListAdapter(List<MainPager> list, Context context) {
    this.list = list;
    this.context = context;
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override public TypeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View inflate = layoutInflater.inflate(R.layout.item, parent, false);
    return new TypeListViewHolder(inflate);
  }

  @Override public void onBindViewHolder(TypeListViewHolder holder, int position) {
    if (position < list.size()) {
      holder.bindView(list.get(position));
    } else {
      holder.handleLastView();
    }
  }

  @Override public int getItemCount() {
    return list.size() + 1;
  }

  class TypeListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv) TextView textView;
    @BindView(R.id.ivSelect) ImageView ivSelect;
    View itemView;

    public TypeListViewHolder(View itemView) {
      super(itemView);
      this.itemView = itemView;
      ButterKnife.bind(this, itemView);
    }

    public void bindView(final MainPager mainPager) {
      ivSelect.setVisibility(View.VISIBLE);
      textView.setText(mainPager.getType().getType());
      if (mainPager.isSelect()) {
        ivSelect.setImageResource(R.drawable.doc_select);
      } else {
        ivSelect.setImageResource(R.drawable.doc_unselect);
      }
      ivSelect.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onTypeItemClickListener != null) {
            onTypeItemClickListener.onItemClick(getLayoutPosition(), mainPager);
            notifyItemChanged(getLayoutPosition());
          }
        }
      });
    }

    public void handleLastView() {
      textView.setText("保存");
      textView.setGravity(Gravity.CENTER);
      ivSelect.setVisibility(View.GONE);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onTypeItemClickListener != null) {
            onTypeItemClickListener.onOKClick(list);
          }
        }
      });
    }
  }
}
