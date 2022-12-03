package com.paynetone.counter.functions.payment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.paynetone.counter.R;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.NumberUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentFragment extends ViewFragment<PaymentContract.Presenter> implements PaymentContract.View {
    @BindView(R.id.tv_mobile)
    TextView tv_mobile;
    @BindView(R.id.tv_note)
    TextView tv_note;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_fee)
    TextView tv_fee;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    @BindView(R.id.tv_providers)
    TextView tv_providers;

    OrderAddRequest mOrderAddRequest;

    public static PaymentFragment getInstance() {
        return new PaymentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        if (mPresenter != null) {
            mOrderAddRequest = mPresenter.getOrderAddRequest();
            tv_mobile.setText(mOrderAddRequest.getMobileNumber());
            tv_note.setText(mOrderAddRequest.getOrderDes());
            tv_amount.setText(NumberUtils.formatPriceNumber(mOrderAddRequest.getAmount()) + " VNĐ");
            tv_fee.setText("0 VNĐ");
            if (mOrderAddRequest.getProviderCode().equals(Constants.PROVIDER_ZALO)) {
                img_logo.setImageResource(R.drawable.zalopay);
                tv_providers.setText(getResources().getString(R.string.str_zalo_pay));
            } else if (mOrderAddRequest.getProviderCode().equals(Constants.PROVIDER_VIETTEL)){
                img_logo.setImageResource(R.drawable.viettel_money);
                tv_providers.setText(getResources().getString(R.string.str_viettel_money));
            } else if (mOrderAddRequest.getProviderCode().equals(Constants.PROVIDER_VN_PAY)) {
                img_logo.setImageResource(R.drawable.ic_vnpay);
                tv_providers.setText(getResources().getString(R.string.str_vn_pay));
            } else if (mOrderAddRequest.getProviderCode().equals(Constants.PROVIDER_SHOPPE)){
                img_logo.setImageResource(R.drawable.ic_shoppe_pay);
                tv_providers.setText(getResources().getString(R.string.str_shoppe_pay));
            }else if (mOrderAddRequest.getProviderCode().equals(Constants.PROVIDER_VIETQR)){
                img_logo.setImageResource(R.drawable.ic_viet_qr);
                tv_providers.setText(getResources().getString(R.string.str_viet_qr));
            } else {
                img_logo.setImageResource(R.drawable.ic_moca);
                tv_providers.setText(getResources().getString(R.string.str_moca));
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.btn_ok})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.btn_ok:
                mPresenter.orderAdd(mOrderAddRequest);
                break;
        }
    }

}
