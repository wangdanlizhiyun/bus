package com.gucci.library

import android.arch.lifecycle.Lifecycle
import android.support.v4.app.FragmentActivity
import com.gucci.lifecycle.LifecycleListener

/**
 * Created by 李志云 2019/3/6 01:30
 * 定义该接口是为了支持跨进程时用来动态代理的
 */
interface ILiveBus {
    fun sendEvent(key: Int, value: Any)
    fun sendStickEvent(key: Int, value: Any)
    fun registerStick(eventObserver: EventObserver<*>)


    fun listener(eventObserver: EventObserver<*>):LifecycleListener
    fun register(eventObserver: EventObserver<*>)
    fun unregister(eventObserver: EventObserver<*>)
}
