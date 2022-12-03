package com.core.base.viper;

import com.core.base.viper.interfaces.IInteractor;
import com.core.base.viper.interfaces.IPresenter;

/**
 * Base Interactor
 * Created by neo on 8/29/2016.
 */
public abstract class Interactor<P extends IPresenter> implements IInteractor<P> {
  protected P mPresenter;

  public Interactor(P presenter) {
    mPresenter = presenter;
  }

  public P getPresenter() {
    return mPresenter;
  }
}
