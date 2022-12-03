package com.paynetone.counter.functions.payment.qr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.paynetone.counter.R;
import com.paynetone.counter.dialog.SuccessPaymentDialog;
import com.paynetone.counter.utils.BitmapUtils;
import com.paynetone.counter.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRActivity  extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.img_qr_code)
    ImageView img_qr_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_qr);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(Constants.WEB_VIEW_URL);
        Bitmap bitmap = BitmapUtils.generateQRBitmap(url);
        img_qr_code.setImageBitmap(bitmap);

        ivBack.setOnClickListener(view -> finish());
    }

}
