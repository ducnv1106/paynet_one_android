package com.paynetone.counter.functions.dashboard.statistic_day

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ViewPortHandler
import com.paynetone.counter.R
import com.paynetone.counter.databinding.StatisticDayFragmentBinding
import com.paynetone.counter.functions.dashboard.DashboardFragment
import com.paynetone.counter.functions.dashboard.MyValueFormatter
import com.paynetone.counter.functions.dashboard.MyXAxisFormatter
import com.paynetone.counter.model.response.DashboardData
import com.paynetone.counter.model.response.Statistical
import com.paynetone.counter.utils.NumberUtils
import com.paynetone.counter.utils.autoCleared
import java.util.ArrayList

class StatisticDayFragment :  Fragment() {

    private var binding: StatisticDayFragmentBinding by autoCleared()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = StatisticDayFragmentBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }

    private fun initView(){
        binding.apply {
            initChart(chartPayment)
            initChart(chartService)
        }
        onRefreshListener()
    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            if (requireParentFragment() is DashboardFragment){
                (requireParentFragment() as DashboardFragment).apply {
                    this.requestDashboardDay()
                }
            }
            binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)
            binding.refreshLayout.isRefreshing=false
//            Handler(Looper.getMainLooper()).postDelayed({
//                binding.refreshLayout.isRefreshing=false
//
//            }, 2000L)

        }
    }

    private fun initChart(chart: CombinedChart){

        // draw bars behind lines
        chart.drawOrder = arrayOf(
            CombinedChart.DrawOrder.BAR,
            CombinedChart.DrawOrder.BUBBLE,
            CombinedChart.DrawOrder.CANDLE,
            CombinedChart.DrawOrder.LINE,
            CombinedChart.DrawOrder.SCATTER
        )

        chart.legend.apply {
            isWordWrapEnabled = true
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            orientation = Legend.LegendOrientation.HORIZONTAL
            isEnabled = false
            setDrawInside(false)
        }

        val data = CombinedData()
        data.setData(generateLineData(50f,100f,150f))
        data.setData(generateBarData(1500f,2500f,3500f))

        chart.axisLeft.apply {
            setDrawGridLines(false)
            axisMaximum = maxSumAmount
            axisMinimum = minSumAmount
            isGranularityEnabled = true
            setLabelCount(9, false)
            textSize = 8f
        }

        chart.axisRight.apply {
            setDrawGridLines(false)
            axisMaximum = maxQuantity
            axisMinimum = minQuantity
            setLabelCount(9, false)
            textSize = 8f
        }

        chart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
//            valueFormatter = MyXAxisFormatter()
            setLabelCount(3,false)
            setDrawGridLines(false)
            setCenterAxisLabels(true)
            setDrawAxisLine(true)
            axisMaximum = data.xMax + 0.55f
            textSize = 8f

        }

        chart.apply {
            description.isEnabled = false
            setBackgroundColor(Color.WHITE)
            setDrawGridBackground(false)
            setDrawBarShadow(false)
            isHighlightFullBarEnabled = false

            setTouchEnabled(false)
            isDragEnabled = false
            setScaleEnabled(false)
            isScaleXEnabled = false
            isScaleYEnabled = false
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            setData(data)
            invalidate()
        }

    }

    private fun generateLineData(data1:Float,data2:Float,data3:Float): LineData {
        val d = LineData()
        val entries = ArrayList<Entry>()
        if (data1>0){
            entries.add(Entry( 0.5f, data1))
        }
        if (data2>0){
            entries.add(Entry( 1.35f, data2))
        }
        if (data3>0){
            entries.add(Entry( 2.3f, data3))
        }

        val set = LineDataSet(entries, "Line DataSet")
        set.color = resources.getColor(R.color.close)
        set.lineWidth = 0.5f
        set.setCircleColor(resources.getColor(R.color.close))
        set.circleRadius = 4f
        set.fillColor = resources.getColor(R.color.white)
        set.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        set.setDrawValues(false)
        set.axisDependency = YAxis.AxisDependency.RIGHT
        d.addDataSet(set)
        return d
    }

    private fun generateBarData(data1:Float,data2:Float,data3:Float): BarData {
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()
        val entries3 = ArrayList<BarEntry>()

        entries1.add(BarEntry(0f,data1))
        entries2.add(BarEntry(0f,data2))
        entries3.add(BarEntry(0f,data3))

        val set1 = BarDataSet(entries1, "DataSet 1")
        val set2 = BarDataSet(entries2, "DataSet 2")
        val set3 = BarDataSet(entries3, "DataSet 3")
        val dataSets = ArrayList<IBarDataSet>()
        set1.color = resources.getColor(R.color.colorPrimary)
        set2.color = resources.getColor(R.color.colorPrimary)
        set3.color = resources.getColor(R.color.colorPrimary)
        dataSets.add(set1)
        dataSets.add(set2)
        dataSets.add(set3)
        val groupSpace = 0.06f
        val barSpace = 0.035f // x2 dataset
        val barWidth = 0.2f // x2 dataset
        val data = BarData(dataSets)
        data.setDrawValues(true)
        data.groupBars(0f, groupSpace, barSpace) // start at x = 0
        data.setValueTextSize(8f)
        data.barWidth = .5f
        return data
    }

    fun showDashboardData(response: ArrayList<DashboardData>?) {
        response?.let {
            if (response.size>0){
                binding.apply {
                    tvAmountTransactionPayment.text = response[0].countQROnline.toString()
                    tvSumAmountPayment.text = NumberUtils.formatPriceNumber(response[0].sumQROnline) + " VNĐ"
                    tvAmountTransactionService.text = response[0].countGTGT.toString()
                    tvSumAmountService.text = NumberUtils.formatPriceNumber(response[0].sumGTGT) + " VNĐ"
                }
            }
        }
    }

    fun showStatistical(response: ArrayList<Statistical>?) {
        response?.let {
            try {
                binding.apply {
                    val titleXAxis = arrayListOf(response[0].nameDate,response[1].nameDate,response[2].nameDate)
                    chartPayment.xAxis.apply {
                        valueFormatter = MyXAxisFormatter(titleXAxis)

                    }
                    chartService.xAxis.apply {
                        valueFormatter = MyXAxisFormatter(titleXAxis)
                    }

                    val dataPayment = CombinedData()
                    dataPayment.setData(generateLineData(response[0].countQROnline.toFloat(),
                        response[1].countQROnline.toFloat(),
                        response[2].countQROnline.toFloat()))

                    dataPayment.setData(generateBarData( if (response[0].sumQROnline.toFloat().div(unitVND)> maxSumAmount) maxSumAmount else response[0].sumQROnline.toFloat().div(unitVND) ,
                        if (response[1].sumQROnline.toFloat().div(unitVND)> maxSumAmount) maxSumAmount else response[1].sumQROnline.toFloat().div(unitVND) ,
                        if (response[2].sumQROnline.toFloat().div(unitVND)> maxSumAmount) maxSumAmount else response[2].sumQROnline.toFloat().div(unitVND) ))

                    chartPayment.data = dataPayment

                    chartPayment.barData.setValueFormatter(MyValueFormatter(arrayListOf(NumberUtils.formatPriceNumber(response[0].sumQROnline.toFloat().div(unitVND).toLong()),
                        NumberUtils.formatPriceNumber(response[1].sumQROnline.toFloat().div(unitVND).toLong()),
                        NumberUtils.formatPriceNumber(response[2].sumQROnline.toFloat().div(unitVND).toLong()))))
                    chartPayment.invalidate()


                    val dataService = CombinedData()
                    dataService.setData(generateLineData(response[0].countGTGT.toFloat(),
                        response[1].countGTGT.toFloat(),
                        response[2].countGTGT.toFloat()))
                    dataService.setData(generateBarData(if (response[0].sumGTGT.toFloat().div(unitVND)> maxSumAmount) maxSumAmount else response[0].sumGTGT.toFloat().div(unitVND),
                        if (response[1].sumGTGT.toFloat().div(unitVND)> maxSumAmount) maxSumAmount else response[1].sumGTGT.toFloat().div(unitVND),
                        if (response[2].sumGTGT.toFloat().div(unitVND)> maxSumAmount) maxSumAmount else response[2].sumGTGT.toFloat().div(unitVND)))

                    chartService.data = dataService
                    chartService.barData.setValueFormatter(MyValueFormatter(arrayListOf(NumberUtils.formatPriceNumber(response[0].sumGTGT.toFloat().div(unitVND).toLong()),
                        NumberUtils.formatPriceNumber(response[1].sumGTGT.toFloat().div(unitVND).toLong()),
                        NumberUtils.formatPriceNumber(response[2].sumGTGT.toFloat().div(unitVND).toLong()))))
                    chartService.invalidate()

                }


            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }
    companion object{
        const val maxSumAmount = 4500f
        const val minSumAmount = 0f

        const val maxQuantity = 450f
        const val minQuantity = 0f
        const val unitVND = 1000
    }

}