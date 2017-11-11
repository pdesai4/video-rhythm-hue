package com.example.priyankadesai.videorhythmhue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    public void openVideoPlayer(View view) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        startActivity(intent);
    }

    public void openPhilipsHueApp(View view) {
        // TODO
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.philips.lighting.hue2");
        if (launchIntent != null) {
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                openPhilipsHueApp(v);
                break;

            case R.id.button2:
                openVideoPlayer(v);
                break;
        }
    }
}
