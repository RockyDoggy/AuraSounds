package com.rockydoggy.aurasounds;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by brodyga on 05.07.16.
 */
public class PlayerService extends Service {

    public  static String TAG = "Service";
    private boolean IsStarted = false;

    int sounds[] = new int[] {R.raw.rain,
                            R.raw.sea,
                            R.raw.birds};

    int currentIndex = -1;

    MediaPlayer currentSound = null;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        int tempCurrentIndex = Integer.valueOf(intent.getData().toString());

        if ( currentSound != null) {
            if (currentSound.isPlaying()) {
                currentSound.stop();
                currentSound.release();
                currentSound = null;
            }
        }

        if (currentIndex != tempCurrentIndex) {
            currentIndex = tempCurrentIndex;
            currentSound = MediaPlayer.create(this, sounds[currentIndex]);
            currentSound.start();
        }
        else {
            currentIndex = -1;
        }

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
/*
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service onDestroy");
    }*/
}
