package com.paynetone.counter.functions.withdraw.history;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.adapter.RecyclerBaseAdapter;
import com.core.widget.BaseViewHolder;
import com.paynetone.counter.R;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.response.WithdrawSearchResponse;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.SharedPref;
import com.paynetone.counter.utils.Utils;

import java.util.List;

import butterknife.BindView;

public class HistoryAdapter extends RecyclerBaseAdapter {

    Activity activity;
    List<WithdrawSearchResponse> mItems;
    PaynetModel paynetModel = new SharedPref(mContext).getPaynet();

    public HistoryAdapter(Context context, List<WithdrawSearchResponse> items) {
        super(context);

        activity = (Activity) context;
        this.mItems = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapter.ViewHolder(inflateView(parent, R.layout.item_withdraw_history));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder((HistoryAdapter.ViewHolder) holder, position);
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_code)
        TextView tv_code;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_bank)
        TextView tv_bank;
        @BindView(R.id.tv_account_number)
        TextView tv_account_number;
        @BindView(R.id.tv_full_name)
        TextView tv_full_name;
        @BindView(R.id.tv_status)
        TextView tv_status;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Object model, int position) {
            WithdrawSearchResponse item = (WithdrawSearchResponse) model;

            try {
                tv_amount.setText(NumberUtils.formatPriceNumber(item.getAmount()) + "đ");
//            tv_amount.setText(item.getMobileNumber());
                tv_date.setText(item.getTransDate());
                tv_code.setText(item.getRetRefNumber());
                tv_full_name.setText(item.getFullName());
                tv_status.setText(Utils.getStatusWithdrawName(item.getReturnCode()));

                switch (item.getWithDrawCatefory()) {
                    case Constants.WITHDRAW_CATEGORY_BANK:
                        tv_bank.setText(item.getBankShortName());
                        tv_account_number.setText(item.getAccountNumber());
                        break;
                    case Constants.WITHDRAW_CATEGORY_VIETLOTT:
                        tv_bank.setText("Hạn mức Vietlott");
                        tv_account_number.setText(paynetModel.getPosID());
                        break;
                    case Constants.WITHDRAW_CATEGORY_WALLET:
                        tv_bank.setText(item.getWalletName());
                        tv_account_number.setText(item.getMobileNumber());
                        break;
                    case Constants.WITHDRAW_CATEGORY_HAN_MUC:
                        tv_bank.setText("Tài khoản hạn mức");
                        if (item.getShopCode().isEmpty()) tv_account_number.setText("");
                        else tv_account_number.setText("Mã cửa hàng: " + item.getShopCode());
                        tv_full_name.setText(item.getShopName());
                        break;
                    default:
                        tv_bank.setText("");
                        tv_account_number.setText("");
                        break;

                }

                switch (item.getReturnCode()) {

                    case 0:
                        tv_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.order_status_s));
                        break;
                    case 1:
                        tv_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.order_status_w));
                        break;
                    case 2:
                        tv_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_status_2));
                        break;
                    default:
                        tv_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.order_status_c));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
