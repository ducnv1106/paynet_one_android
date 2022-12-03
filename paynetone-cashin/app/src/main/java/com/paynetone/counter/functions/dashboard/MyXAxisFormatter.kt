package com.paynetone.counter.functions.dashboard

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class MyXAxisFormatter(val title : ArrayList<String>) : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return title.getOrNull(value.toInt()) ?: value.toString()
    }

}