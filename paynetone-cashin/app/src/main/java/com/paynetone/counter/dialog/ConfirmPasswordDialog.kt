package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.DialogConfirmPasswordBinding
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class ConfirmPasswordDialog(val mContext: Context, private val itemListener:ItemListener) : BaseDialogFragment<DialogConfirmPasswordBinding>() {

    override fun initView(){
        binding.apply {
            edtPassword.disableCopyPaste()
            btnLogin.setSingleClick {
                if (edtPassword.text.toString().isEmpty()){
                    Toast.showToast(requireContext(),"Vui lòng nhập mật khẩu")
                    return@setSingleClick
                }
                itemListener.onItemListener(edtPassword.text.toString())
                dismiss()
            }
            btnClose.setSingleClick {
                dismiss()
            }

            buttonPasswordToggle.setSingleClick {
                edtPassword.passwordToggle(requireContext(),buttonPasswordToggle)
            }
            rootView.setSingleClick {
                it.hideKeyboard()
            }
        }
    }

    interface ItemListener{
        fun onItemListener(password:String)
    }

    override fun initCancelable(): Boolean = true

    override fun initStyle(): Int =  R.style.DialogStyleConfirmPassword

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = DialogConfirmPasswordBinding.inflate(inflater,container,false)


}