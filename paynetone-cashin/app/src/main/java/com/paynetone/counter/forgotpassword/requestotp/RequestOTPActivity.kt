package com.paynetone.counter.forgotpassword.requestotp

import com.core.base.viper.ViewFragment
import com.paynetone.counter.base.PaynetOneActivity
import com.paynetone.counter.forgotpassword.requestotp.RequestOTPPresenter

class RequestOTPActivity : PaynetOneActivity() {
    override fun onCreateFirstFragment(): ViewFragment<*> {
        return RequestOTPPresenter(this).fragment as ViewFragment<*>
    }
}