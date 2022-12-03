package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.SuccessPaymentDialogBinding
import com.paynetone.counter.databinding.TermPolicieDialogBinding
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class TermPoliciesDialog: BaseDialogFragment<TermPolicieDialogBinding>() {

    override fun initView(){
        binding.apply {
           ivBack.setSingleClick {
               dismiss()
           }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = TermPolicieDialogBinding.inflate(inflater,container,false)

    companion object {
        @JvmStatic
        fun getInstance() = TermPoliciesDialog().apply {
            arguments = Bundle().apply {

            }
        }
    }

}