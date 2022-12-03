package com.paynetone.counter.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.paynetone.counter.R;
import com.paynetone.counter.model.BaseDialogModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseDialogAdapter extends RecyclerView.Adapter<BaseDialogAdapter.HolderView> implements Filterable {

    Activity activity;
    List<BaseDialogModel> mListFilter;
    List<BaseDialogModel> mList;
    OnClickItemListener mOnClickItemListener;

    public BaseDialogAdapter(Context context, List<BaseDialogModel> items,OnClickItemListener OnClickItemListener) {
        activity = (Activity) context;
        mListFilter = items;
        mList = items;
        this.mOnClickItemListener=OnClickItemListener;
    }

    public interface OnClickItemListener{
        public void onClickItem(BaseDialogModel model,int position);
    }

    @Override
    public BaseDialogAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseDialogAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseDialogAdapter.HolderView holder, int position) {
        holder.bindView(mListFilter.get(holder.getAdapterPosition()), holder.getAdapterPosition());
    }

    public List<BaseDialogModel> getListFilter() {
        return mListFilter;
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<BaseDialogModel> filteredList = new ArrayList<>();
                    for (BaseDialogModel row : mList) {
                        if (row.getText().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (ArrayList<BaseDialogModel>) filterResults.values;
                mListFilter.forEach(it->{
                    Log.e("TAG", "publishResults: " + it.getText());
                });
                notifyDataSetChanged();
            }
        };
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_short_name)
        TextView tv_short_name;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.rootView)
        LinearLayout rootView;

        public HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final Object model, int position) {
            BaseDialogModel bankModel = (BaseDialogModel) model;
            tv_name.setText(bankModel.getText());
            tv_name.setTextColor(ContextCompat.getColor(activity, R.color.black));
            tv_short_name.setVisibility(View.GONE);
            rootView.setOnClickListener(view -> {
                mOnClickItemListener.onClickItem(bankModel,position);
            });

        }

    }
}
