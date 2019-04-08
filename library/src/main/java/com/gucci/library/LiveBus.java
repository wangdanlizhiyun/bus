package com.gucci.library;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.Observable;

/**
 * Created by 李志云 2019/3/6 01:20
 */
public class LiveBus implements ILiveBus {
    private static volatile LiveBus mInstance;
    EventObserverable mEvent;
    SparseArray<Object> mStickEvent = new SparseArray<>();

    private LiveBus() {
        mEvent = new EventObserverable();
    }

    public static LiveBus getInstance() {
        if (mInstance == null) {
            synchronized (LiveBus.class) {
                if (mInstance == null) {
                    mInstance = new LiveBus();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void sendEvent(int key, Object value) {
        mEvent.setValue(key, value);
    }

    @Override
    public void sendStickEvent(int key, Object value) {
        mStickEvent.put(key, value);

    }

    @Override
    public void registerStick(EventObserver eventObserver) {
        Object event = mStickEvent.get(eventObserver.key);
        if (event != null) {
            eventObserver.onChange(event);
        }
    }

    @Override
    public void listener(Lifecycle lifecycle, EventObserver eventObserver) {
        eventObserver.mWeakLifecycle = new WeakReference<>(lifecycle);
        lifecycle.addObserver(eventObserver);
        mEvent.addObserver(eventObserver);
    }

    @Override
    public void register(EventObserver eventObserver) {
        mEvent.addObserver(eventObserver);
    }

    @Override
    public void unregister(EventObserver eventObserver) {
        mEvent.deleteObserver(eventObserver);
    }

    public static class EventObserverable extends Observable {
        public void setValue(int key, Object value) {
            setChanged();
            notifyObservers(new EventBean(key, value));
        }
    }

    public static class EventBean<T> {
        int key;
        T value;

        public EventBean(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    public static abstract class EventObserver<T> implements java.util.Observer, LifecycleObserver {
        int key;
        WeakReference<Lifecycle> mWeakLifecycle;
        boolean isOnStarted = false;
        T value = null;

        public EventObserver(int key) {
            this.key = key;
        }

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof EventBean) {
                try {
                    EventBean<T> bean = (EventBean<T>) arg;
                    if (bean.key == key) {
                        if (mWeakLifecycle == null || isOnStarted) {
                            value = null;
                            onChange(bean.value);
                        } else {
                            value = bean.value;
                            return;
                        }
                    }
                } catch (Exception e) {
                    Log.e("test", "Exception "+e.getMessage() );
                }
            }
        }
        public abstract void onChange(T o);

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop() {
            isOnStarted = false;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onStart() {
            isOnStarted = true;
            if (value != null) {
                onChange(value);
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestroy() {
            if (mWeakLifecycle.get() != null) {
                mWeakLifecycle.get().removeObserver(this);
            }
        }
    }

}
