package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.DialogForGotPassWordSuccessBinding
import com.paynetone.counter.databinding.GuideRechargeDialogBinding
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class ForgotPassWordSuccessDialog : BaseDialogFragment<DialogForGotPassWordSuccessBinding>() {

    override fun initView() {
        binding.apply {
            btnCompleted.setSingleClick {
                activity?.finish()
                dismiss()

            }

        }

    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = DialogForGotPassWordSuccessBinding.inflate(inflater,container,false)

}