package com.core.base.viper.interfaces;

import android.content.DialogInterface;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Views can present on a {@link ContainerView}
 * Created by neo on 9/15/2016.
 */
public interface PresentView<P extends IPresenter> extends IView<P> {
  void showProgress();

  void hideProgress();

  void showAlertDialog(String message);
  void showAlertDialog(String message, DialogInterface.OnClickListener onClickListener);

  void onRequestError(String errorCode, String errorMessage);

  void onNetworkError(boolean shouldShowPopup);

  void onRequestSuccess();

  void showErrorDialog(String message, FragmentManager fragmentManager);

  FragmentManager getChildFragmentManager();

  FragmentManager getFragmentManager();

  /**
   * check is top fragment of current activity or not
   */
  boolean isShowing();

  /**
   * check fragment is visible or hidden
   */
  boolean isViewHidden();
}
