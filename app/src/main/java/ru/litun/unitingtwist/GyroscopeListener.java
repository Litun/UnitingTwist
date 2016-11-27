package ru.litun.unitingtwist;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.Date;

/**
 * Created by Litun on 20.03.2016.
 */
public class GyroscopeListener implements SensorEventListener {

    private AngleListener listener;
    private Date previousDate;
    private float angle = 0f;

    public GyroscopeListener(AngleListener l) {
        listener = l;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if (previousDate == null) {
                previousDate = new Date();
                //angle = z;
            } else {
                Date current = new Date();
                long delta = current.getTime() - previousDate.getTime();
                previousDate = current;
                angle += z * delta / 1000;
                angle = (float) ((angle + Math.PI * 2) % (Math.PI * 2));

                double sin = Math.sin(angle);
                double cos = Math.cos(angle);

                listener.setUp((float) cos, (float) sin);
            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
