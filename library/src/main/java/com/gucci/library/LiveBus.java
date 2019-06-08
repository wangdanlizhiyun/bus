package com.gucci.library;

import android.util.SparseArray;
import com.gucci.lifecycle.LifecycleListener;

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
    public LifecycleListener listener(EventObserver eventObserver) {
        mEvent.addObserver(eventObserver);
        return eventObserver.generateLifecycleListener();
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


}
