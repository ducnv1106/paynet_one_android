package com.paynetone.counter.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.paynetone.counter.R;
import com.paynetone.counter.base.PaynetOneActivity;
import com.paynetone.counter.functions.history.HistoryActivity;
import com.paynetone.counter.functions.withdraw.WithDrawActivity;
import com.paynetone.counter.utils.Constants;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends PaynetOneActivity {

    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new MainPresenter((ContainerView)getBaseActivity()).getFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Bundle bundle = intent.getExtras();
//        if (bundle!=null){
//            if (bundle.containsKey(Constants.NOTIFICATION_DATA_TRANSFER)){
//                Bundle data = bundle.getBundle(Constants.NOTIFICATION_DATA_TRANSFER);
//                String title = data.getString("title");
//                String body = data.getString("body");
//                if (title != null && body != null){
//                    startActivity(new Intent(this, HistoryActivity.class));
//                }
//
//            }
//        }

    }

}