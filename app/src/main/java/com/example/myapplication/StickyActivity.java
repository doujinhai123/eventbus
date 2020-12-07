package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Leon on 0013.
 */

public class StickyActivity extends Activity {

    private static final String TAG = "StickyActivity";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        EventBus.getDefault().register(this);
    }

    @Subscribe( sticky = true, threadMode = ThreadMode.POSTING)
    public void onPostingEvent(MyStickyEvent event) {
        Log.d(TAG, "onPostingEvent:" + Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
