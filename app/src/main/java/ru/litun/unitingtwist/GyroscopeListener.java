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

    public GyroscopeListener(AngleListener l) {
        listener = l;
    }

    Date previousDate;
    float angle = 0f;

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

//        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//
//            if (xValue == -500)
//                xValue = x;
//            if (yValue == -500)
//                yValue = y;
//
//            xValue = APPROXIMATE_RATE * x + (1 - APPROXIMATE_RATE) * xValue;
//            yValue = APPROXIMATE_RATE * y + (1 - APPROXIMATE_RATE) * yValue;
//
//            float previousAngle = mRenderer.getAngle();
//            float angle = (float) Math.atan2(xValue, yValue);
//            angle *= 180 / Math.PI;
//
//            mRenderer.setAngle(-angle);
//            mRenderer.setUp(xValue, yValue);
//            mGLView.requestRender();
//        }

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

                double sin = Math.sin(-angle);
                double cos = Math.cos(-angle);

                //mRenderer.setAngle(angle);
                listener.setUp((float) cos, (float) sin);
                //mGLView.requestRender();

                System.out.println(angle);
            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setListener(AngleListener listener) {
        this.listener = listener;
    }
}
