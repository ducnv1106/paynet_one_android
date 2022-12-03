package com.paynetone.counter.functions.service.topup

import com.core.base.viper.ViewFragment
import com.core.base.viper.interfaces.IPresenter
import com.paynetone.counter.base.PaynetOneActivity
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_AMOUNT
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_URL_TOPUP_ADDRESS

class TopUpActivity : PaynetOneActivity() {
    override fun onCreateFirstFragment(): ViewFragment<out IPresenter<*, *>> {
        return TopUpPresenter(this,
            intent.getStringExtra(EXTRA_URL_TOPUP_ADDRESS) ?: "",
                intent.getLongExtra(EXTRA_AMOUNT,0L)).fragment as ViewFragment<*>
    }
}