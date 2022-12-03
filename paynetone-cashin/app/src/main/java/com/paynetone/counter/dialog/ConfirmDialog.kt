package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ConfirmDialogBinding
import com.paynetone.counter.utils.ExtraConst
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class ConfirmDialog : BaseDialogFragment<ConfirmDialogBinding>() {

    private lateinit var message: String
    private var callBackDialog: CallBackDialog?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            message = getString(ExtraConst.EXTRA_MESSAGE,"")
        }
    }

    override fun initView(){
        binding.apply {
            tvMessage.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
            btnAgree.setSingleClick {
                callBackDialog?.onAgreeClick()
                dismiss()
            }
            btnCancel.setSingleClick {
                dismiss()
            }

        }
    }

    interface CallBackDialog{
        fun onAgreeClick()
    }

    override fun initCancelable(): Boolean = true

    override fun initStyle(): Int = R.style.DialogStyle

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ConfirmDialogBinding.inflate(inflater,container,false)

    fun setCallBackListener(callBackDialog: CallBackDialog){
        this.callBackDialog = callBackDialog
    }

    companion object {
        @JvmStatic
        fun getInstance( message:String) = ConfirmDialog().apply {
            arguments = Bundle().apply {
                putString(ExtraConst.EXTRA_MESSAGE,message)
            }
        }
    }


}