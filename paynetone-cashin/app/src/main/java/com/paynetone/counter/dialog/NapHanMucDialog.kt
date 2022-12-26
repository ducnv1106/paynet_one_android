package com.paynetone.counter.dialog

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.gson.reflect.TypeToken
import com.paynetone.counter.R
import com.paynetone.counter.databinding.NapHanMucDialogBinding
import com.paynetone.counter.enumClass.StateView
import com.paynetone.counter.functions.han_muc.ListBankActivity
import com.paynetone.counter.functions.withdraw.WithDrawActivity
import com.paynetone.counter.model.PaynetGetBalanceByIdResponse
import com.paynetone.counter.enumClass.SelectWithDraw
import com.paynetone.counter.model.request.BaseRequest
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.response.AddPINCodeResponse
import com.paynetone.counter.network.NetWorkController
import com.paynetone.counter.observer.DisplayElement
import com.paynetone.counter.observer.Observer
import com.paynetone.counter.observer.StateViewData
import com.paynetone.counter.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NapHanMucDialog : BaseDialogFragment<NapHanMucDialogBinding>(), DisplayElement<Any>, Observer<Any> {

    private lateinit var content: String
    private var code:String? = null
    private var codeMerchant:String? = null
    private var amountOutWard:Long = 0L
    private var amountOutWardGTGT:Long = 0L
    private var amountBonus : Long = 0L
    private var payNetGetBalance : PaynetGetBalanceByIdResponse ?= null
    private var provinceCode:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            content = getString(ExtraConst.EXTRA_CONTENT,"")
            getString(ExtraConst.EXTRA_CODE)?.let {
                code = it
            }
            getString(ExtraConst.EXTRA_CODE_MERCHANT)?.let {
                codeMerchant = it
            }
            amountOutWard = getLong(ExtraConst.EXTRA_AMOUNT_OUTWARD,0L)
            amountOutWardGTGT = getLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_GTGT,0L)
            amountBonus = getLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_BONUS,0L)
            getParcelable<PaynetGetBalanceByIdResponse>(ExtraConst.EXTRA_PAYNET)?.let {
                payNetGetBalance = it
            }
            getString(ExtraConst.EXTRA_PROVINCE_CODE)?.let {
                provinceCode = it
            }
        }
    }

    override fun initView() {
        binding.apply {
            tvHanMuc.text = content
            if (SharedPref.getInstance(requireContext()).isMerchantAdmin){
                layoutHanMucDoiSoat.visibility = View.VISIBLE
            }
            ivBack.setSingleClick {
                dismiss()
            }
            tvGuide.setSingleClick {
                GuideRechargeDialog.getInstance(code,codeMerchant,provinceCode).show(childFragmentManager,"GuideRechargeDialog")
            }
            layoutBank.setSingleClick {
                val intent = Intent(requireContext(), ListBankActivity::class.java)
                intent.putExtra(ExtraConst.EXTRA_CODE , code)
                intent.putExtra(ExtraConst.EXTRA_CODE_MERCHANT , codeMerchant)
                intent.putExtra(ExtraConst.EXTRA_PROVINCE_CODE,provinceCode)
                startActivity(intent)
            }
            layoutWallet.setSingleClick {
                DevelopDialog().show(childFragmentManager,"NapHanMucDialog")
            }
            layoutHanMucDoiSoat.setSingleClick {
                val intent = Intent(requireActivity(), WithDrawActivity::class.java)
                intent.apply {
                    putExtra(Constants.AMOUNT_OUTWARD, amountOutWard )
                    putExtra(Constants.AMOUNT_OUTWARD_GTGT,amountOutWardGTGT)
                    putExtra(Constants.AMOUNT_OUT_WARD_BONUS,amountBonus)
                    putExtra(ExtraConst.EXTRA_WITH_DRAW, SelectWithDraw.HAN_MUC)
                    payNetGetBalance?.let {
                        putExtra(ExtraConst.EXTRA_PAYNET_GET_BALANCE_BY_ID,it)
                    }
                }
                startActivity(intent)
            }
            tvPhone.setSingleClick {
                if (allPermissionsGranted()){
                    onPermissionGranted()
                }else{
                    permissionRequest.launch(permissions.toTypedArray())
                }
            }
        }

    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = NapHanMucDialogBinding.inflate(inflater,container,false)

    // The permissions we need for the app to work properly
    private val permissions = mutableListOf(Manifest.permission.CALL_PHONE)

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                onPermissionGranted()
            } else {
                Toast.showToast(activity,resources.getString(R.string.str_no_call_phone_assess))
            }
        }

    // handler after the permission check
    private fun onPermissionGranted() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+84931739889"))
        startActivity(intent)
    }

    override fun display(data: Any) {
        if (data is StateViewData){
            data.also {  stateViewData ->
                when(stateViewData.state){
                    StateView.GONE ->{
                       dismissAllowingStateLoss()
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
        StateViewData.registerObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        StateViewData.removeObserver(this)
    }

    companion object {
        @JvmStatic
        fun getInstance( content:String, code:String?, codeMerchant:String?,
                         amountOutWard:Long,
                         amountOutWardGTGT:Long,
                         amountBonus:Long,
                         payNetGetBalance:PaynetGetBalanceByIdResponse?=null,
                         provinceCode:String?= null) = NapHanMucDialog().apply {
            arguments = Bundle().apply {
                putString(ExtraConst.EXTRA_CONTENT,content)
                code?.let {
                    putString(ExtraConst.EXTRA_CODE,it)
                }
                codeMerchant?.let {
                    putString(ExtraConst.EXTRA_CODE_MERCHANT,it)
                }
                provinceCode?.let {
                    putString(ExtraConst.EXTRA_PROVINCE_CODE,it)
                }
                putLong(ExtraConst.EXTRA_AMOUNT_OUTWARD,amountOutWard)
                putLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_GTGT,amountOutWardGTGT)
                putLong(ExtraConst.EXTRA_AMOUNT_OUTWARD_BONUS,amountBonus)
                putParcelable(ExtraConst.EXTRA_PAYNET,payNetGetBalance)

            }
        }
    }

}