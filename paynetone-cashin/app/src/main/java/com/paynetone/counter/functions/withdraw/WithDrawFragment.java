package com.paynetone.counter.functions.withdraw;

import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.paynetone.counter.R;
import com.paynetone.counter.adapter.BranchAdapter;
import com.paynetone.counter.dialog.ConfirmDialog;
import com.paynetone.counter.dialog.SelectAccountWithDrawBottom;
import com.paynetone.counter.dialog.SelectWithDrawBottom;
import com.paynetone.counter.dialog.SelectWithDrawWalletBottom;
import com.paynetone.counter.dialog.SuccessPaymentDialog;
import com.paynetone.counter.enumClass.SelectAccountWithDraw;
import com.paynetone.counter.enumClass.StateNotify;
import com.paynetone.counter.enumClass.StateView;
import com.paynetone.counter.functions.withdraw.history.HistoryPresenter;
import com.paynetone.counter.model.BankModel;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.MerchantModel;
import com.paynetone.counter.model.PaynetGetBalanceByIdResponse;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.enumClass.SelectWithDraw;
import com.paynetone.counter.model.request.WithdrawRequest;
import com.paynetone.counter.model.response.PaynetGetByParentResponse;
import com.paynetone.counter.model.response.WalletResponse;
import com.paynetone.counter.observer.StateViewData;
import com.paynetone.counter.observer.StateNotifyData;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.CurrencyTextWatcher;
import com.paynetone.counter.utils.ExtraConst;
import com.paynetone.counter.utils.InputFilterCharacterNumber;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.SharedPref;
import com.paynetone.counter.utils.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawFragment extends ViewFragment<WithDrawContract.Presenter> implements WithDrawContract.View {

    @BindView(R.id.tv_amount_p)
    TextView tv_amount_p;
    @BindView(R.id.tv_name_amount_withdraw)
    TextView tvNameAmountWithDraw;
    @BindView(R.id.tv_account)
    AppCompatTextView tv_account;

    //map view bank
    @BindView(R.id.tv_name_with_draw)
    AppCompatTextView tv_name_with_draw;
    @BindView(R.id.tv_name_bank)
    AppCompatTextView tv_name_bank;
    @BindView(R.id.tv_name_account_number_bank)
    AppCompatTextView tv_name_account_number_bank;
    @BindView(R.id.tv_full_name_personal_bank)
    AppCompatTextView tv_full_name_personal_bank;
    @BindView(R.id.edt_amount)
    EditText edt_amount;
    @BindView(R.id.layout_bank)
    ConstraintLayout layoutBank;

    //map view wallet
    @BindView(R.id.tv_name_wallet)
    AppCompatTextView tv_name_wallet;
    @BindView(R.id.tv_name_number_phone)
    AppCompatEditText tv_name_number_phone;
    @BindView(R.id.tv_full_name_personal_wallet)
    AppCompatEditText tv_full_name_personal_wallet;
    @BindView(R.id.layout_wallet)
    ConstraintLayout layoutWallet;

    // map view vietlott

    @BindView(R.id.tv_full_name_personal_vietlott)
    AppCompatEditText tv_full_name_personal_vietlott;
    @BindView(R.id.tv_name_post_id_vietlott)
    AppCompatTextView tv_name_post_id_vietlott;
    @BindView(R.id.layout_vietlott)
    ConstraintLayout layoutVietlott;

    // map view han muc

    @BindView(R.id.tv_branch)
    AppCompatTextView tvBranch;
    @BindView(R.id.tv_store)
    AppCompatTextView tvStore;
    @BindView(R.id.layout_han_muc)
    ConstraintLayout layoutHanMuc;
    @BindView(R.id.layout_store)
    ConstraintLayout layout_store;
    @BindView(R.id.layout_branch)
    ConstraintLayout layoutBranch;
    PopupWindow popupWindowBranch;
    PopupWindow popupWindowStore;

    BranchAdapter branchAdapter;
    BranchAdapter storeAdapter;


    long amountOutward;
    long amountOutwardGTGT;
    String balanceType ="P";
    List<BankModel> mBankModels;
    BankModel mBankModel;

    SharedPref sharedPref;
    EmployeeModel employeeModel;
    PaynetModel paynetModel;
    private ArrayList<WalletResponse> walletResponses = new ArrayList<>();
    private final ArrayList<PaynetGetByParentResponse> branchResponses = new ArrayList<>();
    private final ArrayList<PaynetGetByParentResponse> storeResponses = new ArrayList<>();
    private PaynetGetByParentResponse branchResponse;
    private PaynetGetByParentResponse storeResponse;
    private WalletResponse selectedWallet;
    private SelectWithDraw selectWithDraw = SelectWithDraw.BANK;
    private PaynetGetBalanceByIdResponse paynetGetBalanceByIdResponse;
    private SelectAccountWithDraw selectAccountWithDraw =  SelectAccountWithDraw.ACCOUNT_QR;


    public static WithDrawFragment getInstance() {
        return new WithDrawFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_with_draw;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        try {
            mBankModels = new ArrayList<>();

            sharedPref = new SharedPref(requireActivity());
            employeeModel = sharedPref.getEmployeeModel();
            paynetModel = sharedPref.getPaynet();

            mPresenter.paynetGetByParentRequest(paynetModel.getId());

            amountOutward = requireActivity().getIntent().getLongExtra(Constants.AMOUNT_OUTWARD, 0);
            amountOutwardGTGT = requireActivity().getIntent().getLongExtra(Constants.AMOUNT_OUTWARD_GTGT,0);


            String balanceType = requireActivity().getIntent().getStringExtra(Constants.AMOUNT_TYPE_BALANCE);

            if (balanceType !=null){
                tv_account.setEnabled(false);
                tv_account.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_textview_with_draw_disable_focus));
                this.balanceType = balanceType;
            }


            if (this.balanceType.equals("P")) tvNameAmountWithDraw.setText(getResources().getText(R.string.str_amount_with_draw_qr));
            else {
                tv_account.setText(getResources().getText(R.string.str_with_draw_account_gtgt));
                tvNameAmountWithDraw.setText(getResources().getText(R.string.str_amount_with_draw_gtgt));
            }

            SelectWithDraw receiveSelectWithDraw = (SelectWithDraw) requireActivity().getIntent().getSerializableExtra(ExtraConst.EXTRA_WITH_DRAW);
            PaynetGetBalanceByIdResponse paynetGetBalanceByIdResponse = requireActivity().getIntent().getParcelableExtra(ExtraConst.EXTRA_PAYNET_GET_BALANCE_BY_ID);
            if (receiveSelectWithDraw !=  null){
                this.selectWithDraw = receiveSelectWithDraw;
            }
            if (paynetGetBalanceByIdResponse != null){
                this.paynetGetBalanceByIdResponse = paynetGetBalanceByIdResponse;
            }
            initView();
            tv_amount_p.setText(NumberUtils.formatPriceNumber(amountOutward) + " VNĐ");

            edt_amount.setFilters(new InputFilter[] { new InputFilterCharacterNumber()});
            edt_amount.addTextChangedListener(new CurrencyTextWatcher(edt_amount));

            tv_name_with_draw.setOnClickListener(v -> {
                SelectWithDrawBottom bottom =  SelectWithDrawBottom.getInstance(selectWithDraw,paynetModel.getBusinessType());
                bottom.show(getChildFragmentManager(),"SelectWithDrawBottom");
                bottom.onCallBackListener(selectWithDraw->{
                    showOrHideLayoutWithDrawBank(selectWithDraw);
                    this.selectWithDraw = selectWithDraw;
                });


            });
            tv_name_wallet.setOnClickListener(v -> {
                if (walletResponses ==null || walletResponses.size()==0){
                    mPresenter.getWallet();
                }else {
                    showBottomSheetWallet();
                }

            });
            tv_account.setOnClickListener(v->{
                SelectAccountWithDrawBottom bottom =  SelectAccountWithDrawBottom.getInstance(selectAccountWithDraw);
                bottom.show(getChildFragmentManager(),"SelectAccountWithDrawBottom");
                bottom.onCallBackListener(selectAccountWithDraw->{
                    this.selectAccountWithDraw = selectAccountWithDraw;
                    if (selectAccountWithDraw == SelectAccountWithDraw.ACCOUNT_QR){
                        tvNameAmountWithDraw.setText(getResources().getText(R.string.str_amount_with_draw_qr));
                        tv_account.setText(getResources().getText(R.string.str_with_draw_account_qr));
                        this.balanceType = "P";
                        tv_amount_p.setText(NumberUtils.formatPriceNumber(amountOutward) + " VNĐ");
                    }else{
                        tvNameAmountWithDraw.setText(getResources().getText(R.string.str_amount_with_draw_gtgt));
                        tv_account.setText(getResources().getText(R.string.str_with_draw_account_gtgt));
                        this.balanceType = "C";
                        tv_amount_p.setText(NumberUtils.formatPriceNumber(amountOutwardGTGT) + " VNĐ");
                    }
                });
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void initView(){
        try {
            tv_name_post_id_vietlott.setText(paynetModel.getPosID());
            if (selectWithDraw == SelectWithDraw.HAN_MUC){
                layoutHanMuc.setVisibility(View.VISIBLE);
                layoutWallet.setVisibility(View.GONE);
                layoutVietlott.setVisibility(View.GONE);
                layoutBank.setVisibility(View.GONE);
                tv_name_with_draw.setText(getString(R.string.str_with_draw_han_muc));
                tv_name_with_draw.setEnabled(false);
                tv_name_with_draw.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_textview_with_draw_disable_focus));
            }else {
                mPresenter.getByMobileNumber(employeeModel.getMobileNumber());
            }
            if (paynetGetBalanceByIdResponse != null){
                tvBranch.setText("Cửa hàng 01");
                tvStore.setText(paynetGetBalanceByIdResponse.getName());
                tvStore.setEnabled(false);
                tvStore.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_textview_with_draw_disable_focus));
                layoutBranch.setVisibility(View.GONE);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @OnClick({R.id.iv_back, R.id.btn_ok, R.id.iv_history,R.id.tv_branch,R.id.tv_store})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.btn_ok:
                ok();
                break;
            case R.id.iv_history:
                new HistoryPresenter((ContainerView) requireActivity(),balanceType).pushView();
                break;
            case R.id.tv_branch:
                if (branchResponses.size()>0) showPopUpBranch(branchResponses);
                else mPresenter.paynetGetByParentRequest(paynetModel.getId());
                break;
            case R.id.tv_store:
                if (branchResponse == null) Toast.showToast(requireContext(),"Vui lòng chọn chi nhánh");
                else showPopUpStore(storeResponses);
                break;
        }
    }

    private void ok() {
        try {
            if (confirmInput()){
                String message = "";
                String amount = edt_amount.getText().toString() + "VNĐ";
                switch (selectWithDraw){
                    case BANK:{
                        message = "Bạn có chắc chắn muốn rút số tiền " + "<font color='#007FFF'>"+amount+"</font>" + " về tài khoản ngân hàng không?";
                        break;
                    }
                    case VIETLOTT:
                        message = "Bạn có chắc chắn muốn rút số tiền " + "<font color='#007FFF'>"+amount+"</font>" + " về tài khoản vietlott không?";
                        break;
                    case E_WALLET:
                        message = "Bạn có chắc chắn muốn rút số tiền " + "<font color='#007FFF'>"+amount+"</font>" + " về tài khoản ví điện tử không?";
                        break;
                    case HAN_MUC:
                        message = "Bạn có chắc chắn muốn nạp số tiền " + "<font color='#007FFF'>"+amount+"</font>" + " về tài khoản hạn mức không?";
                        break;
                }
                ConfirmDialog confirmDialog = ConfirmDialog.getInstance(message);
                confirmDialog.setCallBackListener(this::requestWithDraw);
                confirmDialog.show(getChildFragmentManager(),"MainFragment");
//                new ConfirmDialog(requireContext(), message, this::requestWithDraw).show(getChildFragmentManager(),"MainFragment");

            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void requestWithDraw(){
        try {
            WithdrawRequest request = new WithdrawRequest();
            request.setMerchantID(paynetModel.getMerchantID());
            request.setPaynetID(paynetModel.getId());
            request.setAmount(Long.parseLong(String.valueOf(edt_amount.getText()).replace(",", "")));
            request.setFee(0);
            request.setTransAmount(Long.parseLong(String.valueOf(edt_amount.getText()).replace(",", "")));
            request.setBalanceType(this.balanceType);

            switch (selectWithDraw){
                case BANK:
                    request.setBankID(employeeModel.getBankID());
                    request.setMobileNumber(employeeModel.getMobileNumber());
                    request.setAccountNumber(Objects.requireNonNull(tv_name_account_number_bank.getText()).toString());
                    request.setFullName(employeeModel.getPaymentAccName());
                    request.setWithdrawCategory(Constants.WITHDRAW_CATEGORY_BANK);
                    request.setWalletID(0); // default
                    break;
                case E_WALLET:
                    request.setBankID(0);
                    request.setMobileNumber(tv_name_number_phone.getText().toString());
                    request.setAccountNumber("");
                    request.setFullName(Objects.requireNonNull(tv_full_name_personal_wallet.getText()).toString());
                    request.setWithdrawCategory(Constants.WITHDRAW_CATEGORY_WALLET);
                    request.setWalletID(selectedWallet.getId());
                    break;
                case VIETLOTT:
                    request.setBankID(0);
                    request.setFullName(Objects.requireNonNull(tv_full_name_personal_vietlott.getText()).toString());
                    request.setWithdrawCategory(Constants.WITHDRAW_CATEGORY_VIETLOTT);
                    request.setWalletID(0);
                    request.setPosID(paynetModel.getPosID());
                    break;
                case HAN_MUC:{
                    request.setWithdrawCategory(Constants.WITHDRAW_CATEGORY_HAN_MUC);
                    if ( storeResponse != null ) request.setShopId(storeResponse.getLinkID()+"");
                    else if (paynetGetBalanceByIdResponse != null) request.setShopId(paynetGetBalanceByIdResponse.getLinkID()+"");
                    break;

                }
            }
            mPresenter.withdraw(request);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void showBanks(List<BankModel> bankModels) {
        this.mBankModels.clear();
        this.mBankModels.addAll(bankModels);
    }

    @Override
    public void showSuccess(String retRefNumber) {
        SuccessPaymentDialog successPaymentDialog =  SuccessPaymentDialog.getInstance(getString(R.string.str_message_payment_success));
        successPaymentDialog.setCallBackListener(new SuccessPaymentDialog.CallBackListener() {
            @Override
            public void onCloseClicked() {
                Log.e("TAG", "onCloseClicked: 121" );
                StateViewData.setMeasurements(StateView.GONE);
                StateNotifyData.setMeasurements(StateNotify.I_NEED_UPDATE);
                requireActivity().finish();
            }
        });
        successPaymentDialog.show(getChildFragmentManager(),"WithDrawFragment");

//        new SuccessPaymentDialog(requireContext(), getString(R.string.str_message_payment_success), new SuccessPaymentDialog.CallBackListener() {
//            @Override
//            public void onCloseClicked() {
//                StateViewData.setMeasurements(StateView.GONE);
//                StateNotifyData.setMeasurements(StateNotify.I_NEED_UPDATE);
//                requireActivity().finish();
//            }
//        }).show(getChildFragmentManager(),"WithDrawFragment");
    }

    @Override
    public void showListWallet(List<WalletResponse> walletResponseList) {
        this.walletResponses.addAll(walletResponseList ==null ? new ArrayList<>():walletResponseList);
        showBottomSheetWallet();
    }

    @Override
    public void showMerchant(MerchantModel model) {
        tv_full_name_personal_bank.setText(model.getPaymentAccountName());
        tv_name_account_number_bank.setText(model.getPaymentAccountNumber());
        tv_name_bank.setText(model.getPaymentAccountBankName());
    }

    @Override
    public void showPaynetGetByParentRequest(ArrayList<PaynetGetByParentResponse> responses) {
        if (branchResponse==null) {
            branchResponses.clear();
            branchResponses.addAll(responses);

            if (branchResponses.size()==0){
                tvBranch.setText("Chi nhánh 01");
                tvStore.setText("Cửa hàng 01");
            }

            if (responses.size()==0){
                layoutBranch.setVisibility(View.GONE);
                layout_store.setVisibility(View.GONE);
            }
        }
        else  {
            storeResponses.clear();
            storeResponses.addAll(responses);
        }
    }

    private void showBottomSheetWallet(){
        SelectWithDrawWalletBottom bottom =  SelectWithDrawWalletBottom.getInstance(walletResponses);
        bottom.show(getChildFragmentManager(),"SelectWithDrawWalletBottom");
        bottom.onCallBackListener(walletResponse -> {
            tv_name_wallet.setText(walletResponse.getName());
            this.selectedWallet = walletResponse;
        });
    }
    private void showPopUpBranch(ArrayList<PaynetGetByParentResponse> responses){

        View popup = getLayoutInflater().inflate(R.layout.branch_pop_up_layout,null);

        if (popupWindowBranch==null) popupWindowBranch = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        if (branchAdapter==null) branchAdapter = new BranchAdapter(requireContext(), responses, item -> {
            this.branchResponse = item;
            tvBranch.setText(item.getName());
            tvStore.setText("");
            mPresenter.paynetGetByParentRequest(item.getId());
            popupWindowBranch.dismiss();
        });
        popupWindowBranch.showAsDropDown(tvBranch,0, 20,20);
        ((RecyclerView) popup.findViewById(R.id.recycleView)).setAdapter(branchAdapter);
    }
    private void showPopUpStore(ArrayList<PaynetGetByParentResponse> responses){
        View popup = getLayoutInflater().inflate(R.layout.branch_pop_up_layout,null);

        popupWindowStore = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        storeAdapter = new BranchAdapter(requireContext(), responses, item -> {
            this.storeResponse = item;
            tvStore.setText(item.getName());
            popupWindowStore.dismiss();
        });
        popupWindowStore.showAsDropDown(tvStore,0, 20,20);
        ((RecyclerView) popup.findViewById(R.id.recycleView)).setAdapter(storeAdapter);
    }
    private void showOrHideLayoutWithDrawBank(SelectWithDraw selectWithDraw){
        switch (selectWithDraw){
            case BANK:
                layoutBank.setVisibility(View.VISIBLE);
                layoutWallet.setVisibility(View.GONE);
                layoutVietlott.setVisibility(View.GONE);
                layoutHanMuc.setVisibility(View.GONE);
                tv_name_with_draw.setText(getString(R.string.str_with_draw_bank));
                break;
            case E_WALLET:
                layoutWallet.setVisibility(View.VISIBLE);
                layoutBank.setVisibility(View.GONE);
                layoutVietlott.setVisibility(View.GONE);
                layoutHanMuc.setVisibility(View.GONE);
                tv_name_with_draw.setText(getString(R.string.str_with_draw_wallet));
                prepareViewWallet();
                break;
            case VIETLOTT:
                layoutVietlott.setVisibility(View.VISIBLE);
                layoutWallet.setVisibility(View.GONE);
                layoutBank.setVisibility(View.GONE);
                layoutHanMuc.setVisibility(View.GONE);
                tv_name_with_draw.setText(getString(R.string.str_with_draw_vietlott));
                prepareViewWallet();
                break;
            case HAN_MUC:
                layoutHanMuc.setVisibility(View.VISIBLE);
                layoutVietlott.setVisibility(View.GONE);
                layoutWallet.setVisibility(View.GONE);
                layoutBank.setVisibility(View.GONE);
                tv_name_with_draw.setText(getString(R.string.str_with_draw_han_muc));
                prepareViewWallet();
                break;


        }
    }
    private Boolean validateSelectWallet() {
        if (selectedWallet==null) {
            Toast.showToast(requireContext(),getResources().getString(R.string.str_not_enter_wallet));
            return false;
        }
        return true;
    }
    private Boolean validatePhoneNumber() {
        String phone = tv_name_number_phone.getText().toString();
        if (phone.isEmpty()) {
            Toast.showToast(requireContext(),getResources().getString(R.string.str_not_enter_phone_number));
            return false;
        }
        if (phone.charAt(0) != '0' || phone.length() != 10){
            Toast.showToast(requireContext(),getString(R.string.error_warning_phone));
            return false;
        }
        return true;
    }
    private Boolean validateReceiveFullNameWallet() {
        String fullName = tv_full_name_personal_wallet.getText().toString();
        if (fullName.isEmpty()) {
            Toast.showToast(requireContext(),getResources().getString(R.string.str_not_enter_receive_full_name));
            return false;
        }
        return true;
    }
    private Boolean validateReceiveFullNameVietlott() {
        String fullName = tv_full_name_personal_vietlott.getText().toString();
        if (fullName.isEmpty()) {
            Toast.showToast(requireContext(),getResources().getString(R.string.str_not_enter_receive_full_name));
            return false;
        }
        return true;
    }
    private Boolean validateAmount() {
        String amount = edt_amount.getText().toString();
        if (amount.isEmpty()) {
            Toast.showToast(requireContext(),getResources().getString(R.string.str_not_enter_amount));
            return false;
        }
        return true;
    }
    private Boolean validateBranch(){
        String nameBranch = tvBranch.getText().toString();
        if (nameBranch.isEmpty()) {
            Toast.showToast(requireContext(),getResources().getString(R.string.str_not_enter_branch));
            return false;
        }
        return true;
    }

    private Boolean validateStore(){
        String nameBranch = tvStore.getText().toString();
        if (nameBranch.isEmpty()) {
            Toast.showToast(requireContext(),getResources().getString(R.string.str_not_enter_store));
            return false;
        }
        return true;
    }

    private Boolean confirmInput(){
        switch (selectWithDraw){
            case BANK:
                return validateAmount();
            case E_WALLET:
                return  validateSelectWallet() && validatePhoneNumber() && validateReceiveFullNameWallet() && validateAmount();
            case VIETLOTT:
                return validateReceiveFullNameVietlott() && validateAmount();
            case HAN_MUC:
                return validateBranch() && validateStore() && validateAmount() ;
            default:
                return false;
        }
    }
    private void prepareViewWallet(){
        try {
            tv_name_wallet.setText("");
            tv_name_number_phone.setText("");
            tv_full_name_personal_wallet.setText("");
            edt_amount.setText("");
            clearCheckedWallet();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    private void disableFocusViewHanMuc(){
//        tvBranch.setText("");
//        tvStore.setText("");
//        tvBranch.setEnabled(false);
//        tvStore.setEnabled(false);
//        tvBranch.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_textview_with_draw_disable_focus));
//        tvStore.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_textview_with_draw_disable_focus));
//    }
//    private void enabledFocusViewHanMuc(){
//        tvBranch.setEnabled(true);
//        tvStore.setEnabled(true);
//        tvBranch.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_textview_with_draw_enable_focus));
//        tvStore.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_textview_with_draw_enable_focus));
//    }

    private void clearCheckedWallet(){
        for (WalletResponse walletResponse:walletResponses){
            walletResponse.setChecked(false);
        }
    }
}
