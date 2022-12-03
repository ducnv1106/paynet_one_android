package com.paynetone.counter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.paynetone.counter.R;

import butterknife.ButterKnife;

public class ProcessFragDialog  extends Dialog {

    public ProcessFragDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = View.inflate(context, R.layout.dialog_progress, null);
        setContentView(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
