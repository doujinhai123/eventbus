package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Leon on 2016/9/5.
 */
public class SubscriberActivity extends Activity {

    private static final String TAG = "SubscriberActivity";
    private MyObject mObject;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册事件总线
        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);

        mObject = new MyObject();
        mObject.registerEventBus();
    }

    /**
     * POSTING线程模型：在哪个线程发布事件，就在哪个线程执行onPostingEvent方法
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(MyEvent event) {
        Log.d(TAG, "onPostingEvent:" + Thread.currentThread().getName());
    }


    /**
     * MAIN线程模型：不管是哪个线程发布事件，都在主线程执行onMainEvent方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MyEvent event) {
        Log.d(TAG, "onMainEvent: " + Thread.currentThread().getName());
    }

    /**
     * BACKGROUND线程模型：事件如果是在子线程发布，onBackgroundEvent方法就在该子线程执行，事件如果是在
     * 主线程中发布，onBackgroundEvent方法就在EventBus内部的线程池中执行
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundEvent(MyEvent event) {
        Log.d(TAG, "onBackgroundEvent: " + Thread.currentThread().getName());
    }

    /**
     * ASYNC线程模型：不管事件在哪个线程发布，onAsyncEvent方法都在EventBus内部的线程池中执行
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onAsyncEvent(MyEvent event) {
        Log.d(TAG, "onAsyncEvent: " + Thread.currentThread().getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册事件总线
        EventBus.getDefault().unregister(this);

        mObject.unRegisterEventBus();
    }

    public void onStartPublisherActivity(View view) {
        Intent intent = new Intent(this, PublisherActivity.class);
        startActivity(intent);
    }

    public void onPublishStickyEvent(View view) {
        EventBus.getDefault().postSticky(new MyStickyEvent());
    }

    public void onStartStickyEvent(View view) {
        Intent intent = new Intent(this, StickyActivity.class);
        startActivity(intent);
    }
}
