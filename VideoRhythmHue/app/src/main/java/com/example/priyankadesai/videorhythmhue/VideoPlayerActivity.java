package com.example.priyankadesai.videorhythmhue;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

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
