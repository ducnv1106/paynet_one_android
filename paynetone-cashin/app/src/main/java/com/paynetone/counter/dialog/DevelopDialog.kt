package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.DevelopDialogBinding
import com.paynetone.counter.databinding.SuccessPaymentDialogBinding
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class DevelopDialog : BaseDialogFragment<DevelopDialogBinding>() {

    override fun initView(){
        binding.apply {
            animationView.setPadding(-60,-60,-60,-60)
            btnClose.setSingleClick {
                dismiss()
            }
        }
    }

    override fun initStyle(): Int =  R.style.DialogStyle

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = DevelopDialogBinding.inflate(inflater,container,false)
}