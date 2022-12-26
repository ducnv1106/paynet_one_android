package com.paynetone.counter.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.paynetone.counter.R
import com.paynetone.counter.adapter.HistoryViewPager
import com.paynetone.counter.adapter.ViewPager2Adapter
import com.paynetone.counter.app.PaynetOneApplication
import com.paynetone.counter.databinding.DialogNewHistoryBinding
import com.paynetone.counter.functions.history.HistoryFragment
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.Toast
import com.paynetone.counter.utils.setSingleClick

class NewHistoryDialog : BaseDialogFragment<DialogNewHistoryBinding>() {
    private lateinit var historyPager : HistoryViewPager
    private val fragmentHistory = HistoryDialog()
    private  val fragmentHistoryQr = HistoryPaymentQRDialog()
    private val title by lazy { arrayListOf<String>("Thanh toán QR","Dịch vụ gia tăng") }
    private val listFragment by lazy { arrayListOf<Fragment>(fragmentHistoryQr,fragmentHistory) }
    private var indicatorWidth:Int?=null
    var currentPosition = 0

    override fun loadControlsAndResize(binding: DialogNewHistoryBinding) {
        binding.apply {
            val myApplication = PaynetOneApplication.getInstance()
            with(myApplication){
                tab.layoutParams.height =  getSizeWithScale(38.0)
                indicator2.layoutParams.height = getSizeWithScale(38.0)
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
                                when(viewPager.currentItem){
                                    0->{
                                        fragmentHistoryQr.requestTransSearch(branchID  , storeID , stallID)
                                    }
                                    1->{
                                        fragmentHistory.requestTransSearch(branchID, storeID, stallID)
                                    }
                                }
//                                adapter.clearAllContent()
//
                            }

                        }).show(childFragmentManager, "HistoryDialog")
                }

            }
            ivBack.setSingleClick {
                dismiss()
            }

        }
    }
    override fun initView() {
        initViewPager()

    }

    private fun  initViewPager(){
        binding.apply {

            historyPager = HistoryViewPager(this@NewHistoryDialog.childFragmentManager,listFragment, listTitle = title)
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    this@NewHistoryDialog.currentPosition = position
                    val params = binding.indicator.layoutParams as FrameLayout.LayoutParams
                    //Multiply positionOffset with indicatorWidth to get translation
                    val translationOffset: Float = (positionOffset + position) * (indicatorWidth?:0) + (binding.tab.x)
                    params.leftMargin = translationOffset.toInt()
                    binding.indicator.layoutParams = params
                }

                override fun onPageSelected(position: Int) {
                    this@NewHistoryDialog.currentPosition = position
                    when(position){
                        0->{
                            binding.indicator.setBackgroundResource(R.drawable.gradient_bg_history_left)
                        }
                        1->{
                            binding.indicator.setBackgroundResource(R.drawable.gradient_bg_history_right)
                        }
                    }

                }

                override fun onPageScrollStateChanged(state: Int) {

                }

            })
            viewPager.offscreenPageLimit = 3
            viewPager.adapter = historyPager
            tab.setupWithViewPager(viewPager)

            tab.post {
                indicatorWidth = binding.tab.let { binding.tab.width.div(it.tabCount) }

                //Assign new width
                val indicatorParams = binding.indicator.layoutParams
                indicatorParams?.let {
                    if (indicatorWidth != null) {
                        (indicatorParams as FrameLayout.LayoutParams).width = indicatorWidth as Int
                        indicatorParams.height = PaynetOneApplication.getInstance().getSizeWithScale(38.0)
                    }
                    binding.indicator.layoutParams = indicatorParams
                }

            }

        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogNewHistoryBinding  = DialogNewHistoryBinding.inflate(inflater,container,false)

    companion object {
        @JvmStatic
        fun getInstance() = NewHistoryDialog().apply {
            arguments = Bundle().apply {

            }
        }
    }
}