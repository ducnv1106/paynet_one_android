package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import com.paynetone.counter.R
import com.paynetone.counter.databinding.DialogConfirmPinCodeBinding
import com.paynetone.counter.utils.InputFilterCharacterNumber
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.disableCopyPaste
import com.paynetone.counter.utils.setSingleClick
import java.io.File


class ConfirmPINCodeDialog : BaseDialogFragment<DialogConfirmPinCodeBinding>() {

    private var listener: CallBackListener ?= null

    override fun initView() {
        binding.apply {
            edtPinCode.disableCopyPaste()
            initViewPINCode()
            btnOk.setSingleClick {
                if (edtPinCode.text.toString().isEmpty()){
                    Toast.showToast(requireContext(),"Vui lòng nhập mã PIN")
                    return@setSingleClick
                }
                if (edtPinCode.text.toString().length != 4 ){
                    Toast.showToast(requireContext(),"Mã PIN không được bỏ trống!")
                    return@setSingleClick
                }
                listener?.itemClick(edtPinCode.text.toString())
                dismiss()
            }
            btnCancel.setSingleClick {
                dismiss()
            }
            imgClose.setSingleClick {
                dismiss()
            }
            listenerEdtPINCode()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun initStyle(): Int = R.style.DialogStyle

    override fun initCancelable(): Boolean = true

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = DialogConfirmPinCodeBinding.inflate(inflater,container,false)

    private fun listenerEdtPINCode(){
        binding.edtPinCode.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length==4){
                    listener?.itemClick(binding.edtPinCode.text.toString())
                    dismiss()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
    
    private fun initViewPINCode(){
        binding.apply {
            val filters = arrayOfNulls<InputFilter>(2)
            filters[0] = InputFilter.LengthFilter(4)
            filters[1] = InputFilterCharacterNumber()
            edtPinCode.filters = filters
        }
    }
    fun setCallBackListener(listener: CallBackListener){
        this.listener = listener
    }

    interface CallBackListener{
        fun itemClick(pinCode:String)
    }


}