package com.gucci.library

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.gucci.library.LiveBus.EventObserver
import com.gucci.lifecycle.bind

/**
 * Created by 李志云 2019/1/2 17:05
 */
val sHandler = Handler(Looper.getMainLooper()){
    LiveBus.getInstance().sendEvent(it.arg1, it.obj)
    true
}
fun sendEvent(key: Int, value: Any) {
    if (Looper.getMainLooper() == Looper.myLooper()){
        LiveBus.getInstance().sendEvent(key, value)
    }else{
        val msg = sHandler.obtainMessage()
        msg.arg1 = key
        msg.obj = value
        sHandler.sendMessage(msg)
    }
}

fun sendStickEvent(key: Int, value: Any) {
    LiveBus.getInstance().sendStickEvent(key, value)
}
fun register(eventObserver: EventObserver<Any>) {
    LiveBus.getInstance().register(eventObserver)
}
fun unregister(eventObserver: EventObserver<Any>) {
    LiveBus.getInstance().unregister(eventObserver)
}

fun <T> FragmentActivity.listener(key: Int, action: (T) -> Unit) {
    LiveBus.getInstance().listener(object : EventObserver<T>(key) {
        override fun onChange(o: T) {
            action(o)
        }
    }) bind this
}
fun <T> Activity.listener(key: Int, action: (T) -> Unit) {
    LiveBus.getInstance().listener(object : EventObserver<T>(key) {
        override fun onChange(o: T) {
            action(o)
        }
    }) bind this
}
fun <T> Fragment.listener(key: Int, action: (T) -> Unit) {
    LiveBus.getInstance().listener(object : EventObserver<T>(key) {
        override fun onChange(o: T) {
            action(o)
        }
    }) bind this
}
fun <T> android.app.Fragment.listener(key: Int, action: (T) -> Unit) {
    LiveBus.getInstance().listener(object : EventObserver<T>(key) {
        override fun onChange(o: T) {
            action(o)
        }
    }) bind this
}

fun <T> registerStick(key: Int, action: (T) -> Unit) {
    LiveBus.getInstance().registerStick(object : EventObserver<T>(key) {
        override fun onChange(o: T) {
            action(o)
        }
    })

}