package com.paynetone.counter.forgotpassword.requestpassword

import android.util.Log
import com.core.base.viper.ViewFragment
import com.paynetone.counter.base.PaynetOneActivity
import com.paynetone.counter.forgotpassword.requestotp.RequestOTPPresenter
import com.paynetone.counter.utils.Constants.EXTRA_OPT
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_PHONE_NUMBER

class ForgotPasswordActivity : PaynetOneActivity() {
    override fun onCreateFirstFragment(): ViewFragment<*> {
        return ForgotPasswordPresenter(this,intent.getStringExtra(EXTRA_PHONE_NUMBER) ?: "").fragment as ViewFragment<*>
    }
}