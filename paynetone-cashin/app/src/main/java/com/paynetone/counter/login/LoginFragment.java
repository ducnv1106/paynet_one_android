package com.paynetone.counter.login;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.core.base.viper.ViewFragment;
import com.core.utils.AppUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.paynetone.counter.dialog.SelectBusinessTypeDialog;
import com.paynetone.counter.forgotpassword.requestotp.RequestOTPActivity;
import com.paynetone.counter.login.regiter.RegisterActivity;
import com.paynetone.counter.main.MainActivity;
import com.paynetone.counter.R;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.SharedPref;
import com.paynetone.counter.utils.Toast;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends ViewFragment<LoginContract.Presenter> implements LoginContract.View {
    @BindView(R.id.edt_mobile_number)
    TextInputEditText edtPhoneNumber;
    @BindView(R.id.edt_password)
    TextInputEditText edtPass;
    @BindView(R.id.ll_register)
    LinearLayout ll_register;

    SharedPref sharedPref;
    String firebaseToken = "";

    @BindView(R.id.textFieldPassword)
    TextInputLayout textFieldPassword;
    @BindView(R.id.textFieldMobileNumber)
    TextInputLayout textFieldMobileNumber;

    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initLayout() {
        super.initLayout();

        sharedPref = new SharedPref(requireContext());
        String mode = sharedPref.getString(Constants.KEY_ANDROID_PAYMENT_MODE,"");
        if(mode.equals(Constants.ANDROID_PAYMENT_MODE_SHOW)){
            ll_register.setVisibility(View.VISIBLE);
        }
        String mobileNumber = sharedPref.getString(Constants.KEY_MOBILE_NUMBER,"");
        if (!mobileNumber.isEmpty()){
            edtPhoneNumber.setText(mobileNumber);
        }
        changeTextListener();
        getFirebaseMessageToken();
    }

    @OnClick({R.id.btn_login, R.id.btn_register,R.id.rootView,R.id.tv_forgot_password})
    public void OnClick(View view) {
        if (!isClickAble()) return;
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(requireActivity(),RegisterActivity.class);
                startActivity(intent);
                clearInputValidate();
                break;
            case R.id.rootView:
                AppUtils.hideKeyboard(view);
                break;
            case R.id.tv_forgot_password:
                goToRequestOTP();
                clearInputValidate();
                break;
        }
    }

    private void login() {
        textFieldMobileNumber.setError("");
        textFieldPassword.setError("");
        String phoneNumber = Objects.requireNonNull(edtPhoneNumber.getText()).toString();
        String passWord = Objects.requireNonNull(edtPass.getText()).toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            textFieldMobileNumber.setError(getString(R.string.error_empty_phone));
            return;
        }
        if (!org.apache.commons.lang3.math.NumberUtils.isDigits(phoneNumber) || phoneNumber.contains(".") || phoneNumber.contains(",")) {
            textFieldMobileNumber.setError(getString(R.string.error_warning_phone));
            return;
        }
        String phone = NumberUtils.convertVietNamPhoneNumber(phoneNumber);
        if (phone.length() != 10) {
            textFieldMobileNumber.setError(getString(R.string.error_warning_phone));
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            textFieldPassword.setError(getString(R.string.error_password_empty));
            return;
        }
//        if (passWord.length()<6){
//            textFieldPassword.setError(getString(R.string.message_field_password_invalid));
//            return;
//        }

        mPresenter.login(phone, passWord,firebaseToken);
    }

    private void changeTextListener(){
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = s.toString();
                if (mobile.length()>0) textFieldMobileNumber.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = s.toString();
                if (mobile.length()>0) textFieldPassword.setError("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void clearInputValidate() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareClearInputValidate();
            }
        }, 500L);
    }

    private void goToRequestOTP(){
        Intent intent = new Intent(requireActivity(), RequestOTPActivity.class);
        startActivity(intent);
    }

    private void getFirebaseMessageToken(){
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            firebaseToken = task.getResult();
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

//    public void clearInputValidate(){
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                prepareClearInputValidate();
//            }
//        }, 500L);
//    }
    private void prepareClearInputValidate(){
        edtPhoneNumber.clearFocus();
        edtPass.setText("");
        edtPass.clearFocus();
    }


}
