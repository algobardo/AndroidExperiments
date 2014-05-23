package com.example.android.musicplayer.test;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.android.musicplayer.MainActivity;
import com.google.android.apps.common.testing.ui.espresso.IdlingResource;

import static com.google.android.apps.common.testing.testrunner.util.Checks.checkNotNull;

/**
 * Created by cqa on 23/05/14.
 */
public class StatusIdlingResource implements IdlingResource {
    private String resourceName, waitForStatus, waitWhileStatus, currentStatus;
    private ResourceCallback resourceCallback;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String currentTrackStatus = intent.getStringExtra("currentTrackStatus");
            currentStatus = currentTrackStatus;

            if (isIdleNow()) {
                resourceCallback.onTransitionToIdle();
            }
        }
    };

    public StatusIdlingResource(String resourceName, Activity activity, String waitForStatus, String waitWhileStatus) {
        this.resourceName = checkNotNull(resourceName);
        this.waitForStatus = waitForStatus;
        this.waitWhileStatus = waitWhileStatus;

        // Register to receive messages
        LocalBroadcastManager.getInstance(activity).registerReceiver(mMessageReceiver,
                new IntentFilter(MainActivity.SET_CURRENT_TRACK_ACTION));
    }

    @Override
    public String getName() {
        return resourceName;
    }

    @Override
    public boolean isIdleNow() {
        // Note: If waitWhileStatus is initialized to the initial value of currentStatus, then
        // it will wait since currentStatus = null initially (could give currentStatus an initial
        // value.
        return (waitForStatus == null || (currentStatus != null && currentStatus.equals(waitForStatus)))
                && (waitWhileStatus == null || (currentStatus != null && !currentStatus.equals(waitWhileStatus)));
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}
