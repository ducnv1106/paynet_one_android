package com.paynetone.counter.dialog

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.adapter.HistoryDialogAdapter
import com.paynetone.counter.adapter.HistoryQrAdapter
import com.paynetone.counter.databinding.HistoryDialogBinding
import com.paynetone.counter.functions.history.HistoryAdapter
import com.paynetone.counter.model.EmployeeModel
import com.paynetone.counter.model.OrderModel
import com.paynetone.counter.model.request.OrderSearchRequest
import com.paynetone.counter.model.request.TranSearchRequest
import com.paynetone.counter.model.response.TranSearchResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HistoryPaymentQRDialog : BaseDialogFragment<HistoryDialogBinding>() {
    private lateinit var  adapter : HistoryQrAdapter
    private lateinit var employeeModel : EmployeeModel

    override fun loadControlsAndResize(binding: HistoryDialogBinding) {
        binding.apply {
            refreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimary
            )
        }
    }
    override fun initView() {
        employeeModel = SharedPref.getInstance(requireContext()).employeeModel
        initAdapter()
        requestTransSearch()
        onRefreshListener()
    }
    private fun initAdapter(){
        adapter = HistoryQrAdapter(requireContext())
        binding.recycleView.adapter = adapter
    }

    fun requestTransSearch(branchID: Int? = null, storeID: Int? = null, stall: Int? = null): Disposable {
        adapter.clearAllContent()
        showProgressDialog()
        val request = OrderSearchRequest()
        request.paynetID = employeeModel.paynetID
        branchID?.let {
            request.paynetID = it
        }
        storeID?.let {
            request.paynetID = it
        }
        stall?.let {
            request.paynetID = it
        }
        return NetWorkController.orderSearch(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00") {
                    val responses = NetWorkController.getGson()
                        .fromJson<ArrayList<OrderModel>>(result.data,
                            object : TypeToken<ArrayList<OrderModel?>?>() {}.type
                        )
                    adapter.updateContent(responses)
                } else {
                    if (parentFragment is NewHistoryDialog){
                        (parentFragment as NewHistoryDialog).also {  parentFragment ->
                            if (parentFragment.currentPosition==0){
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

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            requestTransSearch()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refreshLayout.isRefreshing = false

            }, 2000L)

        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HistoryDialogBinding  =  HistoryDialogBinding.inflate(inflater,container,false)
}