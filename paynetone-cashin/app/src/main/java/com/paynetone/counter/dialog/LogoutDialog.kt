package com.paynetone.counter.dialog

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paynetone.counter.R
import com.paynetone.counter.databinding.DialogConfirmPinCodeBinding
import com.paynetone.counter.databinding.DialogLogoutBinding
import com.paynetone.counter.utils.InputFilterCharacterNumber
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.disableCopyPaste
import com.paynetone.counter.utils.setSingleClick

class LogoutDialog(val itemClick : () -> Unit) : BaseDialogFragment<DialogLogoutBinding>() {

    override fun initView() {
        binding.apply {
            btnOk.setSingleClick {
                itemClick()
                dismiss()
            }
            btnCancel.setSingleClick {
                dismiss()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun initStyle(): Int = R.style.DialogStyle

    override fun initCancelable(): Boolean = true

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = DialogLogoutBinding.inflate(inflater,container,false)



}