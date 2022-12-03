package com.paynetone.counter.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paynetone.counter.R
import com.paynetone.counter.adapter.WalletAdapter
import com.paynetone.counter.databinding.BottomWithDrawBinding
import com.paynetone.counter.databinding.BottomWithDrawWalletBinding
import com.paynetone.counter.model.response.WalletResponse
import com.paynetone.counter.utils.BindingAdapter.Companion.setShowOrHideDrawable
import com.paynetone.counter.utils.ExtraConst
import com.paynetone.counter.utils.autoCleared
import com.paynetone.counter.utils.setSingleClick

class SelectWithDrawWalletBottom : BottomSheetDialogFragment() {
    private var binding: BottomWithDrawWalletBinding by autoCleared()
    private var callBackListener: CallBackListener?= null
    private var listWallet = ArrayList<WalletResponse>()
    private lateinit var adapter: WalletAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        arguments?.apply {
            getParcelableArrayList<WalletResponse>(ExtraConst.EXTRA_WALLETS)?.let {
                listWallet.addAll(it)
            }

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
        binding = BottomWithDrawWalletBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView(){
        initAdapter()
    }
    private fun initAdapter(){
        adapter = WalletAdapter(requireContext()) {
            callBackListener?.onSelectWallet(it)
        }
        binding.recycleView.adapter=adapter
        adapter.submitList(listWallet)
    }

    fun onCallBackListener(callBackListener: CallBackListener){
        this.callBackListener=callBackListener
    }

    interface CallBackListener{
        fun onSelectWallet(walletResponse: WalletResponse)
    }
    companion object{
        @JvmStatic
        fun getInstance(listWallet:ArrayList<WalletResponse>) = SelectWithDrawWalletBottom().apply {
            arguments = Bundle().apply {
                putSerializable(ExtraConst.EXTRA_WALLETS,listWallet)
            }
        }
    }
}