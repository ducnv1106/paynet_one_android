package com.paynetone.counter.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.adapter.ManagerHanMucAdapter
import com.paynetone.counter.databinding.ManagerHanMucDialogBinding
import com.paynetone.counter.enumClass.StateNotify
import com.paynetone.counter.model.PaynetGetBalanceByIdResponse
import com.paynetone.counter.model.request.BaseRequest
import com.paynetone.counter.model.response.AddPINCodeResponse
import com.paynetone.counter.model.response.AddressPayNetIDResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.observer.DisplayElement
import com.paynetone.counter.observer.Observer
import com.paynetone.counter.observer.StateNotifyData
import com.paynetone.counter.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ManagerHanMucDialog : BaseDialogFragment<ManagerHanMucDialogBinding>(), DisplayElement<Any>, Observer<Any> {

    private lateinit var listContent:ArrayList<PaynetGetBalanceByIdResponse>
    private var amountOutWard:Long = 0L
    private var amountOutWardGTGT:Long = 0L
    private var amountBonus:Long = 0L

    private lateinit var codeMerchant : String
    private lateinit var adapter : ManagerHanMucAdapter
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            listContent = getParcelableArrayList<PaynetGetBalanceByIdResponse>(ExtraConst.EXTRA_LIST_MANAGER_HAN_MUC) as ArrayList<PaynetGetBalanceByIdResponse>
            amountOutWard = getLong(ExtraConst.EXTRA_AMOUNT_OUTWARD,0L)
            amountOutWardGTGT = getLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_GTGT,0L)
            amountBonus = getLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_BONUS,0L)
        }
    }

    override fun initView() {
        sharedPref = SharedPref.getInstance(requireContext())
        codeMerchant = sharedPref.paynet?.code?.toString() ?: "00000000"
        initAdapter()
        binding.apply {
            ivBack.setSingleClick {
                dismiss()
            }
        }

    }

    private fun initAdapter(){
        adapter =  ManagerHanMucAdapter(requireContext(),listContent){ item,amount ->
            requestAddressPayNetID(item,amount)
        }
        binding.recycleView.apply {
            adapter = this@ManagerHanMucDialog.adapter
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ManagerHanMucDialogBinding.inflate(inflater,container,false)

    private fun requestAddressPayNetID(item:PaynetGetBalanceByIdResponse,amount:String): Disposable {
        val request = BaseRequest()
        request.id = item.payNetID
        showProgressDialog()
        return NetWorkController.requestAddressPayNetID(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    val response = NetWorkController.getGson().fromJson<AddressPayNetIDResponse>(result.data,object : TypeToken<AddressPayNetIDResponse>(){}.type)
                    NapHanMucDialog.getInstance(amount, item.code,codeMerchant,amountOutWard,amountOutWardGTGT,amountBonus,item,response.provinceCode).show(childFragmentManager, "ManagerHanMucDialog")
                }else if (result.errorCode =="01"){
                    NapHanMucDialog.getInstance(amount, item.code,codeMerchant,amountOutWard,amountOutWardGTGT,amountBonus,item,null).show(childFragmentManager, "ManagerHanMucDialog")
                }else{
                    toast(result.message,requireContext())
                }
            }) { throwable ->
                throwable.printStackTrace()
                hideProgressDialog()
            }
    }

    override fun display(data: Any) {
        if (data is StateNotifyData){
            data.also {  stateNotify ->
                when(stateNotify.state){
                    StateNotify.I_NEED_UPDATE ->{
                        sharedPref.employeeModel.paynetID?.let {
                            requestPaynetGetBalanceByID(it)
                        }
                    }
                    else->{}
                }
            }
        }

    }

    override fun update(data: Any) {
        display(data)
    }

    override fun onStart() {
        super.onStart()
        StateNotifyData.registerObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        StateNotifyData.removeObserver(this)
    }
    private fun requestPaynetGetBalanceByID(requestId:Int):Disposable{
        val baseRequest = BaseRequest()
        baseRequest.id = requestId
        return NetWorkController.paynetGetBalanceByID(baseRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                hideProgressDialog()
                if (result.errorCode == "00"){
                    val responses = NetWorkController.getGson().fromJson<List<PaynetGetBalanceByIdResponse>>(
                                result.data, object : TypeToken<List<PaynetGetBalanceByIdResponse?>?>() {}.type)
                    adapter.updateData(responses)
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
                hideProgressDialog()
            }
    }
    companion object {
        @JvmStatic
        fun getInstance(listContent:ArrayList<PaynetGetBalanceByIdResponse>,
                       amountOutWard:Long, amountOutWardGTGT:Long,amountBonus:Long) = ManagerHanMucDialog().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ExtraConst.EXTRA_LIST_MANAGER_HAN_MUC,listContent)
                putLong(ExtraConst.EXTRA_AMOUNT_OUTWARD,amountOutWard)
                putLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_GTGT,amountOutWardGTGT)
                putLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_BONUS,amountBonus)

            }
        }
    }

}