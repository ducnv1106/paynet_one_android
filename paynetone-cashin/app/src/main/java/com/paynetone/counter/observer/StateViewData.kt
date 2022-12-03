package com.paynetone.counter.observer

import com.paynetone.counter.enumClass.StateNotify
import com.paynetone.counter.enumClass.StateView

object StateViewData : Subject<Any> {

    var state: StateView = StateView.NOTHING

    private val observers: ArrayList<Observer<Any>> = arrayListOf()

    @JvmStatic
    fun setMeasurements(state: StateView) {
        this.state = state
        onMeasurementsChanged()
    }

    private fun onMeasurementsChanged() {
        notifyObservers()
    }

    override fun registerObserver(observer: Observer<Any>) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer<Any>) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        observers.forEach {
            it.update(this)
        }
    }
}