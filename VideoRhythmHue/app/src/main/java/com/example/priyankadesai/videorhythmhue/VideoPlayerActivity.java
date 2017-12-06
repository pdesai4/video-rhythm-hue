package com.example.priyankadesai.videorhythmhue;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    private static final long REFRESH_RATE = 1000;
    private VideoView mVideoView;
    private Handler mHandler;
    private Runnable mScreenShotTask;
    private MediaMetadataRetriever mMediaMetadataRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        mHandler = new Handler();
        Uri uri = Uri.parse("android.resource://com.example.priyankadesai.videorhythmhue/" + R.raw.sample);

        mMediaMetadataRetriever = new MediaMetadataRetriever();
        mMediaMetadataRetriever.setDataSource(this, uri);

        mVideoView = findViewById(R.id.videoView);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);

        // TODO: Do this on background thread
        final ImageView imageViewTemp = findViewById(R.id.imageViewTemp);
        mScreenShotTask = new Runnable() {
            @Override
            public void run() {
                if (mVideoView.isPlaying()) {
                    Bitmap frame = mMediaMetadataRetriever.getFrameAtTime(
                            mVideoView.getCurrentPosition() * 1000,
                            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                    );
                    if (frame != null) {
                        imageViewTemp.setImageBitmap(frame);
                    }
                    AverageColor averageColor = AverageColor.fromBitmap(frame, 1);
                    System.out.println("The output is ======> " + averageColor);
                }
                mHandler.postDelayed(this, REFRESH_RATE);
            }
        };
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
            mScreenShotTask.run();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mScreenShotTask = null;
        mHandler = null;
    }
}
