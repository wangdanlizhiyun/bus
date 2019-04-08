package com.gucci.library;

import android.arch.lifecycle.Lifecycle;

/**
 * Created by 李志云 2019/3/6 01:30
 */
public interface ILiveBus {
    void sendEvent(int key, Object value);
    void sendStickEvent(int key, Object value);
    void registerStick(LiveBus.EventObserver eventObserver);



    void listener(Lifecycle lifecycle, LiveBus.EventObserver eventObserver);
    void register(LiveBus.EventObserver eventObserver);
    void unregister(LiveBus.EventObserver eventObserver);
}
