package com.paynetone.counter.functions.han_muc

import com.core.base.viper.ViewFragment
import com.paynetone.counter.base.PaynetOneActivity

class ListBankActivity : PaynetOneActivity() {
    override fun onCreateFirstFragment(): ViewFragment<*> {

        return ListBankPresenter(this).fragment as ViewFragment<*>
    }
}