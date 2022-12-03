package com.paynetone.counter.functions.outward;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.paynetone.counter.R;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.response.GetOutwardResponse;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.Utils;

import java.util.List;

import butterknife.BindView;

public class OutwardAdapter extends RecyclerBaseAdapter {

    Activity activity;
    List<GetOutwardResponse> mItems;

    public OutwardAdapter(Context context, List<GetOutwardResponse> items) {
        super(context);

        activity = (Activity) context;
        this.mItems = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OutwardAdapter.ViewHolder(inflateView(parent, R.layout.item_outward));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((OutwardAdapter.ViewHolder) holder, position);
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
        @BindView(R.id.tv_note)
        TextView tv_note;
        @BindView(R.id.tv_service)
        TextView tv_service;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            GetOutwardResponse item = (GetOutwardResponse)model;

            tv_amount.setText(NumberUtils.formatPriceNumber(item.getAmount()) + " VNĐ");
            tv_date.setText(item.getTransDate());
            tv_code.setText(item.getOrderCode());
            tv_note.setText(item.getTransReason());
            tv_service.setText("Dịch vụ: "+item.getProviderAcntName());

            if (item.getProviderCode().equals(Constants.PROVIDER_ZALO)) {
                img_logo.setImageResource(R.drawable.zalopay);
            } else if (item.getProviderCode().equals(Constants.PROVIDER_VIETTEL)){
                img_logo.setImageResource(R.drawable.viettel_money);
            }else if (item.getProviderCode().equals(Constants.PROVIDER_MOCA)){
                img_logo.setImageResource(R.drawable.ic_moca);
            }else if (item.getProviderCode().equals(Constants.PROVIDER_VIETQR)){
                img_logo.setImageResource(R.drawable.ic_viet_qr);
            }else if (item.getProviderCode().equals(Constants.PROVIDER_VN_PAY)){
                img_logo.setImageResource(R.drawable.ic_vnpay);
            }else if (item.getProviderCode().equals(Constants.PROVIDER_SHOPPE)){
                img_logo.setImageResource(R.drawable.ic_shoppe_pay);
            } else if (item.getProviderCode().equals(Constants.PROVIDER_GTGT)){
                img_logo.setVisibility(View.GONE);
                tv_service.setVisibility(View.VISIBLE);
            }
            if(TextUtils.isEmpty(item.getTransReason()))
                tv_note.setVisibility(View.GONE);
            else
                tv_note.setVisibility(View.VISIBLE);
        }
    }
}
