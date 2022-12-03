package com.paynetone.counter.functions.payment.qr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.core.base.viper.ViewFragment;
import com.paynetone.counter.R;
import com.paynetone.counter.dialog.BottomDialog;
import com.paynetone.counter.dialog.DevelopDialog;
import com.paynetone.counter.dialog.ErrorDialog;
import com.paynetone.counter.dialog.SuccessPaymentDialog;
import com.paynetone.counter.dialog.SupportVietQrDialog;
import com.paynetone.counter.main.MainActivity;
import com.paynetone.counter.model.request.OrderAddRequest;
import com.paynetone.counter.model.response.OrderAddResponse;
import com.paynetone.counter.model.OrderModel;
import com.paynetone.counter.utils.BitmapUtils;
import com.paynetone.counter.utils.Constants;
import com.paynetone.counter.utils.ExtraConst;
import com.paynetone.counter.utils.NumberUtils;
import com.paynetone.counter.utils.Toast;
import com.paynetone.counter.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;


public class QRFragment extends ViewFragment<QRContract.Presenter> implements QRContract.View {
    private final String TAG = "QRFragment";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.img_qr_code)
    ImageView img_qr_code;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_providers_logo)
    TextView tv_providers_logo;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    @BindView(R.id.tv_support1)
    TextView tv_support1;
//    @BindView(R.id.tv_providers)
//    TextView tv_providers;
    @BindView(R.id.webview_shoppe)
    WebView wb_shoppe;

    OrderAddResponse orderAddResponse;
    OrderAddRequest mOrderAddRequest;

    @BindView(R.id.tv_support3)
    AppCompatTextView tvSupport;
    @BindView(R.id.img_logo_header)
    AppCompatImageView imgLogoHeader;
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @BindView(R.id.img_logo_qr)
    AppCompatImageView imgLogoQr;
    @BindView(R.id.progress_loading_webview)
    LottieAnimationView progress_loading_webview;

    public static QRFragment getInstance() {
        return new QRFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_qr;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        setup();

        if (mPresenter != null) {
            orderAddResponse = mPresenter.getOrderAddResponse();
            mOrderAddRequest = mPresenter.getOrderAddRequest();
            tvCode.setText(getResources().getString(R.string.str_code_order).concat(" "+ orderAddResponse.getOrderCode()));
            tv_amount.setText(NumberUtils.formatPriceNumber(mOrderAddRequest.getAmount()) + " VNĐ");

            if (mOrderAddRequest.getPaymentType() == Constants.PAYMENT_TYPE_SHOPEE || mOrderAddRequest.getPaymentType() == Constants.PAYMENT_TYPE_VIETQR) {
                wb_shoppe.setVisibility(View.VISIBLE);
                img_qr_code.setVisibility(View.GONE);
            }else {
                wb_shoppe.setVisibility(View.GONE);
                img_qr_code.setVisibility(View.VISIBLE);
            }
            tv_support1.setText("Sử dụng ứng dụng "+mPresenter.getProviderResponse().getName()+" để thanh toán");
            String text = "";
            if (mPresenter.getProviderResponse().getPaymentType() == Constants.PAYMENT_TYPE_VIETQR){
                 text = "Các ứng dụng hỗ trợ Viet QR";
            }else {
                 text = "Các ứng dụng hỗ trợ " + mPresenter.getProviderResponse().getName();
            }

            showImageProvider();
            setTextProvider(mPresenter.getProviderResponse().getName());
            String styledText = "<u><font color='#007FFF'>"+text+"</font><u>";
            tvSupport.setText(Html.fromHtml(String.format(styledText)), TextView.BufferType.SPANNABLE);

            if (mPresenter.getProviderResponse().getPaymentType() == Constants.PAYMENT_TYPE_VN_PAY ||
                mPresenter.getProviderResponse().getPaymentType() == Constants.PAYMENT_TYPE_VIETQR){
                tvSupport.setVisibility(View.VISIBLE);
            }

            if (orderAddResponse.getReturnURL()==null){
                showErrorDialog();
            }else {
                if (img_qr_code.getVisibility()==View.VISIBLE){
                    Bitmap bitmap = BitmapUtils.generateQRBitmap(orderAddResponse.getReturnURL());
                    img_qr_code.setImageBitmap(bitmap);
                    progress_loading_webview.setVisibility(View.GONE);
                }else {
                    eventWebView();
                    wb_shoppe.getSettings().setJavaScriptEnabled(true);
                    wb_shoppe.loadUrl(orderAddResponse.getReturnURL());
                    wb_shoppe.getSettings().setLoadWithOverviewMode(true);
                    wb_shoppe.getSettings().setUseWideViewPort(true);

                }
            }



        }


    }

    @OnClick({R.id.iv_back, R.id.btn_ok,R.id.iv_home,R.id.tv_support3})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.btn_ok:
                mPresenter.getByCode();
                break;
            case R.id.iv_home:
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.tv_support3:
                SupportVietQrDialog.getInstance(mPresenter.getProviderResponse().getPaymentType()).show(getChildFragmentManager(),"QRFragment");
                break;
        }
    }

    @Override
    public void showOrder(OrderModel order) {
        BottomDialog bottomDialog = new BottomDialog(order);
        bottomDialog.setCancelable(false);
        bottomDialog.show(getChildFragmentManager(), TAG);
    }

    private void loadImageWithGlide(ImageView imageView){
        Glide.with(imageView)
                .load(Utils.getUrlImage(mPresenter.getProviderResponse().getIcon()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }
    private void showImageProvider(){
        loadImageWithGlide(img_logo);
        loadImageWithGlide(imgLogoHeader);
        loadImageWithGlide(imgLogoQr);
    }
    private void setTextProvider(String name){
        tvTitle.setText(name);
        tv_providers_logo.setText(name);
    }
    private void setup() {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(
                mMessageReceiver,
                new IntentFilter("notify-receive-listener")
        );
    }
    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(ExtraConst.EXTRA_MESSAGE);
            SuccessPaymentDialog.getInstance(message != null ? message : "").show(getChildFragmentManager(),"MainFragment");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
    }
    private void eventWebView(){
        wb_shoppe.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progress_loading_webview.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                showErrorDialog();
                super.onReceivedError(view, request, error);
            }
        });
    }
    private void showErrorDialog(){
        progress_loading_webview.setVisibility(View.GONE);
        ErrorDialog.getInstance("Không tải được dữ liệu!").show(getChildFragmentManager(),"QRFragment");
    }
}
