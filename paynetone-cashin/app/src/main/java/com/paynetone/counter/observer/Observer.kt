package com.paynetone.counter.observer

interface Observer<T> {
    fun update(data: T)
}