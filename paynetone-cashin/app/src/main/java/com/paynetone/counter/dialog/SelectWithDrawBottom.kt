package com.paynetone.counter.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.BottomWithDrawBinding
import com.paynetone.counter.enumClass.SelectWithDraw
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.Constants.BUSINESS_TYPE_SYNTHETIC
import com.paynetone.counter.utils.Constants.BUSINESS_TYPE_VIETLOTT
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_BUSINESS_TYPE
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_WITH_DRAW_BANK
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class SelectWithDrawBottom : BottomSheetDialogFragment() {

    private var binding: BottomWithDrawBinding by autoCleared()
    private var callBackListener: CallBackListener ?= null
    private lateinit var selectWithDrawBank: SelectWithDraw
    private var businessType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        arguments?.apply {
            selectWithDrawBank = getSerializable(EXTRA_WITH_DRAW_BANK) as SelectWithDraw
            businessType = getInt(EXTRA_BUSINESS_TYPE)
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.behavior.skipCollapsed = true
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations=R.style.MyDialogAnimation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomWithDrawBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView(){
        try {
            binding.apply {
                layoutBank.setSingleClick {
                    icTickBank.setShowOrHideDrawable(true)
                    icTickHanMuc.setShowOrHideDrawable(false)
                    icTickWallet.setShowOrHideDrawable(false)
                    icTickVietlott.setShowOrHideDrawable(false)
                    selectWithDrawBank = SelectWithDraw.BANK
                    callBackListener?.onSelectWithDrawBank(selectWithDrawBank)
                }
                layoutWallet.setSingleClick {
                    icTickBank.setShowOrHideDrawable(false)
                    icTickVietlott.setShowOrHideDrawable(false)
                    icTickHanMuc.setShowOrHideDrawable(false)
                    icTickWallet.setShowOrHideDrawable(true)
                    selectWithDrawBank = SelectWithDraw.E_WALLET
                    callBackListener?.onSelectWithDrawBank(selectWithDrawBank)
                }
                layoutVietlott.setSingleClick {
                    icTickBank.setShowOrHideDrawable(false)
                    icTickWallet.setShowOrHideDrawable(false)
                    icTickHanMuc.setShowOrHideDrawable(false)
                    icTickVietlott.setShowOrHideDrawable(true)
                    selectWithDrawBank = SelectWithDraw.VIETLOTT
                    callBackListener?.onSelectWithDrawBank(selectWithDrawBank)

                }
                layoutHanMuc.setSingleClick {
                    icTickBank.setShowOrHideDrawable(false)
                    icTickWallet.setShowOrHideDrawable(false)
                    icTickVietlott.setShowOrHideDrawable(false)
                    icTickHanMuc.setShowOrHideDrawable(true)
                    selectWithDrawBank = SelectWithDraw.HAN_MUC
                    callBackListener?.onSelectWithDrawBank(selectWithDrawBank)
                }
                when(selectWithDrawBank){
                    SelectWithDraw.BANK -> icTickBank.setShowOrHideDrawable(true)
                    SelectWithDraw.E_WALLET -> icTickWallet.setShowOrHideDrawable(true)
                    SelectWithDraw.VIETLOTT -> icTickVietlott.setShowOrHideDrawable(true)
                    SelectWithDraw.HAN_MUC -> icTickHanMuc.setShowOrHideDrawable(true)
                }
                when(businessType){
                    BUSINESS_TYPE_VIETLOTT, BUSINESS_TYPE_SYNTHETIC -> layoutVietlott.visibility = View.VISIBLE
                    else ->{}
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }


    }

    fun onCallBackListener(callBackListener: CallBackListener){
        this.callBackListener=callBackListener
    }

    interface CallBackListener{
        fun onSelectWithDrawBank(selectWithDraw: SelectWithDraw)
    }
    companion object{
        @JvmStatic
        fun getInstance(selectWithDraw: SelectWithDraw, businessType:Int) = SelectWithDrawBottom().apply {
            arguments = Bundle().apply {
                putSerializable(EXTRA_WITH_DRAW_BANK,selectWithDraw)
                putInt(EXTRA_BUSINESS_TYPE,businessType)
            }
        }
    }
}