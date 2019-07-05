package com.gucci.library

import com.gucci.lifecycle.LifecycleListener


interface ILiveBus {
    fun sendEvent(key: Int, value: Any)
    fun sendStickEvent(key: Int, value: Any)
    fun registerStick(eventObserver: EventObserver<*>)


    fun listener(eventObserver: EventObserver<*>):LifecycleListener
    fun register(eventObserver: EventObserver<*>)
    fun unregister(eventObserver: EventObserver<*>)
}
