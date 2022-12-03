package com.paynetone.counter.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.adapter.BranchAdapter
import com.paynetone.counter.databinding.DevelopDialogBinding
import com.paynetone.counter.databinding.DialogFilterHistoryPaymentBinding
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.PaynetGetByParentRequest
import com.paynetone.counter.model.response.PaynetGetByParentResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.setSingleClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FilterHistoryPaymentDialog(val mContext: Context,val listener:CallBackListener) : BaseDialogFragment<DialogFilterHistoryPaymentBinding>() {

    private var adapterBranch : BranchAdapter? = null
    private var adapterStore : BranchAdapter? = null
    private var adapterStall:BranchAdapter? = null

    private var itemBranch : PaynetGetByParentResponse? = null
    private var itemStore : PaynetGetByParentResponse? = null
    private var itemStall : PaynetGetByParentResponse? = null
    private val sharedPref = SharedPref.getInstance(mContext)

    override fun initView() {

        binding.apply {
            hideLayoutWithAccount()
            edtBranch.setSingleClick {
                if (recycleBranch.visibility == View.GONE){
                    requestBranch()
                    hideAllRecyclerView()
                }else{
                    recycleBranch.visibility = View.GONE
                }

            }
            edtStore.setSingleClick {
                if (itemBranch==null && layoutBranch.visibility == View.VISIBLE){
                    Toast.showToast(requireContext(),"Vui lòng chọn chi nhánh!")
                    return@setSingleClick
                }
                if (recycleStore.visibility == View.GONE){
                    itemBranch?.let {
                        requestStore(it.id)
                        hideAllRecyclerView()
                    }
                }else{
                    recycleStore.visibility = View.GONE
                }
                if (layoutBranch.visibility == View.GONE){
                    sharedPref?.paynet?.id?.let {
                        requestStore(it)
                    }
                }
            }
            edtStall.setSingleClick {
                if (itemStore==null && layoutStore.visibility ==View.VISIBLE){
                    Toast.showToast(requireContext(),"Vui lòng chọn cửa hàng!")
                    return@setSingleClick
                }
                if (recycleStore.visibility == View.GONE){
                    itemStore?.let {
                        requestStall(it.id)
                        hideAllRecyclerView()
                    }
                }else{
                    recycleStore.visibility = View.GONE
                }

                if (layoutStore.visibility == View.GONE){
                    sharedPref?.paynet?.id?.let {
                        requestStall(it)
                    }
                }
            }
            rootView.setSingleClick {
                hideAllRecyclerView()
            }
            btnClose.setSingleClick {
                dismiss()
            }
            btnOk.setSingleClick {
                listener.onConfirmClicked(
                    branchID = itemBranch?.id,
                    storeID = itemStore?.id,
                    stallID = itemStall?.id
                )
                dismiss()
            }

        }
    }

    override fun initCancelable(): Boolean = false

    override fun initStyle(): Int = R.style.DialogStyle

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        DialogFilterHistoryPaymentBinding.inflate(inflater, container, false)

    private fun requestBranch():Disposable{
        val paynetGetByParentRequest = PaynetGetByParentRequest(sharedPref.paynet.id)
        return NetWorkController.paynetGetByParentRequest(paynetGetByParentRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.errorCode == "00"){
                    val response = NetWorkController.getGson().fromJson<ArrayList<PaynetGetByParentResponse>>(result.data,
                        object : TypeToken<ArrayList<PaynetGetByParentResponse>>(){}.type)
                        initAdapterBranch(response)
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
            }
    }

    private fun requestStore(storeID:Int):Disposable{
        val paynetGetByParentRequest = PaynetGetByParentRequest(storeID)
        return NetWorkController.paynetGetByParentRequest(paynetGetByParentRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.errorCode == "00"){
                    val response = NetWorkController.getGson().fromJson<ArrayList<PaynetGetByParentResponse>>(result.data,
                        object : TypeToken<ArrayList<PaynetGetByParentResponse>>(){}.type)
                    initAdapterStore(response)
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
            }
    }
    private fun requestStall(stallID:Int):Disposable{
        val paynetGetByParentRequest = PaynetGetByParentRequest(stallID)
        return NetWorkController.paynetGetByParentRequest(paynetGetByParentRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                if (result.errorCode == "00"){
                    val response = NetWorkController.getGson().fromJson<ArrayList<PaynetGetByParentResponse>>(result.data,
                        object : TypeToken<ArrayList<PaynetGetByParentResponse>>(){}.type)
                    initAdapterStall(response)
                }else{
                    Toast.showToast(requireContext(),result.message)
                }
            }) { throwable ->
                throwable.printStackTrace()
            }
    }
    private fun initAdapterBranch(listContent:ArrayList<PaynetGetByParentResponse>){
        binding.apply {
            recycleBranch.visibility = View.VISIBLE
            if (adapterBranch==null){
                adapterBranch = BranchAdapter(requireContext(),listContent,object :BranchAdapter.OnClickItemListener{
                    override fun onClickItem(item: PaynetGetByParentResponse) {
                        edtBranch.text = item.name
                        binding.recycleBranch.visibility = View.GONE
                        this@FilterHistoryPaymentDialog.itemBranch = item
                        edtStore.text = ""
                        edtStall.text = ""
                        this@FilterHistoryPaymentDialog.itemStore = null
                        this@FilterHistoryPaymentDialog.itemStall = null
                    }
                })
                recycleBranch.adapter = adapterBranch
            }
            else {
                adapterBranch?.updateData(listContent)
            }
        }

    }

    private fun initAdapterStore(listContent:ArrayList<PaynetGetByParentResponse>){
        binding.apply {
            recycleStore.visibility = View.VISIBLE
            if (adapterStore==null){
                adapterStore = BranchAdapter(requireContext(),listContent,object :BranchAdapter.OnClickItemListener{
                    override fun onClickItem(item: PaynetGetByParentResponse) {
                        edtStore.text = item.name
                        binding.recycleStore.visibility = View.GONE
                        edtStall.text = ""
                        this@FilterHistoryPaymentDialog.itemStore = item
                        this@FilterHistoryPaymentDialog.itemStall = null
                    }
                })
                recycleStore.adapter = adapterStore
            }
            else {
                adapterStore?.updateData(listContent)
            }
        }

    }

    private fun initAdapterStall(listContent:ArrayList<PaynetGetByParentResponse>){
        binding.apply {
            recycleStall.visibility = View.VISIBLE
            if (adapterStall==null){
                adapterStall = BranchAdapter(requireContext(),listContent,object :BranchAdapter.OnClickItemListener{
                    override fun onClickItem(item: PaynetGetByParentResponse) {
                        edtStall.text = item.name
                        binding.recycleStall.visibility = View.GONE
                        this@FilterHistoryPaymentDialog.itemStall = item
                    }
                })
                recycleStall.adapter = adapterStall
            }
            else {
                adapterStall?.updateData(listContent)
            }
        }

    }
    private fun hideAllRecyclerView(){
        binding.apply {
            recycleBranch.visibility = View.GONE
            recycleStore.visibility = View.GONE
            recycleStall.visibility = View.GONE
        }
    }
    private fun hideLayoutWithAccount(){
        binding.apply {
            if (sharedPref.isAccountBranch) layoutBranch.visibility = View.GONE
            if (sharedPref.isAccountStore) {
                layoutBranch.visibility = View.GONE
                layoutStore.visibility = View.GONE
            }
        }
    }
    interface CallBackListener{
        fun onConfirmClicked(branchID :Int?, storeID:Int?, stallID:Int?)
    }
}