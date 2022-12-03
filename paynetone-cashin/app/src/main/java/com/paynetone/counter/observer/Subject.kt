package com.paynetone.counter.observer

interface Subject<T> {

    fun registerObserver(observer: Observer<T>)

    fun removeObserver(observer: Observer<T>)

    fun notifyObservers()

}