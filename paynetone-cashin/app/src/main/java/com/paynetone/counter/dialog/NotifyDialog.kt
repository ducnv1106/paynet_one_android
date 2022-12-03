package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paynetone.counter.R

import com.paynetone.counter.databinding.NotifyDialogBinding
import com.paynetone.counter.utils.ExtraConst
import com.paynetone.counter.utils.setSingleClick

class NotifyDialog : BaseDialogFragment<NotifyDialogBinding>() {

    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            message = getString(ExtraConst.EXTRA_MESSAGE,"")
        }
    }

    override fun initView() {
        binding.apply {
            tvMessage.text = message
            btnClose.setSingleClick {
                dismiss()
            }
        }
    }

    override fun initStyle(): Int = R.style.DialogStyle

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        NotifyDialogBinding.inflate(inflater, container, false)

    companion object {
        @JvmStatic
        fun getInstance(message:String) = NotifyDialog().apply {
            arguments = Bundle().apply {
                putString(ExtraConst.EXTRA_MESSAGE,message)

            }
        }
    }
}