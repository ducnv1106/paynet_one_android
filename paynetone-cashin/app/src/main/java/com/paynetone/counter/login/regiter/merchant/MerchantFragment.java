package com.paynetone.counter.login.regiter.merchant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.core.utils.AppUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.paynetone.counter.R;
import com.paynetone.counter.adapter.FormalityTypeAdapter;
import com.paynetone.counter.adapter.QrOptionAdapter;
import com.paynetone.counter.adapter.ServiceTypeAdapter;
import com.paynetone.counter.dialog.AddImageDialogFragment;
import com.paynetone.counter.dialog.BankBottomDialog;
import com.paynetone.counter.dialog.BaseBottomDialog;
import com.paynetone.counter.main.SplashScreenActivity;
import com.paynetone.counter.model.BankModel;
import com.paynetone.counter.model.BaseDialogModel;
import com.paynetone.counter.model.DictionaryModel;
import com.paynetone.counter.model.EmployeeModel;
import com.paynetone.counter.model.FormalityTypeModel;
import com.paynetone.counter.model.ImagePicker;
import com.paynetone.counter.model.MerchantModel;
import com.paynetone.counter.model.PaynetModel;
import com.paynetone.counter.model.QrOptionModel;
import com.paynetone.counter.model.RegisterPassDataModel;
import com.paynetone.counter.model.ServiceTypeModel;
import com.paynetone.counter.model.request.MerchantAddNewRequest;
import com.paynetone.counter.model.response.DictionaryBusinessServiceResponse;
import com.paynetone.counter.observer.DisplayElement;
import com.paynetone.counter.observer.Observer;
import com.paynetone.counter.observer.Subject;
import com.paynetone.counter.utils.BitmapUtils;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.DialogHelper;
import com.paynetone.counter.utils.MediaUtils;
import com.paynetone.counter.utils.SharedPref;
import com.paynetone.counter.utils.Toast;
import com.paynetone.counter.utils.Utils;
import com.paynetone.counter.widgets.ProgressView;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class MerchantFragment extends ViewFragment<MerchantContract.Presenter> implements MerchantContract.View, BlockingStep {
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout_add_profile)
    LinearLayout layout_add_profile;
    @BindView(R.id.tv_add_profile)
    AppCompatTextView tv_add_profile;
    @BindView(R.id.auto_business_service)
    AppCompatTextView auto_business_service;
    @BindView(R.id.auto_province)
    AppCompatTextView auto_province;
    @BindView(R.id.auto_district)
    AppCompatTextView auto_district;
    @BindView(R.id.auto_ward)
    AppCompatTextView auto_ward;
    @BindView(R.id.edt_address)
    AppCompatEditText edt_address;
    @BindView(R.id.auto_bank)
    AppCompatTextView auto_bank;

    @BindView(R.id.edt_mobile_number)
    AppCompatEditText edt_mobile_number;
    @BindView(R.id.layout_tax_code)
    LinearLayout layout_tax_code;
    @BindView(R.id.tv_name_tax_code)
    AppCompatTextView tv_name_tax_code;
    @BindView(R.id.edt_tax_code)
    AppCompatEditText edtTaxCode;
    @BindView(R.id.layout_fax)
    LinearLayout layout_fax;
    @BindView(R.id.edt_fax)
    AppCompatEditText edtFax;
    @BindView(R.id.layout_business_registration_number)
    LinearLayout layout_business_registration_number;
    @BindView(R.id.edt_business_registration_number)
    AppCompatEditText edt_business_registration_number;
    @BindView(R.id.layout_company)
    LinearLayout layout_company;
    @BindView(R.id.edt_name_company)
    AppCompatEditText edtNameCompany;
    @BindView(R.id.edt_name_qr)
    AppCompatEditText edtNameQr;
    @BindView(R.id.edt_name)
    AppCompatEditText edt_name;
    @BindView(R.id.edt_office)
    AppCompatEditText edtOffice;
    @BindView(R.id.layout_office)
    LinearLayout layout_office;
    @BindView(R.id.edt_mobile_number_represent)
    AppCompatEditText edt_mobile_number_represent;
    @BindView(R.id.layout_mobile_number_represent)
    LinearLayout layout_mobile_number_represent;
    @BindView(R.id.edt_post_id)
    AppCompatEditText edt_post_id;
    @BindView(R.id.tv_post_id_default)
    AppCompatTextView tv_post_id_default;
    @BindView(R.id.layout_post_id)
    LinearLayout layoutPostId;
    @BindView(R.id.edt_pid_number)
    AppCompatEditText edt_pid_number;
    @BindView(R.id.radio_yes)
    RadioButton radioYes;
    @BindView(R.id.radio_no)
    RadioButton radioNo;
    @BindView(R.id.layout_radio)
    LinearLayout layout_radio;
    @BindView(R.id.edt_account_number)
    AppCompatEditText edt_account_number;
    @BindView(R.id.edt_branch)
    AppCompatEditText edt_branch;
    @BindView(R.id.edt_name_personal_bank)
    AppCompatEditText edt_name_personal_bank;

    @BindView(R.id.img_before)
    ImageView image_before;
    @BindView(R.id.progress_before)
    ProgressView progressViewBefore;
    @BindView(R.id.img_after)
    ImageView image_after;
    @BindView(R.id.progress_after)
    ProgressView progressViewAfter;
    @BindView(R.id.tv_name_img_business_registration)
    AppCompatTextView tv_name_img_business_registration;
    @BindView(R.id.img_business_registration)
    ImageView image_business_registration;
    @BindView(R.id.progress_business_registration)
    ProgressView progressViewBusinessRegistration;
    @BindView(R.id.tv_name_img_address_sale)
    AppCompatTextView tv_name_img_address_sale;
    @BindView(R.id.img_address_sale)
    ImageView image_address_sale;
    @BindView(R.id.progress_address_sale)
    ProgressView progressViewAddressSale;
    @BindView(R.id.layout_img_tax_code)
    LinearLayout layout_img_tax_code;
    @BindView(R.id.tv_name_img_tax_code)
    AppCompatTextView tv_name_img_tax_code;
    @BindView(R.id.img_tax_code)
    ImageView img_tax_code;
    @BindView(R.id.progress_tax_code)
    ProgressView progressViewTaxCode;
    @BindView(R.id.img_other)
    ImageView image_other;
    @BindView(R.id.progress_other)
    ProgressView progressViewOther;
    @BindView(R.id.img_formality_online)
    ImageView img_formality_online;
    @BindView(R.id.progress_formality_online)
    ProgressView progressViewFormalityOnline;


    @BindView(R.id.rl_navigation_header)
    RelativeLayout rl_navigation_header;
    @BindView(R.id.rl_status)
    RelativeLayout rl_status;
    @BindView(R.id.tv_formality)
    AppCompatTextView tv_formality;
    @BindView(R.id.tv_service_type)
    AppCompatTextView tv_service_type;
    @BindView(R.id.tv_qr_option)
    AppCompatTextView tv_qr_option;
    MerchantModel mMerchantModel;
    @BindView(R.id.tv_business)
    AppCompatTextView tv_business;

    @BindView(R.id.layout_business_online)
    LinearLayout layout_business_online;
    @BindView(R.id.edt_link)
    AppCompatEditText edtLink;



    StepperLayout.OnNextClickedCallback mCallback;

    List<DictionaryBusinessServiceResponse> mBusinessServices = new ArrayList<>();
    List<BankModel> bankModels = new ArrayList<>();
    List<DictionaryModel> provinces = new ArrayList<>();
    List<DictionaryModel> districts = new ArrayList<>();
    List<DictionaryModel> wards = new ArrayList<>();

    BaseDialogModel mBusinessService;
    BankModel bankModel;
    DictionaryModel province;
    DictionaryModel district;
    DictionaryModel ward;

    TypeImage typeImage = TypeImage.OTHER;
    int businessType = Constants.BUSINESS_TYPE_ENTERPRISE;
    String formalityType = Constants.FORMALITY_TYPE_ONLINE;

    String fileImgBefore;
    String fileImgAfter;
    String fileImgBusinessRegistration ="";
    String fileImgAddressSale="";
    String fileImgTaxCode = "";
    String fileOther="";
    String fileImgFormalityOnline = "";
    String tvPostIdDefault = "";

    BaseDialogModel provinceModel;
    BaseDialogModel districtModel;
    BaseDialogModel wardModel;
    QrOptionAdapter adapter;
    ServiceTypeAdapter serviceTypeAdapter;
    FormalityTypeAdapter formalityTypeAdapter;


    RegisterPassDataModel dataModel;
    String mode = Constants.MERCHANT_MODE_ADD;


    public static MerchantFragment getInstance() {
        return new MerchantFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant;
    }

    @Override
    public void initLayout() {
        super.initLayout();
//        disableTextInput(true);
        try {
            rl_navigation_header.setVisibility(View.GONE);
            rl_status.setVisibility(View.GONE);
            if (mPresenter != null) {
                mode = mPresenter.getMode();
                if (mode.equals(Constants.MERCHANT_MODE_VIEW) || mode.equals(Constants.MERCHANT_MODE_EDIT)) {
                    if (mode.equals(Constants.MERCHANT_MODE_EDIT)) {
                        dataModel = mPresenter.getPassData();
                        edt_mobile_number.setText(dataModel.getMobileNumber());
                        rl_status.setVisibility(View.GONE);
                    } else{
                        rl_status.setVisibility(View.VISIBLE);
                        tv_title.setText("HỒ SƠ MERCHANT");
                    }
                    rl_navigation_header.setVisibility(View.VISIBLE);
                }
                if (mode.equals(Constants.MERCHANT_MODE_EDIT)) btn_ok.setVisibility(View.VISIBLE);
            }


            adapter = new QrOptionAdapter(requireContext(), () -> {
                String content = "";
                for (QrOptionModel qrOptionModel:adapter.getList()){
                    if (qrOptionModel.isChecked()) content += qrOptionModel.getName()+". ";
                }
                tv_qr_option.setText(content);
            });
            adapter.submitList();
            serviceTypeAdapter = new ServiceTypeAdapter(requireContext(), () -> {
                String content = "";
                for (ServiceTypeModel serviceTypeModel:serviceTypeAdapter.getList()){
                    if (serviceTypeModel.isChecked()) content += serviceTypeModel.getName()+". ";
                }
                tv_service_type.setText(content);
            });
            serviceTypeAdapter.submitList();

            formalityTypeAdapter = new FormalityTypeAdapter(requireContext(), () -> {
                String content = "";
                String formalityType = "";

                for (FormalityTypeModel formalityTypeModel:formalityTypeAdapter.getList()){
                    if (formalityTypeModel.isChecked()){
                        content += formalityTypeModel.getName()+". ";
                        formalityType += formalityTypeModel.getId()+";";
                    }
                }
                if (formalityType.contains(Constants.FORMALITY_TYPE_ONLINE)){
                    layout_business_online.setVisibility(View.VISIBLE);
                }else layout_business_online.setVisibility(View.GONE);

                if (formalityType.length()>=2) formalityType = formalityType.substring(0,formalityType.length()-1);

                tv_formality.setText(content);
                this.formalityType = formalityType;

            });
            formalityTypeAdapter.submitList();
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    @OnClick({R.id.auto_business_service, R.id.auto_province, R.id.auto_district, R.id.auto_ward,
            R.id.auto_bank, R.id.img_before, R.id.img_after, R.id.iv_back, R.id.btn_ok,R.id.tv_business,
            R.id.tv_formality,R.id.img_business_registration,R.id.img_address_sale,R.id.img_other,R.id.tv_service_type,
            R.id.tv_qr_option,R.id.radio_no,R.id.radio_yes,R.id.img_tax_code,R.id.img_formality_online,R.id.rootView})
    public void OnClick(View view) {
        if (!isClickAble()) return;
        switch (view.getId()) {
            case R.id.rootView:
                AppUtils.hideKeyboard(rootView);
                break;
            case R.id.auto_business_service:
                selectBusinessService();
                break;
            case R.id.auto_province:
                selectProvince();
                break;
            case R.id.auto_district:
                selectDistrict();
                break;
            case R.id.auto_ward:
                selectWard();
                break;
            case R.id.auto_bank:
                selectBank();
                break;
            case R.id.img_before:
                typeImage = TypeImage.CTTT_BEFORE;
                openImagePickFragment();
                break;
            case R.id.img_after:
                typeImage = TypeImage.CTTT_AFTER;
                openImagePickFragment();
                break;
            case R.id.img_business_registration:
                typeImage = TypeImage.BUSINESS_REGISTRATION;
                openImagePickFragment();
                break;
            case R.id.img_address_sale:
                typeImage = TypeImage.ADDRESS_SALE;
                openImagePickFragment();
                break;
            case R.id.img_tax_code:{
                typeImage = TypeImage.TAX_CODE;
                openImagePickFragment();
                break;
            }
            case R.id.img_formality_online:{
                typeImage = TypeImage.FORMALITY_ONLINE;
                openImagePickFragment();
                break;
            }
            case R.id.img_other:
                typeImage = TypeImage.OTHER;
                openImagePickFragment();
                break;
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.btn_ok:
                addMerchant(false);
                break;
            case R.id.tv_formality:
                showPopUpFormality();
                AppUtils.hideKeyboard(rootView);
                break;
            case R.id.tv_business:
                showPopUpBusiness();
                AppUtils.hideKeyboard(rootView);
                break;
            case R.id.tv_service_type:{
                showPopUpService();
                AppUtils.hideKeyboard(rootView);
                break;
            }
            case R.id.tv_qr_option:{
                showPopUpQr();
                AppUtils.hideKeyboard(rootView);
                break;
            }
            case R.id.radio_yes:
                radioYes.setChecked(true);
                radioNo.setChecked(false);
                break;
            case R.id.radio_no:
                radioNo.setChecked(true);
                radioYes.setChecked(false);
                break;
        }
    }

    private void selectBusinessService() {
        if (mBusinessServices.size() > 0) {
            List<BaseDialogModel> baseDialogModels = new ArrayList<>();
            for (DictionaryBusinessServiceResponse item : mBusinessServices) {
                BaseDialogModel baseDialogModel = new BaseDialogModel();
                baseDialogModel.setText(item.getName());
                baseDialogModel.setValue(item.getId().toString());
                baseDialogModels.add(baseDialogModel);
            }
            BaseBottomDialog baseDialog = new BaseBottomDialog(baseDialogModels, "Chọn Loại dịch vụ hàng hóa", bankModel -> {
                auto_business_service.setText(bankModel.getText());
                mBusinessService = bankModel;
            });
            baseDialog.show(getChildFragmentManager(), "WithDrawFragment");
        } else {
            Toast.showToast(getContext(), "Không tải được danh mục Loại dịch vụ hàng hóa");
        }
    }

    private void selectProvince() {
        try {
            if (provinces.size() > 0) {
                List<BaseDialogModel> baseDialogModels = new ArrayList<>();
                for (DictionaryModel item : provinces) {
                    BaseDialogModel baseDialogModel = new BaseDialogModel();
                    baseDialogModel.setText(item.getName());
                    baseDialogModel.setValue(item.getId().toString());
                    baseDialogModels.add(baseDialogModel);
                }
                BaseBottomDialog baseDialog = new BaseBottomDialog(baseDialogModels, "Chọn Tỉnh/Thành phố", bankModel -> {
                    auto_province.setText(bankModel.getText());
                    provinceModel = bankModel;
                    province = new DictionaryModel();
                    province.setId(Integer.parseInt(bankModel.getValue()));
                    province.setName(bankModel.getText());
                    mPresenter.getDistricts(Integer.parseInt(bankModel.getValue()));
                    mPresenter.getPostId(Integer.parseInt(bankModel.getValue()));
                    auto_district.setText("");
                    auto_ward.setText("");
                });
                baseDialog.show(getChildFragmentManager(), "WithDrawFragment");
            } else {
                Toast.showToast(getContext(), "Không tải được danh mục Tỉnh/Thành phố");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void selectDistrict() {
        if (districts.size() > 0) {
            List<BaseDialogModel> baseDialogModels = new ArrayList<>();
            for (DictionaryModel item : districts) {
                BaseDialogModel baseDialogModel = new BaseDialogModel();
                baseDialogModel.setText(item.getName());
                baseDialogModel.setValue(item.getId().toString());
                baseDialogModels.add(baseDialogModel);
            }
            BaseBottomDialog baseDialog = new BaseBottomDialog(baseDialogModels, "Chọn Quận/Huyện", bankModel -> {
                auto_district.setText(bankModel.getText());
                districtModel = bankModel;
                district = new DictionaryModel();
                district.setId(Integer.parseInt(bankModel.getValue()));
                district.setName(bankModel.getText());
                mPresenter.getWards(Integer.parseInt(bankModel.getValue()));
                auto_ward.setText("");
            });
            baseDialog.show(getChildFragmentManager(), "WithDrawFragment");
        } else {
            Toast.showToast(getContext(), "Vui lòng chọn Tỉnh/Thành phố trước");
        }
    }

    private void selectWard() {
        if (wards.size() > 0) {
            List<BaseDialogModel> baseDialogModels = new ArrayList<>();
            for (DictionaryModel item : wards) {
                BaseDialogModel baseDialogModel = new BaseDialogModel();
                baseDialogModel.setText(item.getName());
                baseDialogModel.setValue(item.getId().toString());
                baseDialogModels.add(baseDialogModel);
            }
            BaseBottomDialog baseDialog = new BaseBottomDialog(baseDialogModels, "Chọn Xã/Phường", bankModel -> {
                auto_ward.setText(bankModel.getText());
                wardModel = bankModel;
                ward = new DictionaryModel();
                ward.setId(Integer.parseInt(bankModel.getValue()));
                ward.setName(bankModel.getText());
            });
            baseDialog.show(getChildFragmentManager(), "WithDrawFragment");
        } else {
            Toast.showToast(getContext(), "Vui lòng chọn Quận/Huyện trước");
        }
    }

    private void selectBank() {
        if (bankModels.size() > 0) {
            BankBottomDialog baseDialog = new BankBottomDialog(bankModels, bankModel -> {
                auto_bank.setText(bankModel.getShortName());
                this.bankModel = bankModel;
            });
            baseDialog.show(getChildFragmentManager(), "WithDrawFragment");
        } else {
            Toast.showToast(getContext(), "Không tải được danh mục Ngân hàng");
        }
    }

    private void addMerchant(boolean isPass) {
        // request qr option
        String qrOption = "";
        for (int i=0;i<adapter.getList().size();i++){
            if (adapter.getList().get(i).isChecked()) qrOption += adapter.getList().get(i).getId() +";";
        }
        String serviceType ="";
        for (int i=0;i<serviceTypeAdapter.getList().size();i++){
            if (serviceTypeAdapter.getList().get(i).isChecked()) serviceType += serviceTypeAdapter.getList().get(i).getId() +";";
        }
        if (!isPass) {
            if (Utils.isBlank(formalityType)){
                Toast.showToast(requireContext(), "Bạn chưa chọn hình thức kinh doanh");
                return;
            }
            if (TextUtils.isEmpty(edt_mobile_number.getText())) {
                Toast.showToast(requireContext(), "Bạn chưa nhập Số điện thoại đăng ký kinh doanh");
                return;
            }
            if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE){
                if (TextUtils.isEmpty(edtTaxCode.getText())){
                    Toast.showToast(requireContext(), "Bạn chưa nhập mã số thuế");
                    return;
                }
            }
            if (formalityType.contains(Constants.FORMALITY_TYPE_ONLINE)){
                if (TextUtils.isEmpty(edtNameCompany.getText())){
                    Toast.showToast(requireContext(), "Bạn chưa nhập tên công ty");
                    return;
                }
            }
            if (businessType == Constants.BUSINESS_TYPE_VIETLOTT || businessType == Constants.BUSINESS_TYPE_SYNTHETIC){
                if (TextUtils.isEmpty(edt_post_id.getText())){
                    Toast.showToast(requireContext(), "Bạn chưa nhập PostID");
                    return;
                }
            }
            if (TextUtils.isEmpty(edtNameQr.getText())){
                Toast.showToast(requireContext(), "Bạn chưa nhập tên QR");
                return;
            }
            if (mBusinessService == null) {
                Toast.showToast(requireContext(), "Vui lòng chọn loại hàng hóa kinh doanh");
                return;
            }
            if (TextUtils.isEmpty(serviceType)){
                Toast.showToast(requireContext(), "Vui lòng chọn dịch vụ");
                return;
            }
            if (TextUtils.isEmpty(qrOption)){
                Toast.showToast(requireContext(), "Vui lòng chọn dịch vụ yêu cầu cung cấp");
                return;
            }
            if (TextUtils.isEmpty(auto_province.getText().toString())){
                Toast.showToast(requireContext(), "Vui lòng chọn Tỉnh/thành phố");
                return;
            }
            if (TextUtils.isEmpty(auto_district.getText().toString())){
                Toast.showToast(requireContext(), "Vui lòng chọn Quận/huyện");
                return;
            }
            if (TextUtils.isEmpty(auto_ward.getText().toString())){
                Toast.showToast(requireContext(), "Vui lòng chọn Xã phường");
                return;
            }
            if (TextUtils.isEmpty(edt_address.getText().toString())){
                Toast.showToast(requireContext(), "Vui lòng nhập địa chỉ");
                return;
            }
            if (TextUtils.isEmpty(edt_name.getText())) {
                Toast.showToast(requireContext(), "Bạn chưa nhập Họ tên người đại diện");
                return;
            }
            if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE || businessType == Constants.BUSINESS_TYPE_HOUSEHOLD){
                if (TextUtils.isEmpty(edt_mobile_number_represent.getText())) {
                    Toast.showToast(requireContext(), "Bạn chưa nhập số điện thoại người đại diện");
                    return;
                }
            }

            if (TextUtils.isEmpty(fileImgBefore)) {
                Toast.showToast(requireContext(), "Bạn chưa Chụp ảnh giấy tờ tùy thân mặt trước");
                return;
            }
            if (TextUtils.isEmpty(fileImgAfter)) {
                Toast.showToast(requireContext(), "Bạn chưa Chụp ảnh giấy tờ tùy thân mặt sau");
                return;
            }

            if (TextUtils.isEmpty(edt_pid_number.getText())) {
                Toast.showToast(requireContext(), "Bạn chưa nhập Số giấy tờ tùy thân người đại diện");
                return;
            }
            if (TextUtils.isEmpty(edt_name_personal_bank.getText())) {
                Toast.showToast(requireContext(), "Bạn chưa nhập tên chủ tài khoản");
                return;
            }
            if (TextUtils.isEmpty(edt_account_number.getText())) {
                Toast.showToast(requireContext(), "Bạn chưa nhập Số tài khoản thanh toán");
                return;
            }
            if (this.bankModel == null) {
                Toast.showToast(requireContext(), "Bạn chưa Chọn Ngân hàng thanh toán");
                return;
            }
            if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE || businessType == Constants.BUSINESS_TYPE_HOUSEHOLD){
                if (Utils.isBlank(fileImgBusinessRegistration)){
                    Toast.showToast(requireContext(), "Bạn chưa Chụp ảnh chứng nhận đăng ký kinh doanh/hộ kinh doanh");
                    return;
                }
                if (Utils.isBlank(fileImgAddressSale)){
                    Toast.showToast(requireContext(), "Bạn chưa Chụp ảnh điểm bán hàng");
                    return;
                }

            }

            if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE){
                if (Utils.isBlank(fileImgTaxCode)){
                    Toast.showToast(requireContext(), "Bạn chưa Chụp ảnh mã số thuế");
                    return;
                }
            }
            if (formalityType.contains(Constants.FORMALITY_TYPE_ONLINE)){
                if (Utils.isBlank(edtLink.getText().toString())){
                    Toast.showToast(requireContext(), "Bạn chưa nhập Link website");
                    return;
                }
            }
        }


        MerchantAddNewRequest req = new MerchantAddNewRequest();
        req.setFormalityType(formalityType);
        req.setBusinessType(businessType+"");
        req.setServiceType(serviceType);
        req.setMobileNumber(edt_mobile_number.getText().toString());
        req.setName(dataModel.getMerchantName());
        req.setEmail(dataModel.getEmail());
        if (!TextUtils.isEmpty(edt_address.getText()))
            req.setAddress(edt_address.getText().toString());
        req.setRepresentativeName(edt_name.getText().toString());
        req.setRepresentativePosition(edtOffice.getText().toString());
        req.setRepresentativeMobile(edt_mobile_number_represent.getText().toString());
        req.setPaymentAccountNumber(edt_account_number.getText().toString());
        if (radioYes.isChecked()) req.setIsSignContract("Y"); else req.setIsSignContract("N");
        req.setRepresentativePIDNumber(edt_pid_number.getText().toString());
        req.setRepresentativePIDImageBefore(fileImgBefore);
        req.setRepresentativePIDImageAfter(fileImgAfter);
        req.setBusinessMobile(edt_mobile_number.getText().toString());
        req.setTaxCode(edtTaxCode.getText().toString());
        req.setFax(edtFax.getText().toString());
        req.setContractCode(edt_business_registration_number.getText().toString());
        req.setCompanyName(edtNameCompany.getText().toString());
        if (businessType == Constants.BUSINESS_TYPE_VIETLOTT ||
                businessType == Constants.BUSINESS_TYPE_SYNTHETIC){
            req.setPosID(tvPostIdDefault+edt_post_id.getText().toString());
        }
        req.setPrintQRName(edtNameQr.getText().toString());
        req.setProvinceID(Integer.parseInt(provinceModel.getValue()));
        req.setDistrictID(Integer.parseInt(districtModel.getValue()));
        req.setWardID(Integer.parseInt(wardModel.getValue()));
        req.setBusinessServiceID(Integer.parseInt(mBusinessService.getValue()));
        req.setPaymentAccountBank(bankModel.getId().toString());
        if (!TextUtils.isEmpty(edt_branch.getText()))
            req.setPaymentAccountBranch(edt_branch.getText().toString());
        req.setPaymentAccountName(edt_name_personal_bank.getText().toString());
        String documents = fileImgBusinessRegistration+";"+fileImgAddressSale+";"+fileImgTaxCode+";"+fileOther;
        req.setDocuments(documents);
        req.setQROption(qrOption);
        req.setLinkWebsite(edtLink.getText().toString());
        req.setImagesApp(fileImgFormalityOnline);
        if (mode.equals(Constants.MERCHANT_MODE_VIEW)) {
            req.setiD(mMerchantModel.getId());
            mPresenter.editMerchant(req);
        } else
            mPresenter.addMerchant(req);

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        // request qr option
        String qrOption = "";
        for (int i=0;i<adapter.getList().size();i++){
            if (adapter.getList().get(i).isChecked()) qrOption += adapter.getList().get(i).getId() +";";
        }
        String serviceType ="";
        for (int i=0;i<serviceTypeAdapter.getList().size();i++){
            if (serviceTypeAdapter.getList().get(i).isChecked()) serviceType += serviceTypeAdapter.getList().get(i).getId() +";";
        }
        if (Utils.isBlank(formalityType)){
            return new VerificationError("Bạn chưa chọn hình thức kinh doanh");
        }
        if (TextUtils.isEmpty(edt_mobile_number.getText())) {
            return new VerificationError("Bạn chưa nhập Số điện thoại đăng ký kinh doanh");
        }

        if (TextUtils.isEmpty(serviceType)){
            return new VerificationError("Vui lòng chọn dịch vụ");
        }
        if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE){
            if (TextUtils.isEmpty(edtTaxCode.getText())){
                return new VerificationError("Bạn chưa nhập mã số thuế");
            }
        }
        if (formalityType.contains(Constants.FORMALITY_TYPE_ONLINE)){
            if (TextUtils.isEmpty(edtNameCompany.getText())){
                return new VerificationError("Bạn chưa nhập tên công ty");
            }
        }
        if (TextUtils.isEmpty(edtNameQr.getText())){
            return new VerificationError("Bạn chưa nhập tên QR");
        }
        if (mBusinessService == null) {
            return new VerificationError("Bạn chưa Chọn loại dịch vụ hàng hóa");
        }
        if (TextUtils.isEmpty(qrOption)){
            return new VerificationError("Vui lòng chọn dịch vụ yêu cầu cung cấp");
        }
        if (TextUtils.isEmpty(auto_province.getText().toString())){
            return new VerificationError("Vui lòng chọn Tỉnh/thành phố");
        }
        if (TextUtils.isEmpty(auto_district.getText().toString())){
            return new VerificationError("Vui lòng chọn Quận/huyện");
        }
        if (TextUtils.isEmpty(auto_ward.getText().toString())){
            return new VerificationError("Vui lòng chọn Xã phường");
        }
        if (TextUtils.isEmpty(edt_address.getText().toString())){
            return new VerificationError("Vui lòng nhập địa chỉ");
        }
        if (businessType == Constants.BUSINESS_TYPE_VIETLOTT || businessType == Constants.BUSINESS_TYPE_SYNTHETIC){
            if (TextUtils.isEmpty(edt_post_id.getText())){
                return new VerificationError("Bạn chưa nhập PostID");

            }
        }
        if (TextUtils.isEmpty(edt_name.getText())) {
            return new VerificationError("Bạn chưa nhập Họ tên người đại diện");
        }
        if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE || businessType == Constants.BUSINESS_TYPE_HOUSEHOLD){
            if (TextUtils.isEmpty(edt_mobile_number_represent.getText())) {
                return new VerificationError("Bạn chưa nhập số điện thoại người đại diện");
            }
        }
        if (TextUtils.isEmpty(edt_pid_number.getText())) {
            return new VerificationError("Bạn chưa nhập Số giấy tờ tùy thân người đại diện");
        }

        if (TextUtils.isEmpty(fileImgBefore)) {
            return new VerificationError("Bạn chưa Chụp ảnh giấy tờ tùy thân mặt trước");
        }
        if (TextUtils.isEmpty(fileImgAfter)) {
            return new VerificationError("Bạn chưa Chụp ảnh giấy tờ tùy thân mặt sau");
        }
        if (TextUtils.isEmpty(edt_name_personal_bank.getText())) {
            return new VerificationError("Bạn chưa nhập tên chủ tài khoản");
        }
        if (TextUtils.isEmpty(edt_account_number.getText())) {
            return new VerificationError("Bạn chưa nhập Số tài khoản thanh toán");
        }
        if (this.bankModel == null) {
            return new VerificationError("Bạn chưa Chọn Ngân hàng thanh toán");
        }
        if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE || businessType == Constants.BUSINESS_TYPE_HOUSEHOLD){
            if (Utils.isBlank(fileImgBusinessRegistration)){
                return new VerificationError("Bạn chưa Chụp ảnh chứng nhận đăng ký kinh doanh/hộ kinh doanh");
            }
            if (Utils.isBlank(fileImgAddressSale)){
                return new VerificationError("Bạn chưa Chụp ảnh điểm bán hàng");
            }
        }
        if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE){
            if (Utils.isBlank(fileImgTaxCode)){
                return new VerificationError("Bạn chưa Chụp ảnh mã số thuế");
            }
        }

        if (formalityType.contains(Constants.FORMALITY_TYPE_ONLINE)){
            if (Utils.isBlank(edtLink.getText().toString())){
                return new VerificationError("Bạn chưa nhập Link website");
            }
        }

        addMerchant(true);
        return null;
    }

    @Override
    public void onSelected() {
        if (mPresenter != null) {
            dataModel = mPresenter.getPassData();
            edt_mobile_number.setText(dataModel.getMobileNumber());
        }
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.showToast(getContext(), error.getErrorMessage());
    }


    @Override
    public void showBusinessService(List<DictionaryBusinessServiceResponse> models) {
        this.mBusinessServices.addAll(models);
    }

    @Override
    public void showProvinces(List<DictionaryModel> dictionaryModels) {
        this.provinces.addAll(dictionaryModels);
    }

    @Override
    public void showDistricts(List<DictionaryModel> dictionaryModels) {
        this.districts.clear();
        this.districts.addAll(dictionaryModels);
    }

    @Override
    public void showWards(List<DictionaryModel> dictionaryModels) {
        this.wards.clear();
        this.wards.addAll(dictionaryModels);
    }

    @Override
    public void showBanks(List<BankModel> bankModels) {
        this.bankModels.addAll(bankModels);
    }

    @Override
    public void showImage(String file) {
        switch (typeImage){
            case OTHER:{
                fileOther = file;
                image_other.getLayoutParams().height = 300;
                Utils.loadImageWithProgressView(requireContext(),image_other,Utils.getUrlImage(file),progressViewOther);
                break;
            }
            case CTTT_AFTER:{
                fileImgAfter = file;
                image_after.getLayoutParams().height = 300;
                Utils.loadImageWithProgressView(requireContext(),image_after,Utils.getUrlImage(file),progressViewAfter);
                break;
            }
            case CTTT_BEFORE:{
                fileImgBefore = file;
                Log.e("TAG", "showImage: "+ file );
                image_before.getLayoutParams().height = 300;
                Utils.loadImageWithProgressView(requireContext(),image_before,Utils.getUrlImage(file),progressViewBefore);
                break;
            }
            case BUSINESS_REGISTRATION:{
                fileImgBusinessRegistration = file;
                image_business_registration.getLayoutParams().height = 300;
                Utils.loadImageWithProgressView(requireContext(),image_business_registration,Utils.getUrlImage(file),progressViewBusinessRegistration);
                break;
            }
            case ADDRESS_SALE:{
                fileImgAddressSale =  file;
                image_address_sale.getLayoutParams().height = 300;
                Utils.loadImageWithProgressView(requireContext(),image_address_sale,Utils.getUrlImage(file),progressViewAddressSale);
                break;
            }
            case TAX_CODE:{
                fileImgTaxCode = file;
                img_tax_code.getLayoutParams().height = 300;
                Utils.loadImageWithProgressView(requireContext(),img_tax_code,Utils.getUrlImage(file),progressViewTaxCode);
                break;
            }
            case FORMALITY_ONLINE:{
                fileImgFormalityOnline = file;
                img_formality_online.getLayoutParams().height = 300;
                Utils.loadImageWithProgressView(requireContext(),img_formality_online,Utils.getUrlImage(file),progressViewFormalityOnline);
            }
        }

    }

    @Override
    public void handlerEditMerchantSuccess() {
        saveEmployee();
    }

    @Override
    public void goToNextStep() {
        if (mCallback != null) {
            mCallback.goToNextStep();
        }
    }

    @Override
    public void showError(String message) {
        showErrorDialog(message,getChildFragmentManager());
    }

    @Override
    public void showMerchant(MerchantModel model) {
        mMerchantModel = model;
        dataModel = new RegisterPassDataModel();
        dataModel.setMerchantName(model.getName());
        dataModel.setMobileNumber(model.getMobileNumber());
        dataModel.setEmail(model.getEmail());
        mBusinessService = new BaseDialogModel();
        mBusinessService.setValue(model.getBusinessServiceID().toString());
        mBusinessService.setText(model.getBusinessServiceName());
        auto_business_service.setText(mBusinessService.getText());
        bankModel = new BankModel();
        for (BankModel bankModel1 : bankModels) {
            if (bankModel1.getId().toString().equals(model.getPaymentAccountBank())) {
                bankModel = bankModel1;
                break;
            }
        }
        auto_bank.setText(bankModel.getShortName());
        edt_name.setText(model.getRepresentativeName());
        edtOffice.setText(model.getRepresentativePosition());
        edt_mobile_number_represent.setText(model.getRepresentativeMobile());
        if (model.getIsSignContract().equals("N")) {
            radioYes.setChecked(false);
            radioNo.setChecked(true);
        }
        edt_post_id.setText(model.getPosID());
        edt_account_number.setText(model.getPaymentAccountNumber());
        edt_address.setText(model.getAddress());
        edt_mobile_number.setText(model.getMobileNumber());
        edtTaxCode.setText(model.getTaxCode());
        edtFax.setText(model.getFax());
        edt_business_registration_number.setText(model.getContractCode());
        edtNameCompany.setText(model.getCompanyName());
        edtNameQr.setText(model.getPrintQRName());
        edt_pid_number.setText(model.getRepresentativePIDNumber());
        edt_branch.setText(model.getPaymentAccountBranch());
        fileImgBefore = model.getRepresentativePIDImageBefore();
        fileImgAfter = model.getRepresentativePIDImageAfter();

        image_after.getLayoutParams().height = 300;
        image_before.getLayoutParams().height = 300;
        Utils.loadImageWithProgressView(requireContext(),image_before,Utils.getUrlImage(model.getRepresentativePIDImageBefore()),progressViewBefore);
        Utils.loadImageWithProgressView(requireContext(),image_after,Utils.getUrlImage(model.getRepresentativePIDImageAfter()),progressViewAfter);

        businessType = Integer.parseInt(mMerchantModel.getBusinessType());
        switch (businessType){
            case Constants.BUSINESS_TYPE_ENTERPRISE:
                tv_business.setText(getResources().getString(R.string.str_business_enterprise));
                layoutPostId.setVisibility(View.GONE);
                layout_radio.setVisibility(View.VISIBLE);
                tv_name_img_business_registration.setText(R.string.str_business_registration_required);
                tv_name_img_address_sale.setText(R.string.str_address_sale_required);
                break;
            case Constants.BUSINESS_TYPE_HOUSEHOLD:
                tv_business.setText(getResources().getString(R.string.str_business_household));
                layoutPostId.setVisibility(View.GONE);
                layout_radio.setVisibility(View.VISIBLE);
                tv_name_tax_code.setText("Mã số thuế");
                tv_name_img_tax_code.setText(getResources().getString(R.string.str_image_tax_code));
                tv_name_img_business_registration.setText(R.string.str_business_registration_required);
                tv_name_img_address_sale.setText(R.string.str_address_sale_required);
                break;
            case Constants.BUSINESS_TYPE_PERSONAL:
                tv_business.setText(getResources().getString(R.string.str_business_personal));
                disableFocusText(tv_formality,getString(R.string.str_formality_offline));
                disableFocusText(tv_service_type,getString(R.string.str_service_qr));
                layoutPostId.setVisibility(View.GONE);
                layout_office.setVisibility(View.GONE);
                layout_mobile_number_represent.setVisibility(View.GONE);
                layout_radio.setVisibility(View.GONE);
                tv_name_img_business_registration.setText(R.string.str_business_registration);
                tv_name_img_address_sale.setText(R.string.str_address_sale);
                break;
            case Constants.BUSINESS_TYPE_VIETLOTT:
                tv_business.setText(getResources().getString(R.string.str_business_vietlott));
                disableFocusText(tv_formality,getString(R.string.str_formality_offline));
                disableFocusText(tv_service_type,getString(R.string.str_service_qr));
                layoutPostId.setVisibility(View.VISIBLE);
                layout_office.setVisibility(View.GONE);
                layout_mobile_number_represent.setVisibility(View.GONE);
                layout_radio.setVisibility(View.GONE);
                tv_name_img_business_registration.setText(R.string.str_business_registration);
                tv_name_img_address_sale.setText(R.string.str_address_sale);
                break;
            case Constants.BUSINESS_TYPE_SYNTHETIC:
                tv_business.setText(getResources().getString(R.string.str_business_synthetic));
                disableFocusText(tv_formality,getString(R.string.str_formality_offline));
                disableFocusText(tv_service_type,getString(R.string.str_service_qr));
                layoutPostId.setVisibility(View.VISIBLE);
                layout_office.setVisibility(View.GONE);
                layout_mobile_number_represent.setVisibility(View.GONE);
                layout_radio.setVisibility(View.GONE);
                tv_name_img_business_registration.setText(R.string.str_business_registration);
                tv_name_img_address_sale.setText(R.string.str_address_sale);
                break;

        }

//        if (mMerchantModel.getFormalityType().equals(Constants.FORMALITY_TYPE_ONLINE)) tv_formality.setText(getResources().getText(R.string.str_formality_online));
//        else tv_formality.setText(getResources().getText(R.string.str_formality_offline));
        //load formality
        String[] formalitys = mMerchantModel.getFormalityType().split(";");
        formalityTypeAdapter.currentListNonChecked();
        String contentFormality = "";
        for (int i = 0;i< formalitys.length;i++) {
            if (!formalitys[i].isEmpty()) {
                if (formalitys[i].equals(Constants.FORMALITY_TYPE_ONLINE)){
                    contentFormality += getString(R.string.str_formality_online)+". ";
                    formalityTypeAdapter.selectItem(Constants.FORMALITY_TYPE_ONLINE);
                }
                if (formalitys[i].equals(Constants.FORMALITY_TYPE_OFFLINE)){
                    contentFormality += getString(R.string.str_formality_offline)+". ";
                    formalityTypeAdapter.selectItem(Constants.FORMALITY_TYPE_OFFLINE);
                }
            }
        }
        tv_formality.setText(contentFormality);
        formalityType = model.getFormalityType();

        switch (model.getStatus()) {
            case Constants.WAITING_APPROVAL:
                tv_status.setText(getResources().getText(R.string.str_waiting_approval));
                tv_status.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.order_status_w));
                break;
            case Constants.APPROVED:
                tv_status.setText(getResources().getText(R.string.str_approved));
                tv_status.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.order_status_s));
                break;
            case Constants.REFUSE:
                tv_status.setText(getResources().getText(R.string.str_refuse));
                tv_status.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.order_status_c));
                break;
        }
        try {
            auto_province.setText(model.getProvinceName());
            auto_district.setText(model.getDistrictName());
            auto_ward.setText(model.getWardName());
            provinceModel = new BaseDialogModel(model.getProvinceID().toString(),model.getProvinceName());
            districtModel = new BaseDialogModel(model.getDistrictID().toString(),model.getDistrictName());
            wardModel = new BaseDialogModel(model.getWardID().toString(),model.getWardName());
            mPresenter.getDistricts(Integer.parseInt(provinceModel.getValue()));
            mPresenter.getWards(Integer.parseInt(districtModel.getValue()));
            edt_name_personal_bank.setText(model.getPaymentAccountName());

            // load image chứng từ cần thiết
            String[] documentImages =  model.getDocuments().split(";");
            for (int i=0;i<documentImages.length;i++){
                if (!documentImages[i].isEmpty()){
                    if (i == 0){
                        image_business_registration.getLayoutParams().height = 300;
                        fileImgBusinessRegistration = documentImages[i];
                        Utils.loadImageWithProgressView(requireContext(),image_business_registration,Utils.getUrlImage(documentImages[i]),progressViewBusinessRegistration);
                    }
                    if (i == 1){
                        image_address_sale.getLayoutParams().height = 300;
                        fileImgAddressSale = documentImages[i];
                        Utils.loadImageWithProgressView(requireContext(),image_address_sale,Utils.getUrlImage(documentImages[i]),progressViewAddressSale);
                    }
                    if (i == 2){
                        img_tax_code.getLayoutParams().height = 300;
                        fileImgTaxCode = documentImages[i];
                        Utils.loadImageWithProgressView(requireContext(),img_tax_code,Utils.getUrlImage(documentImages[i]),progressViewTaxCode);
                    }
                    if (i==3){
                        image_other.getLayoutParams().height = 300;
                        fileOther = documentImages[i];
                        Utils.loadImageWithProgressView(requireContext(),image_other,Utils.getUrlImage(documentImages[i]),progressViewOther);
                    }
                }

            }
            // load qr code option
            String[] qrOptions = model.getQROption().split(";");
            String contentQr = "";
            adapter.currentListNonChecked();
            for (int i = 0;i< qrOptions.length;i++) {
                if (!qrOptions[i].isEmpty()) {
                    if (qrOptions[i].equals(Constants.QR_CODE_TINH)){
                        contentQr += getString(R.string.str_qr_code_tinh)+". ";
                        adapter.selectItem(Constants.QR_CODE_TINH);
                    }
                    if (qrOptions[i].equals(Constants.QR_CODE_DONG)){
                        contentQr += getString(R.string.str_qr_code_dong)+". ";
                        adapter.selectItem(Constants.QR_CODE_DONG);
                    }
                }
            }
            tv_qr_option.setText(contentQr);
            // load service type
            String[] services = model.getServiceType().split(";");
            String contentServices = "";
            serviceTypeAdapter.currentListNonChecked();
            for (int i = 0;i< services.length;i++) {
                if (!services[i].isEmpty()) {
                    if (services[i].equals(Constants.SERVICE_TYPE_QR)){
                        contentServices += getString(R.string.str_service_qr)+". ";
                        serviceTypeAdapter.selectItem(Constants.SERVICE_TYPE_QR);
                    }
                    if (services[i].equals(Constants.SERVICE_TYPE_TRA_SAU)){
                        contentServices += getString(R.string.str_service_tra_sau)+". ";
                        serviceTypeAdapter.selectItem(Constants.SERVICE_TYPE_TRA_SAU);
                    }
                }
            }
            tv_service_type.setText(contentServices);

            if (model.getAddInfoStatus().equals("A")){ // status  = A, cho phép bổ sung hồ sơ
                btn_ok.setVisibility(View.VISIBLE);
                layout_add_profile.setVisibility(View.VISIBLE);
                tv_add_profile.setText(model.getAddInfo());
            }

            // load data layout business formality
            if (formalityType.contains(Constants.FORMALITY_TYPE_ONLINE)){
                layout_business_online.setVisibility(View.VISIBLE);
            }else  layout_business_online.setVisibility(View.GONE);
            edtLink.setText(model.getLinkWebsite());
            if (!model.getImagesApp().isEmpty()){
                img_formality_online.getLayoutParams().height = 300;
                fileImgFormalityOnline = model.getImagesApp();
                Utils.loadImageWithProgressView(requireContext(),img_formality_online,Utils.getUrlImage(model.getImagesApp()),progressViewFormalityOnline);
            }

            // hide or show tax code , name company, image tax code
            if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE || businessType == Constants.BUSINESS_TYPE_HOUSEHOLD){
                layout_tax_code.setVisibility(View.VISIBLE);
                layout_company.setVisibility(View.VISIBLE);
                layout_img_tax_code.setVisibility(View.VISIBLE);

            }else {
                layout_tax_code.setVisibility(View.GONE);
                layout_company.setVisibility(View.GONE);
                layout_img_tax_code.setVisibility(View.GONE);
            }
            // load fax, mã só đăng ký kinh doanh
            if (businessType == Constants.BUSINESS_TYPE_ENTERPRISE){
                layout_fax.setVisibility(View.VISIBLE);
                layout_business_registration_number.setVisibility(View.GONE);
            } else if (businessType == Constants.BUSINESS_TYPE_HOUSEHOLD){
                layout_fax.setVisibility(View.GONE);
                layout_business_registration_number.setVisibility(View.VISIBLE);
            }else {
                layout_fax.setVisibility(View.GONE);
                layout_business_registration_number.setVisibility(View.GONE);
            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void gotoSplashWhenUpdateMerchant() {
        if (getActivity()!=null){
            startActivity(new Intent(getActivity(), SplashScreenActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void showPostId(int postId) {
        if (postId!=0){
            int maxLength = 12 - (postId+"").length();
            InputFilter filter = new InputFilter.LengthFilter(maxLength);
            edt_post_id.setFilters(new InputFilter[]{filter});
            tv_post_id_default.setVisibility(View.VISIBLE);
            tv_post_id_default.setText(postId+"");
            tvPostIdDefault = postId+"";
        }else {
            InputFilter filter = new InputFilter.LengthFilter(12);
            edt_post_id.setFilters(new InputFilter[]{filter});
            tvPostIdDefault = postId+"";
            tv_post_id_default.setVisibility(View.GONE);
        }
    }


    @SuppressLint("CheckResult")
    private void attemptSendMedia(ImagePicker imagePicker, MultipartBody.Part body) {
        try {
            if (imagePicker.isCamera()){
                File file = new File(imagePicker.getUri());
                Observable.fromCallable(() -> {
                            Uri uri = Uri.fromFile(new File(imagePicker.getUri()));
                            return BitmapUtils.processingBitmap(uri, getViewContext());
                        }).subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.io())
                        .map(bitmap ->
                                BitmapUtils.saveImage(bitmap, file.getParent(), "pno" + file.getName(), Bitmap.CompressFormat.JPEG, 50)
                        )
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                isSavedImage -> {
                                    String path = file.getParent() + File.separator + "pno" + file.getName();
                                    mPresenter.postImage(path);
                                    if (file.exists())
                                        file.delete();
                                },
                                onError ->{
                                    Logger.e("error save image");
                                    this.hideProgress();
                                }
                        );
            } else {
                mPresenter.postImage(body);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        this.showProgress();
    }


    private void openImagePickFragment() {
        new AddImageDialogFragment(requireContext(), (imagePicker,body) -> {
            try {
                attemptSendMedia(imagePicker,body);
            }catch (Exception e){
                e.printStackTrace();
            }
        }).show(getChildFragmentManager(),"MerchantFragment");
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        this.mCallback = callback;
//        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }
    private void saveEmployee(){
        try {
            SharedPref sharedPref = new SharedPref(requireActivity());
            EmployeeModel employeeModel = sharedPref.getEmployeeModel();
            employeeModel.setBankID(bankModel.getId());
            employeeModel.setPaymentAccName(edt_name.getText().toString());
            employeeModel.setPaymentAccNo(edt_account_number.getText().toString());
            employeeModel.setBankName(bankModel.getName());
            sharedPref.saveEmployee(employeeModel);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void showPopUpBusiness(){
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        View popup = getLayoutInflater().inflate(R.layout.business_pop_up_layout,null);

        PopupWindow popupWindow = new PopupWindow(popup,width-100,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(tv_business,0, 20,20);

        popup.findViewById(R.id.tv_business_type_enterprise).setOnClickListener(view1 ->{
            tv_business.setText(R.string.str_business_enterprise);
            businessType = Constants.BUSINESS_TYPE_ENTERPRISE;
            formalityType = Constants.FORMALITY_TYPE_ONLINE;
            formalityTypeAdapter.resetCurrentList();
            layout_business_online.setVisibility(View.VISIBLE);
            layout_tax_code.setVisibility(View.VISIBLE);
            layout_company.setVisibility(View.VISIBLE);
            layout_img_tax_code.setVisibility(View.VISIBLE);
            enableFocusText(tv_formality,getString(R.string.str_formality_online));
            enableFocusText(tv_service_type,getString(R.string.str_service_qr));
            layout_fax.setVisibility(View.VISIBLE);
            layout_business_registration_number.setVisibility(View.GONE);
            tv_name_tax_code.setText(getResources().getText(R.string.str_tax_code_required));
            tv_name_img_tax_code.setText(getResources().getText(R.string.str_image_tax_code_required));
            serviceTypeAdapter.resetCurrentList();
            layoutPostId.setVisibility(View.GONE);
            layout_office.setVisibility(View.VISIBLE);
            layout_mobile_number_represent.setVisibility(View.VISIBLE);
            layout_radio.setVisibility(View.VISIBLE);
            tv_name_img_business_registration.setText(R.string.str_business_registration_required);
            tv_name_img_address_sale.setText(R.string.str_address_sale_required);
            popupWindow.dismiss();
        });
        popup.findViewById(R.id.tv_business_type_household).setOnClickListener(view1 ->{
            tv_business.setText(R.string.str_business_household);
            businessType = Constants.BUSINESS_TYPE_HOUSEHOLD;
            formalityType = Constants.FORMALITY_TYPE_ONLINE;
            formalityTypeAdapter.resetCurrentList();
            layout_business_online.setVisibility(View.VISIBLE);
            layout_tax_code.setVisibility(View.VISIBLE);
            layout_company.setVisibility(View.VISIBLE);
            layout_img_tax_code.setVisibility(View.VISIBLE);
            enableFocusText(tv_formality,getString(R.string.str_formality_online));
            enableFocusText(tv_service_type,getString(R.string.str_service_qr));
            layout_fax.setVisibility(View.GONE);
            layout_business_registration_number.setVisibility(View.VISIBLE);
            tv_name_tax_code.setText(getResources().getString(R.string.str_tax_code));
            tv_name_img_tax_code.setText(getResources().getString(R.string.str_image_tax_code));
            serviceTypeAdapter.resetCurrentList();
            layoutPostId.setVisibility(View.GONE);
            layout_office.setVisibility(View.VISIBLE);
            layout_mobile_number_represent.setVisibility(View.VISIBLE);
            layout_radio.setVisibility(View.VISIBLE);
            tv_name_img_business_registration.setText(R.string.str_business_registration_required);
            tv_name_img_address_sale.setText(R.string.str_address_sale_required);
            popupWindow.dismiss();
        });
        popup.findViewById(R.id.tv_business_personal).setOnClickListener(view1 -> {
            tv_business.setText(R.string.str_business_personal);
            businessType = Constants.BUSINESS_TYPE_PERSONAL;
            disableFocusText(tv_formality,getResources().getString(R.string.str_formality_offline));
            disableFocusText(tv_service_type,getResources().getString(R.string.str_service_qr));
            layout_fax.setVisibility(View.GONE);
            layout_business_registration_number.setVisibility(View.GONE);
            tv_name_tax_code.setText(getResources().getText(R.string.str_tax_code));
            layout_tax_code.setVisibility(View.GONE);
            layout_company.setVisibility(View.GONE);
            layout_img_tax_code.setVisibility(View.GONE);
            edtTaxCode.setText("");
            edtNameCompany.setText("");
            fileImgTaxCode = "";
            img_tax_code.setImageResource(R.drawable.ic_cttt);
            img_tax_code.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            serviceTypeAdapter.resetCurrentList();
            formalityType = Constants.FORMALITY_TYPE_OFFLINE;
            layoutPostId.setVisibility(View.GONE);
            layout_office.setVisibility(View.GONE);
            edtOffice.setText("");
            layout_mobile_number_represent.setVisibility(View.GONE);
            edt_mobile_number_represent.setText("");
            layout_business_online.setVisibility(View.GONE);
            layout_radio.setVisibility(View.GONE);
            tv_name_img_business_registration.setText(getResources().getString(R.string.str_business_registration));
            tv_name_img_address_sale.setText(getResources().getString(R.string.str_address_sale));
            popupWindow.dismiss();
        });
        popup.findViewById(R.id.tv_business_vietlott).setOnClickListener(view1 -> {
            tv_business.setText(R.string.str_business_vietlott);
            businessType = Constants.BUSINESS_TYPE_VIETLOTT;
            disableFocusText(tv_service_type,getResources().getString(R.string.str_service_qr));
            disableFocusText(tv_formality,getResources().getString(R.string.str_formality_offline));
            layout_fax.setVisibility(View.GONE);
            layout_business_registration_number.setVisibility(View.GONE);
            tv_name_tax_code.setText(getResources().getText(R.string.str_tax_code));
            layout_tax_code.setVisibility(View.GONE);
            layout_company.setVisibility(View.GONE);
            layout_img_tax_code.setVisibility(View.GONE);
            edtTaxCode.setText("");
            edtNameCompany.setText("");
            fileImgTaxCode = "";
            img_tax_code.setImageResource(R.drawable.ic_cttt);
            img_tax_code.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            serviceTypeAdapter.resetCurrentList();
            formalityType = Constants.FORMALITY_TYPE_OFFLINE;
            layoutPostId.setVisibility(View.VISIBLE);
            layout_office.setVisibility(View.GONE);
            edtOffice.setText("");
            layout_mobile_number_represent.setVisibility(View.GONE);
            edt_mobile_number_represent.setText("");
            layout_business_online.setVisibility(View.GONE);
            layout_radio.setVisibility(View.GONE);
            tv_name_img_business_registration.setText(getResources().getString(R.string.str_business_registration));
            tv_name_img_address_sale.setText(getResources().getString(R.string.str_address_sale));
            popupWindow.dismiss();

        });
        popup.findViewById(R.id.tv_business_synthetic).setOnClickListener(view1 ->{
            tv_business.setText(R.string.str_business_synthetic);
            businessType = Constants.BUSINESS_TYPE_SYNTHETIC;
            disableFocusText(tv_service_type,getResources().getString(R.string.str_service_qr));
            disableFocusText(tv_formality,getResources().getString(R.string.str_formality_offline));
            layout_fax.setVisibility(View.GONE);
            layout_business_registration_number.setVisibility(View.GONE);
            tv_name_tax_code.setText(getResources().getText(R.string.str_tax_code));
            layout_tax_code.setVisibility(View.GONE);
            layout_company.setVisibility(View.GONE);
            layout_img_tax_code.setVisibility(View.GONE);
            edtTaxCode.setText("");
            edtNameCompany.setText("");
            fileImgTaxCode = "";
            img_tax_code.setImageResource(R.drawable.ic_cttt);
            img_tax_code.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            serviceTypeAdapter.resetCurrentList();
            formalityType = Constants.FORMALITY_TYPE_OFFLINE;
            layoutPostId.setVisibility(View.VISIBLE);
            layout_office.setVisibility(View.GONE);
            edtOffice.setText("");
            layout_mobile_number_represent.setVisibility(View.GONE);
            edt_mobile_number_represent.setText("");
            layout_radio.setVisibility(View.GONE);
            layout_business_online.setVisibility(View.GONE);
            tv_name_img_business_registration.setText(getResources().getString(R.string.str_business_registration));
            tv_name_img_address_sale.setText(getResources().getString(R.string.str_address_sale));
            popupWindow.dismiss();
        });

    }
    private void showPopUpFormality(){
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        View popup = getLayoutInflater().inflate(R.layout.formality_offline_pop_up_layout,null);

        PopupWindow popupWindow = new PopupWindow(popup,width-100,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(tv_formality,0, 20,20);

        ((RecyclerView) popup.findViewById(R.id.recycleView)).setAdapter(formalityTypeAdapter);
        formalityTypeAdapter.submitList();
//        popup.findViewById(R.id.tv_formality_offline).setOnClickListener(view1 ->{
//            tv_formality.setText(R.string.str_formality_offline);
//            formalityType = Constants.FORMALITY_TYPE_OFFLINE;
//            popupWindow.dismiss();
//            layout_business_online.setVisibility(View.GONE);
//        });
//        popup.findViewById(R.id.tv_formality_online).setOnClickListener(view1 ->{
//            tv_formality.setText(R.string.str_formality_online);
//            formalityType = Constants.FORMALITY_TYPE_ONLINE;
//            popupWindow.dismiss();
//            layout_business_online.setVisibility(View.VISIBLE);
//        });

    }
    private void showPopUpService(){
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        View popup = getLayoutInflater().inflate(R.layout.service_type_pop_up,null);

        PopupWindow popupWindow = new PopupWindow(popup,width-100,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(tv_service_type,0, 20,20);
        ((RecyclerView) popup.findViewById(R.id.recycleView)).setAdapter(serviceTypeAdapter);
        serviceTypeAdapter.submitList();

    }
    private void showPopUpQr(){
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        View popup = getLayoutInflater().inflate(R.layout.qr_option_pop_up,null);

        PopupWindow popupWindow = new PopupWindow(popup,width-100,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAsDropDown(tv_qr_option,0, 20,20);
        ((RecyclerView) popup.findViewById(R.id.recycleView)).setAdapter(adapter);
        adapter.submitList();

    }
    private void disableFocusText(AppCompatTextView tv,String content){
        tv.setText(content);
        tv.setEnabled(false);
        tv.setBackground(getResources().getDrawable(R.drawable.bg_disble_focus_text));
    }
    private void enableFocusText(AppCompatTextView tv,String content){
        tv.setEnabled(true);
        tv.setText(content);
        tv.setBackground(getResources().getDrawable(R.drawable.bg_edt_forgot_password));
    }


    enum TypeImage{
        CTTT_AFTER,
        CTTT_BEFORE,
        BUSINESS_REGISTRATION,
        ADDRESS_SALE,
        OTHER,
        TAX_CODE,
        FORMALITY_ONLINE
    }

}
