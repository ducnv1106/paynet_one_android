package com.core.widget;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

/**
 * Base View Holder for RecyclerView Adapter
 * Created by neo on 7/18/2016.
 */
public abstract class BaseViewHolder<M> extends RecyclerView.ViewHolder {

  public BaseViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public abstract void bindView(final M model, final int position);
}
