package com.paynetone.counter.functions.dashboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.core.base.viper.ViewFragment
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.material.tabs.TabLayoutMediator
import com.paynetone.counter.R
import com.paynetone.counter.adapter.ViewPager2Adapter
import com.paynetone.counter.app.PaynetOneApplication
import com.paynetone.counter.databinding.DashboardFragmentBinding
import com.paynetone.counter.dialog.ErrorDialog
import com.paynetone.counter.functions.dashboard.statistic_day.StatisticDayFragment
import com.paynetone.counter.functions.dashboard.statistic_month.StatisticMonthFragment
import com.paynetone.counter.model.EmployeeModel
import com.paynetone.counter.model.PaynetModel
import com.paynetone.counter.model.request.DashboardRequest
import com.paynetone.counter.model.response.DashboardData
import com.paynetone.counter.model.response.Statistical
import com.paynetone.counter.utils.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

class DashboardFragment : ViewFragment<DashboardContract.Presenter>(), DashboardContract.View  {
    private lateinit var binding: DashboardFragmentBinding
    private var paynetModel: PaynetModel? = null
    private var employeeModel: EmployeeModel?=null
    private val sharedPref by lazy { SharedPref(requireActivity()) }
    private lateinit var fragmentStatisticDay:StatisticDayFragment
    private lateinit var fragmentStatisticMonth:StatisticMonthFragment
    private var indicatorWidth:Int?=null

    companion object {
        val instance: DashboardFragment
            get() = DashboardFragment()
    }

    override fun getLayoutId(): Int = R.layout.dashboard_fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, container, false)
        binding.lifecycleOwner = this
        initView()
        loadControlsAndResize()
        return binding.root
    }

    private fun initView() {
        paynetModel = sharedPref.paynet
        employeeModel = sharedPref.employeeModel
        requestDashboardDay()
        requestDashboardMonth()
        initViewPagerAdapter()
        onListenerViewPager()

    }
    private fun loadControlsAndResize(){
        binding.apply {
            with(PaynetOneApplication.getInstance()){
                tab.resizeLayout(getSizeWithScale(302.0), getSizeWithScale(38.0))
                indicator2.resizeLayout(getSizeWithScale(302.0), getSizeWithScale(34.0))
            }

        }

    }

    private fun initViewPagerAdapter(){
        fragmentStatisticDay = StatisticDayFragment()
        fragmentStatisticMonth = StatisticMonthFragment()
        val dashboardViewPager = ViewPager2Adapter( this)
        dashboardViewPager.addFragment(fragmentStatisticDay,resources.getString(R.string.str_title_statistic_day))
        dashboardViewPager.addFragment(fragmentStatisticMonth,resources.getString(R.string.str_title_statistic_month))

        binding.apply {
            viewPager.adapter = dashboardViewPager
            viewPager.offscreenPageLimit = 2
            viewPager.isUserInputEnabled = false

            TabLayoutMediator(tab, viewPager) { tab, position ->
                tab.text = dashboardViewPager.getPageTitle(position)
                viewPager.setCurrentItem(tab.position, true)
            }.attach()

            //Determine indicator width at runtime
            tab.post {
                indicatorWidth = tab.let { tab.width.div(it.tabCount) }

                //Assign new width
                val indicatorParams = indicator.layoutParams
                indicatorParams?.let {
                    if (indicatorWidth != null) {
                        (indicatorParams as FrameLayout.LayoutParams).width = indicatorWidth as Int
                        indicatorParams.height = PaynetOneApplication.getInstance().getSizeWithScale(34.0)
                    }
                    indicator.layoutParams = indicatorParams
                }

            }
        }
    }

    private fun onListenerViewPager(){
        try {
            binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    val params = binding.indicator.layoutParams as FrameLayout.LayoutParams
                    //Multiply positionOffset with indicatorWidth to get translation
                    val translationOffset: Float = (positionOffset + position) * (indicatorWidth?:0) + binding.tab.x
                    params.leftMargin = translationOffset.toInt()
                    binding.indicator.layoutParams = params
                }

            } )
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun requestDashboardDay(){
        if (mPresenter!=null)  mPresenter.requestDashboardDay(DashboardRequest(merchantID = paynetModel?.merchantID ?: 0,
            empID = employeeModel?.id ?: 0,
            dateMode = Constants.DASHBOARD_DAY))

    }
    fun requestDashboardMonth(){
        if (mPresenter!=null) mPresenter.requestDashboardMonth(DashboardRequest(merchantID = paynetModel?.merchantID ?: 0,
            empID = employeeModel?.id ?: 0,
            dateMode = Constants.DASHBOARD_MONTH))

    }

    override fun requestError(message: String) {
        Toast.showToast(requireContext(),message)

    }

    override fun showDashboardDataDay(response: ArrayList<DashboardData>?) {
        fragmentStatisticDay.showDashboardData(response)
    }

    override fun showStatisticalDay(response: ArrayList<Statistical>?) {
        fragmentStatisticDay.showStatistical(response)

    }

    override fun showDashboardDataMonth(response: ArrayList<DashboardData>?) {
       fragmentStatisticMonth.showDashboardData(response)
    }

    override fun showStatisticalMonth(response: ArrayList<Statistical>?) {
        fragmentStatisticMonth.showStatistical(response)
    }


}