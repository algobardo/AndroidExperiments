/*   
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.musicplayer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.util.LogPrinter;
import android.util.Log;
import android.os.Looper;
import android.widget.TextView;

/** 
 * Main activity: shows media player buttons. This activity shows the media player buttons and
 * lets the user click them. No media handling is done here -- everything is done by passing
 * Intents to our {@link MusicService}.
 * */
public class MainActivity extends Activity implements OnClickListener {
    /**
     * The URL we suggest as default when adding by URL. This is just so that the user doesn't
     * have to find an URL to test this sample.
     */
    public static final String SUGGESTED_URL = "http://www.vorbis.com/music/Epoq-Lepidoptera.ogg";

    public static final String SET_CURRENT_TRACK_ACTION = "com.example.android.musicplayer.action.SET_CURRENT_TRACK_ACTION";

    Button mPlayButton;
    Button mPauseButton;
    Button mSkipButton;
    Button mRewindButton;
    Button mStopButton;
    Button mEjectButton;

    TextView mCurrentTrackTextView;

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String currentTrackTitle = intent.getStringExtra("currentTrackTitle");
            String currentTrackStatus = intent.getStringExtra("currentTrackStatus");

            System.out.println("New state: " + currentTrackStatus);

            if (currentTrackStatus.equals("stopped")) {
                mCurrentTrackTextView.setText("(stopped)");
            } else {
                mCurrentTrackTextView.setText(currentTrackTitle + " (" + currentTrackStatus + ")");
            }
        }
    };

    /**
     * Called when the activity is first created. Here, we simply set the event listeners and
     * start the background service ({@link MusicService}) that will handle the actual media
     * playback.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Looper.getMainLooper().setMessageLogging(new LogPrinter(Log.VERBOSE,"Logger"));

        setContentView(R.layout.main);

        mPlayButton = (Button) findViewById(R.id.playbutton);
        mPauseButton = (Button) findViewById(R.id.pausebutton);
        mSkipButton = (Button) findViewById(R.id.skipbutton);
        mRewindButton = (Button) findViewById(R.id.rewindbutton);
        mStopButton = (Button) findViewById(R.id.stopbutton);
        mEjectButton = (Button) findViewById(R.id.ejectbutton);
        mCurrentTrackTextView = (TextView) findViewById(R.id.currentTrack);

        mPlayButton.setOnClickListener(this);
        mPauseButton.setOnClickListener(this);
        mSkipButton.setOnClickListener(this);
        mRewindButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mEjectButton.setOnClickListener(this);

        // Register to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(SET_CURRENT_TRACK_ACTION));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public void onClick(View target) {
        // Send the correct intent to the MusicService, according to the button that was clicked
        if (target == mPlayButton)
            startService(new Intent(MusicService.ACTION_PLAY));
        else if (target == mPauseButton)
            startService(new Intent(MusicService.ACTION_PAUSE));
        else if (target == mSkipButton)
            startService(new Intent(MusicService.ACTION_SKIP));
        else if (target == mRewindButton)
            startService(new Intent(MusicService.ACTION_REWIND));
        else if (target == mStopButton)
            startService(new Intent(MusicService.ACTION_STOP));
        else if (target == mEjectButton) {
            showUrlDialog();
        }
    }

    /** 
     * Shows an alert dialog where the user can input a URL. After showing the dialog, if the user
     * confirms, sends the appropriate intent to the {@link MusicService} to cause that URL to be
     * played.
     */
    void showUrlDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Manual Input");
        alertBuilder.setMessage("Enter a URL (must be http://)");
        final EditText input = new EditText(this);
        alertBuilder.setView(input);

        input.setText(SUGGESTED_URL);

        alertBuilder.setPositiveButton("Play!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {
                // Send an intent with the URL of the song to play. This is expected by
                // MusicService.
                Intent i = new Intent(MusicService.ACTION_URL);
                Uri uri = Uri.parse(input.getText().toString());
                i.setData(uri);
                startService(i);
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {}
        });

        alertBuilder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_HEADSETHOOK:
                startService(new Intent(MusicService.ACTION_TOGGLE_PLAYBACK));
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
