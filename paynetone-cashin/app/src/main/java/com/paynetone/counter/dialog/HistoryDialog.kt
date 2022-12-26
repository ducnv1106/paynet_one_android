package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.adapter.HistoryDialogAdapter
import com.paynetone.counter.adapter.HistoryViewPager
import com.paynetone.counter.databinding.HistoryDialogBinding
import com.paynetone.counter.functions.service.billing.BillingFragment
import com.paynetone.counter.model.EmployeeModel
import com.paynetone.counter.model.request.TranSearchRequest
import com.paynetone.counter.model.response.TranSearchResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.ExtraConst
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.setSingleClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HistoryDialog : BaseDialogFragment<HistoryDialogBinding>() {

    private lateinit var adapter: HistoryDialogAdapter
    private lateinit var employeeModel : EmployeeModel

    override fun initView() {
        employeeModel = SharedPref.getInstance(requireContext()).employeeModel
        initAdapter()
        requestTransSearch()
        onRefreshListener()
        binding.apply {
            refreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimary
            )
            ivBack.setSingleClick {
                dismiss()
            }
            imgFilter.setSingleClick {
                if (SharedPref.getInstance(requireContext()).isAccountStall) {
                    Toast.showToast(
                        requireContext(),
                        getString(R.string.str_not_authorized_function)
                    )
                    return@setSingleClick
                } else {
                    FilterHistoryPaymentDialog(requireContext(),
                        object : FilterHistoryPaymentDialog.CallBackListener {
                            override fun onConfirmClicked(
                                branchID: Int?,
                                storeID: Int?,
                                stallID: Int?
                            ) {
                                adapter.clearAllContent()
                                requestTransSearch(branchID, storeID, stallID)
                            }

                        }).show(childFragmentManager, "HistoryDialog")
                }

            }
        }

    }
    private fun initAdapter(){
        adapter = HistoryDialogAdapter(requireContext())
        binding.recycleView.adapter = adapter
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        HistoryDialogBinding.inflate(inflater, container, false)

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            requestTransSearch()
            binding.refreshLayout.isRefreshing = false
//            Handler(Looper.getMainLooper()).postDelayed({
//                binding.refreshLayout.isRefreshing = false
//
//            }, 2000L)

        }
    }

    fun requestTransSearch(branchID: Int? = null, storeID: Int? = null, stall: Int? = null): Disposable {
        adapter.clearAllContent()
        showProgressDialog()
        val request = TranSearchRequest(employeeModel.paynetID ?: 0, null, null, null, null)
        branchID?.let {
            request.paynetID = it
        }
        storeID?.let {
            request.paynetID = it
        }
        stall?.let {
            request.paynetID = it
        }
        return NetWorkController.requestTransSearch(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00") {
                    val responses = NetWorkController.getGson()
                        .fromJson<ArrayList<TranSearchResponse>>(result.data,
                            object : TypeToken<ArrayList<TranSearchResponse?>?>() {}.type
                        )
                    adapter.updateContent(responses)
                } else {
                    if (parentFragment is NewHistoryDialog){
                        (parentFragment as NewHistoryDialog).also {  parentFragment ->
                            if (parentFragment.currentPosition==1){
                                Toast.showToast(requireContext(), result.message)
                            }
                        }
                    }

                }
            }) { throwable ->
                hideProgressDialog()
                throwable.printStackTrace()
            }
    }

    companion object {
        @JvmStatic
        fun getInstance() = HistoryDialog().apply {
            arguments = Bundle().apply {

            }
        }
    }
}


