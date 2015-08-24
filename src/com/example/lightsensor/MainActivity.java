package com.example.lightsensor;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    ProgressBar lightMeter;
    TextView textMax, textReading;
     float counter;
     Button read;
     TextView display;

        /** Called when the activity is first created. */

     @Override
        public void onCreate(Bundle savedInstanceState) {
         counter = 0;
         setContentView(R.layout.activity_main);
         read = (Button) findViewById(R.id.bStart);
         display = (TextView) findViewById(R.id.tvDisplay);

            super.onCreate(savedInstanceState);
            
            lightMeter = (ProgressBar)findViewById(R.id.lightmeter);
            textMax = (TextView)findViewById(R.id.max);
            textReading = (TextView)findViewById(R.id.reading);

            SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
            Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

            if (lightSensor == null){
             Toast.makeText(MainActivity.this,"No Light Sensor! quit-", Toast.LENGTH_LONG).show();
            }
            else
            {
             float max =  lightSensor.getMaximumRange();
             lightMeter.setMax((int)max);
             textMax.setText("Max Reading(Lux): " + String.valueOf(max));

             sensorManager.registerListener(lightSensorEventListener,lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

            }
        }

     SensorEventListener lightSensorEventListener= new SensorEventListener(){

      @Override
      public void onAccuracyChanged(Sensor sensor, int accuracy) {
       // TODO Auto-generated method stub

      }

      @Override
      public void onSensorChanged(SensorEvent event) {
       // TODO Auto-generated method stub
       if(event.sensor.getType()==Sensor.TYPE_LIGHT){
        final float currentReading = event.values[0];
        lightMeter.setProgress((int)currentReading);
        textReading.setText("Current Reading(Lux): " + String.valueOf(currentReading));
        read.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                display.setText("" + String.valueOf(currentReading));
            }
        });

       }
      }

        };
    }