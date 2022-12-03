package com.paynetone.counter.app

import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.paynetone.counter.main.SplashScreenActivity
import com.paynetone.counter.utils.Constants
import com.paynetone.counter.utils.PrefConst
import com.paynetone.counter.utils.SharedPref
import com.paynetone.counter.utils.ViewSize
import kotlinx.coroutines.*


class PaynetOneApplication : MultiDexApplication(), LifecycleObserver {

    var applicationScope :Job?=null
    var mTimeRunning :Boolean = false
    private val timeBackground = 180 // time second
    private fun isLogin() = SharedPref.getInstance(this).getBoolean(PrefConst.PREF_IS_LOGIN,false)

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    companion object {
        lateinit var instance: PaynetOneApplication
        @JvmName("getInstance1")
        fun getInstance(): PaynetOneApplication = synchronized(this) {
            instance
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        // App goes to foreground
        if (applicationScope?.isActive==true){
            applicationScope?.cancel()
        }
        if (mTimeRunning){
            SharedPref(this).clear()
            mTimeRunning=false

            val intent = Intent(this, SplashScreenActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
     fun onBackground() {
        // App goes to background
        if (isLogin()){ // when login app
            applicationScope =  CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                for (x in 0..timeBackground step 1) {
                    Log.e("TAG", "onBackground: $x" )
                    delay(1000)
                    if (x==timeBackground) mTimeRunning = true
                }
            }
        }


    }

    override fun onLowMemory() {
        super.onLowMemory()
        try {
            applicationScope?.cancelChildren()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    //Start Resize
    private var scaleValue = 0F
    private fun getScaleValue(): Float {
        if (scaleValue == 0F) {
            scaleValue = resources.displayMetrics.widthPixels * 1f / Constants.SCREEN_WIDTH_DESIGN
        }
        return scaleValue
    }

    fun getSizeWithScale(sizeDesign: Double): Int {
        return (sizeDesign * getScaleValue()).toInt()
    }

    fun getSizeWithScaleFloat(sizeDesign: Double): Float {
        return (sizeDesign * getScaleValue()).toFloat()
    }

    fun getRealSize(sizeDesign: ViewSize): ViewSize {
        return ViewSize(sizeDesign.width * getScaleValue(), sizeDesign.height * getScaleValue())
    }
    //End Resize
}
