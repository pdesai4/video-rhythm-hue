package com.example.priyankadesai.videorhythmhue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openVideoPlayer(View view) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        startActivity(intent);
    }

    public void openPhilipsHueApp(View view) {
        // TODO
    }
}
