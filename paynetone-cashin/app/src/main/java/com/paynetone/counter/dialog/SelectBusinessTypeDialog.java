package com.paynetone.counter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.paynetone.counter.R;
import com.paynetone.counter.callback.CloseCallback;
import com.paynetone.counter.utils.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectBusinessTypeDialog extends Dialog {
    private final CloseCallback mDelegate;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.rd_business)
    RadioButton rd_business;
    @BindView(R.id.rl_household)
    RadioButton rl_household;
    @BindView(R.id.rl_personal)
    RadioButton rl_personal;

    public SelectBusinessTypeDialog(@NonNull Context context, CloseCallback callback) {
        super(context, R.style.TranslucentBackground);

        View view = View.inflate(getContext(), R.layout.dialog_select_business_type, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rl_personal.isChecked()) {
                    dismiss();
                    mDelegate.OnClose();
                } else {
                    Toast.showToast(getContext(), "Chức năng chỉ dùng cho Merchant cá nhân, Vui lòng truy cập Merchant Site để đăng ký");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
