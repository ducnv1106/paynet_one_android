package com.paynetone.counter.functions.dashboard

import android.util.Log
import com.github.mikephil.charting.formatter.ValueFormatter



class MyValueFormatter(val title:ArrayList<String>) : ValueFormatter() {
    private var position = -1
    override fun getFormattedValue(value: Float): String {
        return try {
            if (position==count) position = -1
            position = position.plus(1)
            if (value>0){
                title[position]
            }else{
                ""
            }

       }catch (e:Exception){
           e.printStackTrace()
           ""
       }

    }
    companion object{
        const val count = 2
    }

}