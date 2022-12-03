package com.paynetone.counter.observer

interface DisplayElement<T> {
    fun display(data: T)
}