package com.paynetone.counter.dialog

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.adapter.HistoryBonusAdapter
import com.paynetone.counter.databinding.DialogHistoryAccountBonusBinding
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.WithdrawRequest
import com.paynetone.counter.model.request.WithdrawSearchRequest
import com.paynetone.counter.model.response.AddPINCodeResponse
import com.paynetone.counter.model.response.WithdrawSearchResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.PrefConst
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.setSingleClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HistoryAccountBonusDialog : BaseDialogFragment<DialogHistoryAccountBonusBinding>() {
    private var adapter:HistoryBonusAdapter?=null
    override fun loadControlsAndResize(binding: DialogHistoryAccountBonusBinding) {
        binding.apply {
            refreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimary
            )

            ivBack.setSingleClick {
                dismiss()
            }
        }
    }
    override fun initView() {
        initAdapter()
        requestReferralTransSearch()
        onRefreshListener()
    }
    private fun initAdapter(){
        if (adapter==null) adapter = HistoryBonusAdapter(requireContext())
        binding.recycleView.adapter = adapter
    }

    private fun requestReferralTransSearch(): Disposable {
        adapter?.removeAll()
        val merchantID = SharedPref.getInstance(requireContext()).paynet.merchantID
        showProgressDialog()
        val request = WithdrawSearchRequest(merchantID,"T")
        return NetWorkController.requestReferralTransSearch(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    val response = NetWorkController.getGson().fromJson<ArrayList<WithdrawSearchResponse>>(result.data,object : TypeToken<ArrayList<WithdrawSearchResponse>>(){}.type)
                    adapter?.submitList(response)
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
                hideProgressDialog()
            }
    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            requestReferralTransSearch()
            binding.refreshLayout.isRefreshing = false
//            Handler(Looper.getMainLooper()).postDelayed({
//                binding.refreshLayout.isRefreshing = false
//
//            }, 2000L)

        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogHistoryAccountBonusBinding = DialogHistoryAccountBonusBinding.inflate(inflater,container,false)
}