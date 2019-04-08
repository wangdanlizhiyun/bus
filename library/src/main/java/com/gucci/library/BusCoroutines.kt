package com.gucci.library

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.os.Handler
import android.os.Looper

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

fun <T> LifecycleOwner.listener(key: Int, action: (T) -> Unit) {
    LiveBus.getInstance().listener(lifecycle, object : LiveBus.EventObserver<T>(key) {
        override fun onChange(o: T) {
            action(o)
        }
    })
}

fun <T>  registerStick(key: Int, action: (T) -> Unit) {
    LiveBus.getInstance().registerStick(object : LiveBus.EventObserver<T>(key) {
        override fun onChange(o: T) {
            action(o)
        }
    })

}