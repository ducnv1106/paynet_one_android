package com.paynetone.counter.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.core.base.BaseActivity;
import com.paynetone.counter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MessageDialog extends Dialog implements DialogInterface.OnClickListener {

    private OnClickListener mOnClickListener;
    private String mContent;
    protected BaseActivity mActivity;
    @BindView(R.id.tv_content)
    TextView tvContent;

    public MessageDialog(@NonNull Context context, String content) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContent = content;
        init(context);
    }

    public MessageDialog(@NonNull Context context, String content, OnClickListener onClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContent = content;
        this.mOnClickListener = onClickListener;
        init(context);
    }

    public MessageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected MessageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        getWindow().setWindowAnimations(R.style.DialogAnimation);
        mActivity = (BaseActivity) context;
        setContentView(R.layout.dialog_message);
        final Unbinder unbinder = ButterKnife.bind(this);
        tvContent.setText(mContent);
        setOnDismissListener(dialog -> unbinder.unbind());
        setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                switch (event.getScanCode()) {
                    case KeyEvent.KEYCODE_CLEAR:
                    case KeyEvent.KEYCODE_CAPS_LOCK:
                        //your Action code
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (mOnClickListener != null) {
                                mOnClickListener.onClick(dialog, 0);
                            }
                            dismiss();
                        }
                        return true;
                }
                return false;
            }
        });
    }


    @OnClick({R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_close:
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(this, 0);
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
