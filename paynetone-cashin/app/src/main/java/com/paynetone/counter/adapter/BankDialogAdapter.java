package com.paynetone.counter.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paynetone.counter.R;
import com.paynetone.counter.model.BankModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankDialogAdapter extends RecyclerView.Adapter<BankDialogAdapter.HolderView> implements Filterable {

    Activity activity;
    List<BankModel> mListFilter;
    List<BankModel> mList;
    OnClickItemListener mOnClickItemListener;

    public BankDialogAdapter(Context context, List<BankModel> items, OnClickItemListener mOnClickItemListener) {
        activity = (Activity) context;
        mListFilter = items;
        mList = items;
        this.mOnClickItemListener = mOnClickItemListener;

    }

    public interface OnClickItemListener{
        public void onClickItem(BankModel model, int position);
    }

    @Override
    public BankDialogAdapter.HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BankDialogAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position), position);
    }

    public List<BankModel> getListFilter() {
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
                    List<BankModel> filteredList = new ArrayList<>();
                    for (BankModel row : mList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getShortName().toLowerCase().contains(charString.toLowerCase())) {
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
                mListFilter = (ArrayList<BankModel>) filterResults.values;
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

        public HolderView(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final Object model, int position) {
            BankModel bankModel = (BankModel)model;
            tv_name.setText(bankModel.getName());
            tv_short_name.setText(bankModel.getShortName());
            rootView.setOnClickListener(view -> {
                mOnClickItemListener.onClickItem(bankModel,position);
            });
        }

    }
}
