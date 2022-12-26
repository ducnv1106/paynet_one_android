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
import com.paynetone.counter.databinding.BottomAccountWithDrawBinding
import com.paynetone.counter.enumClass.SelectAccountWithDraw
import com.paynetone.counter.enumClass.SelectWithDraw
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.ExtraConst
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class SelectAccountWithDrawBottom : BottomSheetDialogFragment() {

    private var binding: BottomAccountWithDrawBinding by autoCleared()
    private var callBackListener: CallBackListener ?= null
    private lateinit var selectAccountWithDraw: SelectAccountWithDraw
    private var businessType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        arguments?.apply {
            selectAccountWithDraw = getSerializable(ExtraConst.EXTRA_WITH_ACCOUNT_WITH_DRAW) as SelectAccountWithDraw
            businessType = getInt(ExtraConst.EXTRA_BUSINESS_TYPE)
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
        dialog?.window?.attributes?.windowAnimations= R.style.MyDialogAnimation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomAccountWithDrawBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView(){
        try {
            binding.apply {
                layoutWithDrawQr.setSingleClick {
                    icTickQr.setShowOrHideDrawable(true)
                    icTickGtgt.setShowOrHideDrawable(false)
                    icTickBonus.setShowOrHideDrawable(false)
                    selectAccountWithDraw = SelectAccountWithDraw.ACCOUNT_QR
                    callBackListener?.onSelectAccountWithDrawBank(selectAccountWithDraw)
                }
                layoutWithDrawGtgt.setSingleClick {
                    icTickQr.setShowOrHideDrawable(false)
                    icTickGtgt.setShowOrHideDrawable(true)
                    icTickBonus.setShowOrHideDrawable(false)
                    selectAccountWithDraw = SelectAccountWithDraw.ACCOUNT_GTGT
                    callBackListener?.onSelectAccountWithDrawBank(selectAccountWithDraw)
                }

                layoutWithDrawBonus.setSingleClick {
                    icTickQr.setShowOrHideDrawable(false)
                    icTickGtgt.setShowOrHideDrawable(false)
                    icTickBonus.setShowOrHideDrawable(true)
                    selectAccountWithDraw = SelectAccountWithDraw.ACCOUNT_BONUS
                    callBackListener?.onSelectAccountWithDrawBank(selectAccountWithDraw)
                }

                when(selectAccountWithDraw){
                    SelectAccountWithDraw.ACCOUNT_QR -> icTickQr.setShowOrHideDrawable(true)
                    SelectAccountWithDraw.ACCOUNT_GTGT -> icTickGtgt.setShowOrHideDrawable(true)
                    SelectAccountWithDraw.ACCOUNT_BONUS -> icTickBonus.setShowOrHideDrawable(true)
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
        fun onSelectAccountWithDrawBank(selectAccountWithDraw: SelectAccountWithDraw)
    }
    companion object{
        @JvmStatic
        fun getInstance(selectAccountWithDraw: SelectAccountWithDraw) = SelectAccountWithDrawBottom().apply {
            arguments = Bundle().apply {
                putSerializable(ExtraConst.EXTRA_WITH_ACCOUNT_WITH_DRAW,selectAccountWithDraw)
            }
        }
    }
}