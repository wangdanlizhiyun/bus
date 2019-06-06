package com.gucci.library;

import android.util.Log;
import com.gucci.lifecycle.LifecycleListener;
import com.gucci.lifecycle.annotations.*;

import java.util.Observable;

/**
 * Created by 李志云 2019/6/7 04:25
 */
public abstract class EventObserver<T> implements java.util.Observer, LifecycleListener {
    int key;
    LifecycleListener lifecycleListener;
    boolean isOnStarted = false;
    T value = null;

    public EventObserver(int key) {
        this.key = key;
    }

    public class BusLifecycleListener implements LifecycleListener {
        @OnStop
        public void onStop() {
            isOnStarted = false;
        }

        @OnStart
        public void onStart() {
            isOnStarted = true;
            if (value != null) {
                onChange(value);
            }
        }

        @OnDestory
        public void onDestory() {
            LiveBus.getInstance().unregister(EventObserver.this);
        }

        @OnDetachedToWindow
        public void onDetachedToWindow() {
            LiveBus.getInstance().unregister(EventObserver.this);
        }
        @OnAttachedToWindow
        public void onAttachedToWindow() {
            LiveBus.getInstance().register(EventObserver.this);
        }

    }

    public LifecycleListener generateLifecycleListener() {
        if (lifecycleListener == null) {
            lifecycleListener = new BusLifecycleListener();
        }
        return lifecycleListener;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof LiveBus.EventBean) {
            try {
                LiveBus.EventBean<T> bean = (LiveBus.EventBean<T>) arg;
                if (bean.key == key) {
                    if (lifecycleListener == null || isOnStarted) {
                        value = null;
                        onChange(bean.value);
                    } else {
                        value = bean.value;
                        return;
                    }
                }
            } catch (Exception e) {
                Log.e("test", "Exception " + e.getMessage());
            }
        }
    }

    public abstract void onChange(T o);
}
