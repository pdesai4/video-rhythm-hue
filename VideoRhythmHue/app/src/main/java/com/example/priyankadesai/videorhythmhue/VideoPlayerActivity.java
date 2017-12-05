package com.example.priyankadesai.videorhythmhue;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.File;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Uri uri = Uri.parse("android.resource://com.example.priyankadesai.videorhythmhue/" + R.raw.sample);
        mVideoView = findViewById(R.id.videoView);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);

        final int INTERVAL = 1000 * 5;
        final Handler mHandler = new Handler();
        final Activity test = this;
        final View mvideoview = mVideoView;
        final Runnable screenShotTask = new Runnable()
        {
            int times = 0;
            @Override
            public void run(){
                int a = AverageColor.calculateAverageColor(Utils.takeScreenShot(mvideoview),1);
                System.out.println("The output is ======> " + a);
                times ++;
                if(times < 20){
                    mHandler.postDelayed(this, INTERVAL);
                }
            }
        };

        screenShotTask.run();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mVideoView.isPlaying()) {
            mVideoView.requestFocus();
            mVideoView.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
    }
}
