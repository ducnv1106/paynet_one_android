package com.paynetone.counter.functions.media

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.core.base.viper.ViewFragment
import com.core.base.viper.interfaces.IPresenter
import com.paynetone.counter.R
import com.paynetone.counter.base.PaynetOneActivity
import com.paynetone.counter.utils.ExtraConst

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_camera)

    }


}