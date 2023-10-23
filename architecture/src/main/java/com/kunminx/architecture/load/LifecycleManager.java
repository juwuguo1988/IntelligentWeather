package com.kunminx.architecture.load;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 生命周期管理类，若有根据activity、fragment生命周期来处理一些逻辑的类，可以继承此类
 * 注：需要在构造器中调用 super(lifecycleOwner);  lifecycleOwner:activity、fragment
 *
 * @data 4/28/21 @time 4:19 PM
 */
public abstract class LifecycleManager implements LifecycleObserver {
    public static final String TAG = LifecycleManager.class.getSimpleName();
    private LifecycleOwner owner;

    public LifecycleManager(Object owner) {
        if (owner instanceof LifecycleOwner) {
            ((LifecycleOwner) owner).getLifecycle().addObserver(this);
            this.owner = (LifecycleOwner) owner;
        }
    }

    /**
     * 获取到的是 fragment 或者 activity
     *
     * @return
     */
    public LifecycleOwner getLifecycleOwner() {
        return owner;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(LifecycleOwner owner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(LifecycleOwner owner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(LifecycleOwner owner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(LifecycleOwner owner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(LifecycleOwner owner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
    }

}
