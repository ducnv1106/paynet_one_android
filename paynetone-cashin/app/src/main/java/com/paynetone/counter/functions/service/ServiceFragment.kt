package com.paynetone.counter.functions.service

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.core.base.viper.ViewFragment
import com.paynetone.counter.R
import com.paynetone.counter.databinding.ServiceFragmentBinding
import com.paynetone.counter.dialog.ConfirmPINCodeDialog
import com.paynetone.counter.dialog.DevelopDialog
import com.paynetone.counter.dialog.NotifyDialog
import com.paynetone.counter.functions.qr.OptionPaymentAdapter
import com.paynetone.counter.functions.service.billing.BillingFragment
import com.paynetone.counter.functions.service.topup.TopUpActivity
import com.paynetone.counter.main.MainFragment
import com.paynetone.counter.model.EmployeeModel
import com.paynetone.counter.model.PaynetModel
import com.paynetone.counter.model.request.GetProviderResponse
import com.paynetone.counter.model.request.PINAddRequest
import com.paynetone.counter.model.request.PartNerAddressRequest
import com.paynetone.counter.model.request.TopupAddressRequest
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_AMOUNT
import com.paynetone.counter.utils.ExtraConst.Companion.EXTRA_URL_TOPUP_ADDRESS
import com.paynetone.counter.utils.MarginDecoration
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.Toast

class ServiceFragment : ViewFragment<ServiceContract.Presenter>(), ServiceContract.View {

    private lateinit var binding: ServiceFragmentBinding
    private lateinit var adapter: OptionPaymentAdapter
    private var paynetModel: PaynetModel? = null
    private var employeeModel: EmployeeModel?=null
    private val sharedPref by lazy { SharedPref(requireActivity()) }
    private var providerResponse:GetProviderResponse? = null

    companion object {
        val instance: ServiceFragment
            get() = ServiceFragment()
    }

    override fun getLayoutId(): Int = R.layout.service_fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, container, false)
        binding.lifecycleOwner = this
        initView()
        return binding.root
    }

    private fun initView() {
        paynetModel = sharedPref.paynet
        employeeModel = sharedPref.employeeModel

    }

    fun initAdapter(providers: ArrayList<GetProviderResponse>) {
        try {
            adapter = OptionPaymentAdapter(
                requireContext(), object : OptionPaymentAdapter.OnClickItemListener {
                    override fun onClickItem(item: GetProviderResponse) {
                        this@ServiceFragment.providerResponse = item

                        val merchantStatus = paynetModel?.merchantStatus
                        merchantStatus?.let {
                            if (it == Constants.WAITING_APPROVAL){
                                DevelopDialog(resources.getString(R.string.str_message_waiting_approval)).show(
                                    childFragmentManager, "ServiceFragment"
                                )
                                return
                            }
                        }
                        if (item.isActive == Constants.PROVIDER_ACTIVE) {
                            if (sharedPref.isExistPINCode){
                                ConfirmPINCodeDialog().show(childFragmentManager, "ServiceFragment")
                            }else{
                                NotifyDialog.getInstance(getString(R.string.str_message_not_setting_pin_code))
                                    .show(childFragmentManager,"ServiceFragment")
                            }
                        } else {
                            DevelopDialog().show(
                                childFragmentManager,
                                "ServiceFragment"
                            )
                        }


                    }
                },
                OptionPaymentAdapter.ProviderEnum.SERVICE,
                providers
            )
            binding.recycleView.adapter = adapter
            binding.recycleView.addItemDecoration(MarginDecoration(10, 4))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun requestTopUpAddressSuccess(url: String) {
        showTopUpWebView(url)
    }

    override fun requestVerifyPINCodeSuccess() {
        paynetModel?.let {payNetModel->
            providerResponse?.let {
                when(it.id){
                    Constants.ID_MOBILE_RECHARE -> mPresenter.topUpAddress(TopupAddressRequest(payNetModel.code))
                    Constants.ID_VBOOKING -> mPresenter.requestVBookingAddress(TopupAddressRequest(payNetModel.code))
                    else ->{
                        val addressPayNet = sharedPref.addressPayNet
                        val request = PartNerAddressRequest(providerACNTCode = it.providerACNTCode ?: "", counterCode = payNetModel.code,
                                                            provinceCode = addressPayNet?.provinceCode ?: "", districtCode = addressPayNet?.districtCode ?: "",
                                                            wardCode = addressPayNet?.wardCode ?: "")
                        mPresenter.requestPartnerAddress(request)
                    }
                }
            }

        }
    }

    override fun requestError(message: String) {
        Toast.showToast(requireContext(),message)
    }

    override fun requestVBoonKingSuccess(url: String) {
        try {
            val mainFragment: MainFragment = this@ServiceFragment.parentFragment as MainFragment
            BillingFragment.getInstance(url,providerResponse?.name,mainFragment.amountHanMuc, isBooking = true,isShowErr = false).show(childFragmentManager,"ServiceFragment")
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun requestPartNerSuccess(url: String) {
        try {
            providerResponse?.let {
                when(it.id){
                    Constants.ID_TOPUP_DATA,Constants.ID_CARD_GAME -> {
                        showTopUpWebView(url)
                    }
                    else ->{
                        val mainFragment: MainFragment = this@ServiceFragment.parentFragment as MainFragment
                        BillingFragment.getInstance(url,providerResponse?.name,mainFragment.amountHanMuc).show(childFragmentManager,"ServiceFragment")
                    }
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun showTopUpWebView(url: String){
        val intent = Intent(requireActivity(), TopUpActivity::class.java)
        intent.putExtra(EXTRA_URL_TOPUP_ADDRESS, url)
        val mainFragment: MainFragment = this@ServiceFragment.parentFragment as MainFragment
        intent.putExtra(EXTRA_AMOUNT,mainFragment.amountHanMuc)
        startActivity(intent)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is ConfirmPINCodeDialog) {
            val dialog: ConfirmPINCodeDialog = childFragment as ConfirmPINCodeDialog
            dialog.setCallBackListener(object :ConfirmPINCodeDialog.CallBackListener{
                override fun itemClick(pinCode: String) {
                    val empId = SharedPref.getInstance(requireContext()).employeeModel.getiDMerAdmin()
                    val mobileNumber = SharedPref.getInstance(requireContext()).employeeModel.mobileNumber
                    if (mPresenter != null)
                        mPresenter.requestVerifyPinCode(PINAddRequest(empId ?: 0, pinCode, mobileNumber = mobileNumber))
                }
            })
        }
    }


}