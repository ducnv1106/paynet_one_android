package com.paynetone.counter.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.core.common.BuildConfig;
import com.paynetone.counter.R;
import com.paynetone.counter.widgets.ProgressView;

import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class Utils {

    public static String getUrlImage(String fileName){
        return com.paynetone.counter.BuildConfig.IMAGE_URL + "/Assets/Images/" + fileName;
    }

    public static String getStatusName(String code){
        String name = "Chờ thanh toán";
        switch (code){
            case Constants.STATUS_W:
                name = "Chờ thanh toán";
                break;
            case Constants.STATUS_S:
                name = "Đã thanh toán";
                break;
            case Constants.STATUS_C:
                name = "Hủy";
                break;
        }
        return name;
    }

    public static String getStatusWithdrawName(int code){
        String name = "Từ chối";
        switch (code){
            case 0:
                name = "Đã chuyển tiền";
                break;
            case 1:
                name = "Chờ xử lý";
                break;
            case 2:
                name = "Từ chối";
                break;
            case 3:
                name = "Chờ xác nhận";
                break;
            case 4:
                name ="Chuyển tiền lỗi";
                break;
        }
        return name;
    }

    public static OkHttpClient getUnsafeOkHttpClient(int readTimeOut, int connectTimeOut,String userName) {

        try {
            // Create a trust manager that does not validate certificate chains
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            // Install the all-trusting trust manager
            final SSLContext tls = SSLContext.getInstance("TLS");
            tls.init(null, new TrustManager[] { trustManager },
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = tls
                    .getSocketFactory();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, trustManager);
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                    .hostnameVerifier(hostnameVerifier);//org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                   /* String credentials = "lottnet:dms";
                    String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);*/
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Authorization", "Basic " + userName);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            builder.retryOnConnectionFailure(true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadImageWithProgressView(Context context, ImageView imageView, String imageUrl, ProgressView progress){
        progress.setVisibility(View.VISIBLE);
        progress.setAnimation(12,800);
        Glide.with(context)
                .load(imageUrl) // image url
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        progress.setAnimation(null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        progress.setAnimation(null);
                        return false;
                    }
                })
//                .placeholder(R.drawable.loading) // any placeholder to load at start// any image in case of error
                .error(R.drawable.ic_no_images)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);  // imageview object
    }

    public static boolean passwordValidation(String password) {

        if(password.length()>=6) {
            Pattern letter = Pattern.compile("(?=.*[A-Z])(?=.*[a-z]).*");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|?{}.;\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }
    public static boolean isBlank(String text) {
        return text == null || text.trim().equals("");
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}
