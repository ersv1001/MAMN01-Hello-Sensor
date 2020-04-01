package com.example.mamn01individuell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayAccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView textX;
    private TextView textY;
    private TextView textZ;
    static final float ALPHA = 0.25f;
    float[] gravSensorVals = null;
    float[] magSensorVals = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_accelerometer);

        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        textZ = findViewById(R.id.textZ);
        setTitle("Accelerometer");

        //Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(DisplayAccelerometerActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Low Pass fungerar ej just nu
        //if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        //    gravSensorVals = lowPass(event.values.clone(), gravSensorVals);
        //}
        if(gravSensorVals == null){
            gravSensorVals = event.values;
        }
        // Om skillnaden på nya värden är mindre än 10% skriv ej ut
        String x = "X = " + ((int) Math.ceil(gravSensorVals[0]));
        String y = "Y = " + ((int) Math.ceil(gravSensorVals[1]));
        String z = "Z = " + ((int) Math.ceil(gravSensorVals[2]));
        textX.setText(x);
        textY.setText(y);
        textZ.setText(z);

        //gravSensorVals = event.values;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(DisplayAccelerometerActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }
}
