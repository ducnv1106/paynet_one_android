package com.paynetone.counter.dialog

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.adapter.HistoryQrAdapter
import com.paynetone.counter.adapter.ReferralCodeAdapter
import com.paynetone.counter.databinding.DialogReferralCodeBinding
import com.paynetone.counter.model.EmployeeModel
import com.paynetone.counter.model.OrderModel
import com.paynetone.counter.model.request.OrderSearchRequest
import com.paynetone.counter.model.request.ReferralCodeRequest
import com.paynetone.counter.model.response.ReferralMerchantResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.handlerCopyText
import com.paynetone.counter.utils.setSingleClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ReferralCodeDialog : BaseDialogFragment<DialogReferralCodeBinding>() {
    private lateinit var employeeModel : EmployeeModel
    private  lateinit var referralAdapter : ReferralCodeAdapter

    override fun loadControlsAndResize(binding: DialogReferralCodeBinding) {
        binding.apply {
            ivBack.setSingleClick { dismiss() }
            tvReferralCode.setSingleClick {
                tvReferralCode.handlerCopyText(employeeModel.mobileNumber,requireContext())
            }
            imgCopy.setSingleClick {
                tvReferralCode.handlerCopyText(employeeModel.mobileNumber,requireContext())
            }
        }
    }
    override fun initView() {
        employeeModel = SharedPref.getInstance(requireContext()).employeeModel
        binding.apply {
            val message =   "Mã giới thiệu của tôi: <font color='#007fff'>${employeeModel.mobileNumber}</font>"
            tvReferralCode.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        initAdapter()
        requestReferralCode()
        onRefreshListener()
    }

    private fun initAdapter(){
        referralAdapter = ReferralCodeAdapter(requireContext())
        binding.recycleView.adapter = referralAdapter
    }

    private fun requestReferralCode(): Disposable {
//        adapter.clearAllContent()
        referralAdapter.removeAll()
        val request = ReferralCodeRequest(mobileNumber = employeeModel.mobileNumber)

        return NetWorkController.referralCodes(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00") {
                    val responses = NetWorkController.getGson()
                        .fromJson<ArrayList<ReferralMerchantResponse>>(result.data,
                            object : TypeToken<ArrayList<ReferralMerchantResponse>>() {}.type
                        )
                    referralAdapter.submitList(responses)
                }else if (result.errorCode != "01"){
                    toast(result.message)
                }
            }) { throwable ->
                hideProgressDialog()
                throwable.printStackTrace()
                toast(throwable.message ?: "")
            }
    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            requestReferralCode()
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
    ): DialogReferralCodeBinding = DialogReferralCodeBinding.inflate(inflater,container,false)
}