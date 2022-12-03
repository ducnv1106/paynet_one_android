package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.GuideRechargeDialogBinding
import com.paynetone.counter.databinding.NapHanMucDialogBinding
import com.paynetone.counter.utils.ExtraConst
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class GuideRechargeDialog : BaseDialogFragment<GuideRechargeDialogBinding>() {

    private var code:String? = null
    private var codeMerchant:String? = null
    private var provinceCde:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            getString(ExtraConst.EXTRA_CODE)?.let {
                code = it
            }
            getString(ExtraConst.EXTRA_CODE_MERCHANT)?.let {
                codeMerchant = it
            }
            getString(ExtraConst.EXTRA_PROVINCE_CODE)?.let {
                provinceCde = it
            }

        }
    }
    override fun initView() {
        binding.apply {
            btnConfirm.setSingleClick {
                dismiss()
            }
            val provinceCode = provinceCde ?:(SharedPref.getInstance(requireContext()).addressPayNet?.provinceCode ?: "00")
            if (code!=null){
                tvContent3.text = "NAPHM ${if ( provinceCode.length<2) "0${provinceCode}" else provinceCode}$code"
                tvContent1.text = "${resources.getString(R.string.str_content_guide1)}"
            } else{
                tvContent1.text = "${resources.getString(R.string.str_content_guide1)}"
                tvContent3.text = "NAPHM <Mã tài khoản hạn mức>"
            }

        }

    }
    override fun initStyle(): Int = R.style.DialogStyle

    override fun initCancelable(): Boolean = false

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = GuideRechargeDialogBinding .inflate(inflater,container,false)

    companion object {
        @JvmStatic
        fun getInstance( code:String?, codeMerchant:String?,provinceCde:String?) = GuideRechargeDialog().apply {
            arguments = Bundle().apply {
                code?.let {
                    putString(ExtraConst.EXTRA_CODE,code)
                }
                codeMerchant?.let {
                    putString(ExtraConst.EXTRA_CODE_MERCHANT,codeMerchant)
                }
                provinceCde?.let {
                    putString(ExtraConst.EXTRA_PROVINCE_CODE,it)
                }

            }
        }
    }

}