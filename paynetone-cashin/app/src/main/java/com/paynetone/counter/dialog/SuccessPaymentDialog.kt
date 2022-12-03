package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.SuccessPaymentDialogBinding
import com.paynetone.counter.utils.ExtraConst
import com.paynetone.counter.utils.setSingleClick

class SuccessPaymentDialog : BaseDialogFragment<SuccessPaymentDialogBinding>() {

    private lateinit var message:String
    private var listener: CallBackListener?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            message = getString(ExtraConst.EXTRA_MESSAGE,"")
        }
    }


    override fun initView(){
        binding.apply {
            btnClose.setSingleClick {
                dismiss()
                listener?.onCloseClicked()
            }
            if (message.isNotEmpty()) tvMessage.text = message
        }
    }
    fun setCallBackListener(listener: CallBackListener){
        this.listener = listener
    }

    override fun initStyle(): Int  = R.style.DialogStyle

    override fun initCancelable(): Boolean  = true

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) =  SuccessPaymentDialogBinding.inflate(inflater,container,false)

    interface CallBackListener{
        fun onCloseClicked()
    }

    companion object {
        @JvmStatic
        fun getInstance( message:String) = SuccessPaymentDialog().apply {
            arguments = Bundle().apply {
                putString(ExtraConst.EXTRA_MESSAGE,message)
            }
        }
    }

}