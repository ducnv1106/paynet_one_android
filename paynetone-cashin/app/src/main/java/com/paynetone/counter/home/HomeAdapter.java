package com.paynetone.counter.home;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.paynetone.counter.R;
import com.paynetone.counter.model.HomeModel;

import java.util.List;

import butterknife.BindView;

public class HomeAdapter extends RecyclerBaseAdapter {
    Activity activity;
    OnClickItemListener mOnClickItemListener;

    public HomeAdapter(Context context, List<HomeModel> mHomeList,OnClickItemListener mOnClickItemListener) {
        super(context, mHomeList);
        activity = (Activity) context;
        this.mOnClickItemListener=mOnClickItemListener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeAdapter.ViewHolder(inflateView(parent, R.layout.item_home));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((BaseViewHolder) holder, position);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.img_logo)
        ImageView imgLogo;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rootView)
        LinearLayout rootView;
//        @BindView(R.id.cv_item)
//        CardView cv_item;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            HomeModel homeModel = (HomeModel) model;
            imgLogo.setImageResource(homeModel.getLogo());
            tvTitle.setText(homeModel.getTitle());
            rootView.setOnClickListener( view -> mOnClickItemListener.onClickItem(homeModel,position));

//            if (position == 1) {
//                ViewGroup.MarginLayoutParams  params =  (ViewGroup.MarginLayoutParams) cv_item.getLayoutParams();
//                int top = getPixelValue(activity, 8);
//                int right = getPixelValue(activity, 8);
//                int bottom = getPixelValue(activity, 8);
//                params.setMargins(0, top, right, bottom);
//                cv_item.setLayoutParams(params);
//            }
        }

        public  int getPixelValue(Context context, int dimenId) {
            Resources resources = context.getResources();
            return (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dimenId,
                    resources.getDisplayMetrics()
            );
        }
    }
    public interface OnClickItemListener{
        public void onClickItem(HomeModel model, int position);
    }
}
