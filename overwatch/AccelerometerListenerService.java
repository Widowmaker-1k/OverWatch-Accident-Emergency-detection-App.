package com.example.overwatch;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


public class AccelerometerListenerService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor linearAccelerationSensor;
    private Sensor gyroscopeSensor;
    private boolean listening = false;
    private static final float THRESHOLD = 6.94f; // 25km/h in m/s
    private static final int ACCELERATION_THRESHOLD_LIGHT = 30;
    private static final int ACCELERATION_THRESHOLD_MODERATE = 50;
    private static final int ACCELERATION_THRESHOLD_SEVERE = 60;
    private static final int ACCELERATION_THRESHOLD_EXTREME = 100;
    private static final float GYROSCOPE_THRESHOLD = 2.3f; // rad/s
    private float[] gyroSample = new float[3];
    private static final float GYROSCOPE_TIME_THRESHOLD = 0.9f; // seconds
    private long lastGyroscopeEventTime = 0;
    private boolean isGyroscopeThresholdExceeded = false;
    private static final float ACCELERATION_THRESHOLD = 6.94f; // 25 km/h in m/s
    private long gyroSampleTime=0;

    private boolean isListening = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isListening) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            isListening = true;
        }
        return START_STICKY;
    }
    private void startListening() {
        if (!listening) {
            sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
            listening = true;
        }
    }

    private void stopListening() {
        if (listening) {
            sensorManager.unregisterListener(this);
            listening = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isListening) {
            sensorManager.unregisterListener(this);
            isListening = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Calculate acceleration magnitude using the formula: √(x^2 + y^2 + z^2)
        float accelerationMagnitude = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        if (accelerationMagnitude > THRESHOLD) {

        // Check if the acceleration magnitude matches any of the thresholds
        if (accelerationMagnitude >= ACCELERATION_THRESHOLD_LIGHT && accelerationMagnitude < ACCELERATION_THRESHOLD_MODERATE) {
            openOverwatchApp();
        } else if (accelerationMagnitude >= ACCELERATION_THRESHOLD_MODERATE && accelerationMagnitude < ACCELERATION_THRESHOLD_SEVERE) {
            openOverwatchApp();
        } else if (accelerationMagnitude >= ACCELERATION_THRESHOLD_SEVERE && accelerationMagnitude < ACCELERATION_THRESHOLD_EXTREME) {
            openOverwatchApp();
        } else if (accelerationMagnitude >= ACCELERATION_THRESHOLD_EXTREME) {
            openOverwatchApp();
        }
    }
       if (event.sensor == accelerometer) {
            float x1 = event.values[0];
            float y1 = event.values[1];
            float z1 = event.values[2];
            float accelerationMagnitude1 = (float) Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2) + Math.pow(z1, 2));

            if (accelerationMagnitude1 > ACCELERATION_THRESHOLD) {
                if (isGyroscopeThresholdExceeded && System.currentTimeMillis() - lastGyroscopeEventTime <= GYROSCOPE_TIME_THRESHOLD * 1000) {
                    openOverwatchApp();
                } else {
                    isGyroscopeThresholdExceeded = false;
                }
            }
        }
       if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Check if the gyroscope readings exceed the threshold in less than 0.15 seconds
            long currentTime = System.currentTimeMillis();
            float dt = (currentTime - gyroSampleTime) * 1e-3f;
            if (dt < 2) {
                float omegaMagnitude = (float) Math.sqrt(Math.pow(event.values[0] - gyroSample[0], 2)
                        + Math.pow(event.values[1] - gyroSample[1], 2)
                        + Math.pow(event.values[2] - gyroSample[2], 2));
                float omegaMagnitudeThreshold = GYROSCOPE_THRESHOLD * dt;
                Log.d("Gyroscope", "Omega Magnitude: " + omegaMagnitude);
                if (omegaMagnitude > omegaMagnitudeThreshold)
                {
                    openOverwatchApp();
                }
            }


           // Save the current gyroscope readings for the next comparison
            System.arraycopy(event.values, 0, gyroSample, 0, 3);
            gyroSampleTime = currentTime;
        }
    }


    private void openOverwatchApp() {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage("com.blizzard.overwatch");
        Intent intent2 = new Intent(AccelerometerListenerService.this, ManualEmergency.class);

        if (intent != null) {
            startActivity(intent);
            startActivity(intent2);

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed in this example
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Not needed in this example
        return null;
    }

}
