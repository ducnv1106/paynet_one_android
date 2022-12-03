package com.paynetone.counter.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.paynetone.counter.R

class BindingAdapter {
    companion object{
        @BindingAdapter("android:setShowOrHideDrawable")
        @JvmStatic
        fun ImageView.setShowOrHideDrawable(isShowOrHide : Boolean){
            if (isShowOrHide) this.setImageDrawable( ContextCompat.getDrawable(context, R.drawable.ic_tick))
            else  this.setImageDrawable(null)
        }
    }
}