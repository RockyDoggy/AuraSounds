package com.rockydoggy.aurasounds;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int[] soundsNames = new int[] {
            R.string.soundNameRain,
            R.string.soundNameSea,
            R.string.soundNameBirds};

    int currentNameIndex = 0;
    int currentPlayIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent data = new Intent(this, PlayerService.class);

        Button nextButton = (Button) findViewById(R.id.nextButton);
        Button prevButton = (Button) findViewById(R.id.prevButton);
        final Button playButton = (Button) findViewById(R.id.playStopButton);
        final TextView playNow = (TextView) findViewById(R.id.playingNow);
        playNow.setText(soundsNames[currentNameIndex]);

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.nextButton:
                        Log.i("Next", "Next");
                        if (currentNameIndex < soundsNames.length - 1){currentNameIndex++;}
                        else currentNameIndex = 0;

                        if (currentPlayIndex == currentNameIndex) {
                            playButton.setText(R.string.playStop);
                        }
                        else {
                            playButton.setText(R.string.playStopButton);
                        }

                        Log.i("Prev", "CurrName: " + currentNameIndex + "CurrPlay: " + currentPlayIndex);
                        playNow.setText(soundsNames[currentNameIndex]);
                        break;

                    case R.id.prevButton:
                        Log.i("Prev", "Prev");
                        if (currentNameIndex>0){currentNameIndex--;}
                        else currentNameIndex = soundsNames.length - 1;

                        Log.i("Prev", "CurrName: " + currentNameIndex + "CurrPlay: " + currentPlayIndex);
                        if (currentPlayIndex == currentNameIndex) {
                            playButton.setText(R.string.playStop);
                        }
                        else {
                            playButton.setText(R.string.playStopButton);
                        }

                        playNow.setText(soundsNames[currentNameIndex]);
                        break;

                    case R.id.playStopButton:
                        Log.i("Play", "Play/Stop");
                        if (currentPlayIndex != currentNameIndex) {
                            currentPlayIndex = currentNameIndex;
                            playButton.setText(R.string.playStop);
                        }
                        else {
                            currentPlayIndex = -1;
                            playButton.setText(R.string.playStopButton);
                        }

                        data.setData(Uri.parse(Integer.toString(currentNameIndex)));
                        startService(data);

                        break;
                }
            }

        };

        nextButton.setOnClickListener(listener);
        prevButton.setOnClickListener(listener);
        playButton.setOnClickListener(listener);
    }

    public void stopService() {

        stopService(new Intent(getBaseContext(), PlayerService.class));
    }
/*
    @Override
    public void onDestroy() {
        stopService();
        super.onDestroy();
    }*/
}
