package com.paynetone.counter.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.paynetone.counter.utils.SharedPref


class MyService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        // clear data SharedPref

        // clear data SharedPref
        SharedPref.getInstance(this).clear()
        //Code here
        stopSelf()
    }
}