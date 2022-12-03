package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.adapter.SupportVietQrAdapter
import com.paynetone.counter.databinding.SuccessPaymentDialogBinding
import com.paynetone.counter.databinding.SupportVietQrDialogBinding
import com.paynetone.counter.utils.*

class SupportVietQrDialog : BaseDialogFragment<SupportVietQrDialogBinding>() {

    private var typeSupport:Int = 0

    private lateinit var adapter : SupportVietQrAdapter

    override fun initStyle(): Int = R.style.DialogStyleVietQr

    override fun initCancelable(): Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            typeSupport = getInt(ExtraConst.EXTRA_TYPE_SUPPORT_VIETQR,0)
        }
    }

    override fun initView(){
        initAdapter()
        binding.apply {
            btnClose.setSingleClick {
                dismiss()
            }
            if (typeSupport == Constants.PAYMENT_TYPE_VIETQR){
                tvTitle.text = "Các ứng dụng hỗ trợ VIETQR"
            }else{
                tvTitle.text = "Các ứng dụng hỗ trợ VNPAY"
            }
        }
    }
    private fun initAdapter(){
        adapter = SupportVietQrAdapter(requireContext(),typeSupport)
        binding.recycleView.apply {
            adapter = this@SupportVietQrDialog.adapter
            addItemDecoration(MarginDecoration(10,4))
            this@SupportVietQrDialog.adapter.submitList()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = SupportVietQrDialogBinding.inflate(inflater,container,false)

    companion object {
        @JvmStatic
        fun getInstance(typeSupport:Int) = SupportVietQrDialog().apply {
            arguments = Bundle().apply {
                putInt(ExtraConst.EXTRA_TYPE_SUPPORT_VIETQR,typeSupport)
            }
        }
    }
}