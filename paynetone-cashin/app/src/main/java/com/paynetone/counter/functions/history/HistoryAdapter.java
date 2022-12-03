package com.paynetone.counter.functions.history;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.base.viper.interfaces.ContainerView;
import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;
import com.core.base.viper.interfaces.PresentView;
import com.core.widget.BaseViewHolder;
import com.paynetone.counter.R;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.Utils;

import java.util.List;

import butterknife.BindView;

public class HistoryAdapter extends RecyclerBaseAdapter {

    Activity activity;
    List mItems;

    public HistoryAdapter(Context context, List items) {
        super(context);

        activity = (Activity) context;
        this.mItems = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapter.ViewHolder(inflateView(parent, R.layout.item_history));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((HistoryAdapter.ViewHolder) holder, position);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.img_logo)
        ImageView img_logo;
        @BindView(R.id.tv_code)
        TextView tv_code;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_status)
        TextView tv_status;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            OrderModel item = (OrderModel)model;

            tv_amount.setText(NumberUtils.formatPriceNumber(item.getAmount()) + " VNĐ");
//            tv_amount.setText(item.getMobileNumber());
            tv_date.setText(item.getOrderDate());
            tv_code.setText(item.getCode());
            tv_status.setText(Utils.getStatusName(item.getStatus()));
            if (item.getProviderCode().equals(Constants.PROVIDER_ZALO)) {
                img_logo.setImageResource(R.drawable.zalopay);
            } else if (item.getProviderCode().equals(Constants.PROVIDER_VIETTEL)){
                img_logo.setImageResource(R.drawable.viettel_money);
            }else if (item.getProviderCode().equals(Constants.PROVIDER_VN_PAY)){
                img_logo.setImageResource(R.drawable.ic_vnpay);
            }else if (item.getProviderCode().equals(Constants.PROVIDER_MOCA)) img_logo.setImageResource(R.drawable.ic_moca);
            else if (item.getProviderCode().equals(Constants.PROVIDER_VIETQR)) img_logo.setImageResource(R.drawable.ic_viet_qr);
            else img_logo.setImageResource(R.drawable.ic_shoppe_pay);


            switch (item.getStatus()){
                case Constants.STATUS_W:
                    tv_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.order_status_w));
                    break;
                case Constants.STATUS_S:
                    tv_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.order_status_s));
                    break;
                case Constants.STATUS_C:
                    tv_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.order_status_c));
                    break;
            }

        }
    }
}
