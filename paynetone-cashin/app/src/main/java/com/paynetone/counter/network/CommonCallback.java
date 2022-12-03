package com.paynetone.counter.network;

import android.accounts.AuthenticatorException;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.paynetone.counter.R;
import com.paynetone.counter.model.SimpleResult;
import com.paynetone.counter.utils.DialogUtils;
import com.paynetone.counter.utils.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.channels.NoConnectionPendingException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonCallback <T extends SimpleResult> implements Callback<T> {
    private Context mActivity;
    private static final String TAG = CommonCallback.class.getSimpleName();
    private boolean isDismissProgress = true;

    private boolean isInternalErrorDisplayed = true;
    private boolean mIsShowToast = true;
    private boolean mIsCheckResponseCode = true;

    public CommonCallback(Context activity) {
        this.isInternalErrorDisplayed = false;
        mActivity = activity;
    }

    protected CommonCallback(Context activity, boolean isInternalErrorDisplayed) {
        this.isInternalErrorDisplayed = isInternalErrorDisplayed;
        mActivity = activity;
    }


    protected void onSuccess(Call<T> call, Response<T> response) {
        if (isDismissProgress) {
            DialogUtils.dismissProgressDialog();
        }
    }

    protected void onError(Call<T> call, String message) {
        if (isDismissProgress) {
            DialogUtils.dismissProgressDialog();
        }
        if (mIsShowToast) {
            if (mActivity != null)
                Toast.showToast(mActivity, message);
        }
    }

    public void setDismissProgress(boolean dismissProgress) {
        isDismissProgress = dismissProgress;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null && response.code() >= 200 && response.code() < 300 && response.body() != null) {
            if (response.body().getErrorCode() != null) {
                if (response.body().getErrorCode().equals("00")) {
                    onSuccess(call, response);
                } else {
                    if (isDismissProgress) {
                        DialogUtils.dismissProgressDialog();
                    }
                    this.onError(call, response.body().getMessage());
                }
            } else {
                this.onError(call, mActivity.getString(R.string.error_system_upgrading));
            }
        } else if (response != null && (response.code() < 200 || response.code() >= 300)) {
            if (mActivity != null && isInternalErrorDisplayed) {
                this.onError(call, mActivity.getString(R.string.error_system_upgrading));
            } else {
                if (isDismissProgress) {
                    DialogUtils.dismissProgressDialog();
                }
                if (mActivity != null && response.body() != null)
                    Toast.showToast(mActivity, response.body().getMessage());
            }
        } else {
            if (mActivity != null && this.isInternalErrorDisplayed) {
                this.onError(call, mActivity.getString(R.string.error_fail_default));
            } else {
                if (isDismissProgress) {
                    DialogUtils.dismissProgressDialog();
                }
                if (mActivity != null && response != null && response.body() != null)
                    Toast.showToast(mActivity, response.body().getMessage());
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable error) {
        if (mActivity != null && this.isInternalErrorDisplayed) {
            Toast.showToast(mActivity, R.string.error_fail_default);
        }

        if (error instanceof TimeoutException || error instanceof NoConnectionPendingException) {
            this.onError(call, "Thời gian kết nối đến máy chủ quá lâu");
        } else if (error instanceof AuthenticatorException) {
            this.onError(call, "Lỗi xác thực tới máy chủ");
        } else if (error instanceof SocketTimeoutException || error instanceof TimeoutException || error instanceof ConnectException) {
            this.onError(call, "Không thể kết nối đến máy chủ");
        } else if (error instanceof NetworkErrorException) {
            this.onError(call, "Vui lòng kiểm tra lại kết nối mạng");
        } else if (error instanceof ParseException) {
            this.onError(call, "Parse error");
        } else if (error instanceof JsonSyntaxException) {
            this.onError(call, "Dữ liệu trả về sai cấu trúc");
        } else {
            Log.e(TAG, "onFailure: "+ error.getMessage() );
            this.onError(call, "Lỗi kết nối hệ thống");
        }
    }

    public void showToast(boolean isShowToast) {
        this.mIsShowToast = isShowToast;
    }

    public void isCheckResponseCode(boolean checkResponseCode) {
        this.mIsCheckResponseCode = checkResponseCode;
    }
}
