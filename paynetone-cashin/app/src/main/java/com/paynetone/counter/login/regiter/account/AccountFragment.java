package com.paynetone.counter.login.regiter.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.core.base.viper.ViewFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.paynetone.counter.R;
import com.paynetone.counter.dialog.TermPoliciesDialog;
import com.paynetone.counter.interfaces.RegisterPassData;
import com.paynetone.counter.model.RegisterPassDataModel;
import com.paynetone.counter.model.request.EmployeeAddNewRequest;
import com.paynetone.counter.utils.Toast;
import com.paynetone.counter.utils.Utils;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

import static com.paynetone.counter.utils.Constants.REGISTER_PASS_DATA;
import static com.paynetone.counter.utils.Constants.TYPE_GET_OTP_REGISTER_ACCOUNT;

public class AccountFragment extends ViewFragment<AccountContract.Presenter> implements AccountContract.View, BlockingStep {
    @BindView(R.id.edt_name)
    AppCompatEditText edt_name;
    @BindView(R.id.edt_mobile_number)
    AppCompatEditText edt_mobile_number;
    @BindView(R.id.edt_email)
    AppCompatEditText edt_email;
    @BindView(R.id.edt_password)
    AppCompatEditText edt_password;
    @BindView(R.id.edt_re_password)
    AppCompatEditText edt_re_password;
    @BindView(R.id.edt_otp)
    AppCompatEditText edt_otp;
    @BindView(R.id.btn_otp)
    Button btn_otp;
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkBox;
    @BindView(R.id.button_password_toggle)
    AppCompatImageView buttonPasswordToggle;
    @BindView(R.id.button_confirm_password_toggle)
    AppCompatImageView buttonConfirmPasswordToggle;
    @BindView(R.id.tv_policy_terms)
    AppCompatTextView tv_policy_terms;
    boolean IsGetOTP = true;

    StepperLayout.OnNextClickedCallback mCallback;


    public static AccountFragment getInstance() {
        return new AccountFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        btn_otp.setOnClickListener(view -> {
            if (IsGetOTP) {
                if (TextUtils.isEmpty(edt_mobile_number.getText())) {
                    Toast.showToast(getContext(), "Bạn chưa nhập số điện thoại");
                    return;
                }
                if (edt_mobile_number.getText().length()<10){
                    Toast.showToast(getContext(), "Bạn chưa nhập đúng số điện thoại");
                    return;
                }

                if (edt_mobile_number.getText().toString().charAt(0) != '0'){
                    Toast.showToast(requireContext(),R.string.error_warning_phone);
                    return;
                }

                mPresenter.getOTP(edt_mobile_number.getText().toString(),TYPE_GET_OTP_REGISTER_ACCOUNT);
            }
        });
        tv_policy_terms.setOnClickListener(view -> {
            TermPoliciesDialog.getInstance().show(getChildFragmentManager(), "PersonalFragment");
        });
        buttonPasswordToggle.setOnClickListener(view -> passwordToggle(edt_password,buttonPasswordToggle));
        buttonConfirmPasswordToggle.setOnClickListener(view -> passwordToggle(edt_re_password,buttonConfirmPasswordToggle));

    }

    private void countDownOTP(long diff) {
        CountDownTimer cdt = new CountDownTimer(diff, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                btn_otp.setText(StringUtils.leftPad(String.valueOf(minutes), 2, '0') + ":" + StringUtils.leftPad(String.valueOf(seconds), 2, '0'));
            }

            @Override
            public void onFinish() {
                cancel();
                btn_otp.setText("Gửi lại mã OTP");
                IsGetOTP = true;
            }
        };
        cdt.start();
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        this.mCallback = callback;
//        mPresenter.saveData(edt_mobile_number.getText().toString());
//        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if (TextUtils.isEmpty(edt_name.getText())) {
            return new VerificationError("Bạn chưa nhập Tên Merchant");
        }
        if (TextUtils.isEmpty(edt_email.getText().toString())){
            return new VerificationError("Bạn chưa nhập  Email");
        }
        if (!Utils.isBlank(edt_email.getText().toString()) && !Utils.checkEmail(edt_email.getText().toString())){
            return new VerificationError("Bạn chưa nhập đúng định dạng Email");
        }
        if (TextUtils.isEmpty(edt_password.getText())) {
            return new VerificationError("Bạn chưa nhập Mật khẩu");
        }
        if (edt_password.getText().length()<6 || edt_password.getText().length()>50){
            return new VerificationError(getResources().getString(R.string.str_message_field_password_invalid));
        }
        if (!Utils.passwordValidation(edt_password.getText().toString())){
            return new VerificationError(getResources().getString(R.string.str_message_field_password_not_strong));
        }
        if (TextUtils.isEmpty(edt_re_password.getText())) {
            return new VerificationError("Bạn chưa Nhập lại mật khẩu");
        } else {
            if (!edt_password.getText().toString().equals(edt_re_password.getText().toString())) {
                return new VerificationError("Mật khẩu không khớp");
            }
        }
        if (TextUtils.isEmpty(edt_mobile_number.getText())) {
            return new VerificationError("Bạn chưa nhập Số điện thoại");
        }
        if (edt_mobile_number.getText().toString().length()<10) {
            return new VerificationError("Số điện thoại bạn nhập không đúng");
        }
        if (!edt_mobile_number.getText().toString().substring(0,1).equals("0")){
            return new VerificationError("Số điện thoại bạn nhập không đúng");
        }
        if (TextUtils.isEmpty(edt_otp.getText())) {
            return new VerificationError("Bạn chưa nhập Mã OTP");
        }

        if (!checkBox.isChecked()){
            return new VerificationError("Vui lòng chọn điều khoản sử dụng!");
        }
        EmployeeAddNewRequest request = new EmployeeAddNewRequest();
        request.setAddress("");
        request.setEmail(edt_email.getText().toString());
        request.setEmpID(0);
        request.setFullName(edt_name.getText().toString());
        request.setIsRegister("Y");
        request.setMobileNumber(edt_mobile_number.getText().toString());
        request.setOtp(edt_otp.getText().toString());
        request.setPassword(edt_password.getText().toString());
        mPresenter.employeeAdd(request);
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.showToast(getContext(),error.getErrorMessage());
    }

    @Override
    public void showSuccess() {
        if (mCallback != null) {
            RegisterPassDataModel model = new RegisterPassDataModel();
            model.setEmail(edt_email.getText().toString());
            model.setMerchantName(edt_name.getText().toString());
            model.setMobileNumber(edt_mobile_number.getText().toString());
            mPresenter.saveData(model);
            mCallback.goToNextStep();
        }
    }

    @Override
    public void showError(String message) {
        Toast.showToast(getContext(), message);
    }

    @Override
    public void showSuccessOTP() {
        Toast.showToast(requireContext(),getResources().getString(R.string.xac_thuc_otp) + " " + Objects.requireNonNull(edt_mobile_number.getText()).toString() + " (kiểm tra cuộc gọi và ZALO)");
        IsGetOTP = false;
        countDownOTP(3 * 60 * 1000);
    }

    private void passwordToggle(AppCompatEditText edt,AppCompatImageView imageView){
        if (edt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            edt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                            R.drawable.ic_hide_password));
        } else {
            edt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                            R.drawable.ic_show_password));
        }
        edt.setSelection(edt.length());
    }
}
