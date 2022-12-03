package com.paynetone.counter.personal

import com.core.base.viper.ViewFragment
import com.core.base.viper.interfaces.ContainerView
import com.core.base.viper.interfaces.IPresenter
import com.paynetone.counter.base.PaynetOneActivity
import com.paynetone.counter.main.MainPresenter

class PersonalActivity : PaynetOneActivity() {
    override fun onCreateFirstFragment(): ViewFragment<out IPresenter<*, *>> {
        return PersonalPresenter(baseActivity as ContainerView).fragment as ViewFragment<*>
    }
}