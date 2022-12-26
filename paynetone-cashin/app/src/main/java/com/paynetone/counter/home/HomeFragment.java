package com.paynetone.counter.home;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.paynetone.counter.R;
import com.paynetone.counter.dialog.DevelopDialog;
import com.paynetone.counter.dialog.ListBankQRDialog;
import com.paynetone.counter.functions.history.HistoryActivity;
import com.paynetone.counter.functions.outward.OutwardActivity;
import com.paynetone.counter.functions.qr.OptionPaymentAdapter;
import com.paynetone.counter.functions.qr.QRDynamicActivity;
import com.paynetone.counter.functions.withdraw.WithDrawActivity;
import com.paynetone.counter.model.HomeModel;
import com.paynetone.counter.model.MerchantBalance;
import com.paynetone.counter.model.PaymentModel;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.request.GetProviderResponse;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.ExtraConst;
import com.paynetone.counter.utils.MarginDecoration;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.SharedPref;
import com.paynetone.counter.utils.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.recycleBank)
    RecyclerView recyclerViewBank;
    @BindView(R.id.recyclePayment)
    RecyclerView recyclePayment;
    @BindView(R.id.recycleEWallet)
    RecyclerView recyclerViewEWallet;

    OptionPaymentAdapter bankAdapter;
    OptionPaymentAdapter eWalletAdapter;
    OptionPaymentAdapter paymentQrAdapter;
    PaynetModel paynetModel;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        paynetModel = new SharedPref(requireActivity()).getPaynet();
    }

    public void initAdapter(ArrayList<GetProviderResponse> providers){
        if (bankAdapter==null) bankAdapter = new OptionPaymentAdapter(getContext(), item -> {
            String merchantStatus = paynetModel.getMerchantStatus();
            if (merchantStatus!=null && merchantStatus.equals(Constants.WAITING_APPROVAL)){
                new DevelopDialog(getResources().getString(R.string.str_message_waiting_approval)).show(getChildFragmentManager(),"HomeFragment");
                return;
            }
            if (item.isActive().equals(Constants.PROVIDER_ACTIVE)){
                if (item.getItemGroup()){
                    ListBankQRDialog.getInstance(providers).show(getChildFragmentManager(),"HomeFragment");
                }else {
                    Intent intent = new Intent(requireActivity(), QRDynamicActivity.class);
                    intent.putExtra(ExtraConst.EXTRA_PROVIDER_RESPONSE,item);
                    startActivity(intent);
                }
            }else{
                new DevelopDialog().show(getChildFragmentManager(),"HomeFragment");
            }
        }, OptionPaymentAdapter.ProviderEnum.BANK,providers);
        recyclerViewBank.setAdapter(bankAdapter);
        recyclerViewBank.addItemDecoration(new MarginDecoration(20,4));

        if (paymentQrAdapter ==null)  paymentQrAdapter = new OptionPaymentAdapter(getContext(), item -> {
            String merchantStatus = paynetModel.getMerchantStatus();
            if (merchantStatus!=null && merchantStatus.equals(Constants.WAITING_APPROVAL)){
                new DevelopDialog(getResources().getString(R.string.str_message_waiting_approval)).show(getChildFragmentManager(),"HomeFragment");
                return;
            }
            if (item.getId() == Constants.ID_VNPAY && SharedPref.getInstance(getContext()).isPersonal()){
                new DevelopDialog(getResources().getString(R.string.str_message_unsupport)).show(getChildFragmentManager(),"HomeFragment");
                return;
            }
            if (item.isActive().equals(Constants.PROVIDER_ACTIVE)){
                Intent intent = new Intent(requireActivity(), QRDynamicActivity.class);
                intent.putExtra(ExtraConst.EXTRA_PROVIDER_RESPONSE,item);
                startActivity(intent);
            }else{
                new DevelopDialog().show(getChildFragmentManager(),"HomeFragment");
            }
        }, OptionPaymentAdapter.ProviderEnum.PAYMENT_QR,providers);
        recyclePayment.setAdapter(paymentQrAdapter);
        recyclePayment.addItemDecoration(new MarginDecoration(20,4));

        if (eWalletAdapter==null) eWalletAdapter = new OptionPaymentAdapter(getContext(), item -> {
            String merchantStatus = paynetModel.getMerchantStatus();
            if (merchantStatus!=null && merchantStatus.equals(Constants.WAITING_APPROVAL)){
                new DevelopDialog(getResources().getString(R.string.str_message_waiting_approval)).show(getChildFragmentManager(),"HomeFragment");
                return;
            }
            if (item.isActive().equals(Constants.PROVIDER_ACTIVE)){
                Intent intent = new Intent(requireActivity(), QRDynamicActivity.class);
                intent.putExtra(ExtraConst.EXTRA_PROVIDER_RESPONSE,item);
                startActivity(intent);
            }else {
                new DevelopDialog().show(getChildFragmentManager(),"HomeFragment");
            }
        }, OptionPaymentAdapter.ProviderEnum.E_WALLET,providers);
        recyclerViewEWallet.setAdapter(eWalletAdapter);
        recyclerViewEWallet.addItemDecoration(new MarginDecoration(20,4));

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
