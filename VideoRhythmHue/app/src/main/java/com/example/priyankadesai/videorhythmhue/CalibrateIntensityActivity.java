package com.example.priyankadesai.videorhythmhue;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CalibrateIntensityActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "VIDEO_RHYTHM_HUE";
    private OkHttpClient mOkHttpClient;
    private SensorManager mSensorManager;
    private Sensor mLight;
    private float mSensorReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate_intensity);
        findViewById(R.id.calibrateOK).setOnClickListener(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Show ambient light sensor readings in the text view
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calibrateOK) {
            int brightnessValue = calculateBrightnessValue();
            setHueBrightness(brightnessValue);
        }
    }

    private int calculateBrightnessValue() {
        return (int) (254 * mSensorReading / 40000);
    }

    private void setHueBrightness(int brightnessValue) {
        // Send brightness value to the hue
        String url = "http://192.168.2.3/api/m1PdiJCDPDPWOg7oIOVBt-Dzja2nSigINkN4dryx/lights/1/state";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("bri", brightnessValue);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, jsonBody.toString());
        final Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Hue API call onFailure", e);
                finish();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "Hue API call onResponse");
                Log.d(TAG, "Response code: " + response.code());
                //noinspection ConstantConditions
                Log.d(TAG, "Response: " + response.body().string());
                finish();
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            Log.i("Sensor Changed", "onSensor Change :" + event.values[0]);
            mSensorReading = event.values[0];
            String intensityValue = String.valueOf(mSensorReading);
            TextView textView = findViewById(R.id.intensityView);
            textView.setText(intensityValue);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor.getType() == Sensor.TYPE_LIGHT) {
            Log.i("Sensor Changed", "Accuracy :" + accuracy);
        }
    }
}
