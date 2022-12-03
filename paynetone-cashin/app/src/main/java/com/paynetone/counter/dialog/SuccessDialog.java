package com.paynetone.counter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.paynetone.counter.R;
import com.paynetone.counter.callback.BankDialogCallback;
import com.paynetone.counter.callback.CloseCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccessDialog extends Dialog {
    private final CloseCallback mDelegate;
    @BindView(R.id.tv_ref_number)
    TextView tvRefNumber;
    @BindView(R.id.tv_close)
    TextView tvClose;

    public SuccessDialog(@NonNull Context context, String retRefNumber, CloseCallback callback) {
        super(context,R.style.TranslucentBackground);

        View view = View.inflate(getContext(), R.layout.dialog_success, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        tvRefNumber.setText("Mã giao dịch " + retRefNumber);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mDelegate.OnClose();
            }
        });
    }
}
