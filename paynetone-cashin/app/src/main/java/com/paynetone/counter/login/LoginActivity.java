package com.paynetone.counter.login;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.core.base.viper.ViewFragment;
import com.core.base.viper.interfaces.ContainerView;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.paynetone.counter.base.PaynetOneActivity;
import com.paynetone.counter.dialog.NotifyDialog;
import com.paynetone.counter.main.MainActivity;
import com.sanojpunchihewa.updatemanager.UpdateManager;
import com.sanojpunchihewa.updatemanager.UpdateManagerConstant;

public class LoginActivity extends PaynetOneActivity {

    UpdateManager mUpdateManager;
    private AppUpdateManager mAppUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private static final int RC_APP_UPDATE = 11;
    @Override
    public ViewFragment onCreateFirstFragment() {
        return (ViewFragment)new LoginPresenter((ContainerView)getBaseActivity()).getFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUpdateManager = UpdateManager.Builder(this).mode(UpdateManagerConstant.IMMEDIATE);
        mUpdateManager.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUpdateInApp();
    }

    private void initUpdateInApp(){
        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        initListenerInApp();

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, LoginActivity.this, RC_APP_UPDATE);

                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip

            } else {
                Log.e("TAG", "checkForAppUpdateAvailability: something else");
            }
        });
    }
    private void initListenerInApp(){
        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
            } else if (state.installStatus() == InstallStatus.INSTALLED){
                if (mAppUpdateManager != null){
                    mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                }

            } else {
                Log.i("TAG", "InstallStateUpdatedListener: state: " + state.installStatus());
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                showDialogUpdateFailed();
            }
        }
    }

    private void showDialogUpdateFailed(){
        String err =  "Cập nhật ứng dụng thất bại!";
        NotifyDialog notifyDialog = NotifyDialog.getInstance(err);
        notifyDialog.setCallback(() -> {
            this.finish();
            System.exit(0);
        });
        notifyDialog.show(getSupportFragmentManager(),"WithDrawFragment");

    }
}
