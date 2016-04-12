package ru.litun.unitingtwist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

// Using R.layout.activity_main from the main source set
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AngleListener {

    val renderer: MyGLRenderer by lazy { MyGLRenderer() }
    val sensorManager: SensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gyroscope: Sensor by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) }
    val listener: GyroscopeListener by lazy { GyroscopeListener(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an OpenGL ES 2.0 context.
        surface.setEGLContextClientVersion(2)
        surface.setRenderer(renderer)

        // Render the view only when there is a change in the drawing data
        surface.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onResume() {
        super.onResume()
        surface.onResume()
        sensorManager.registerListener(listener, gyroscope, SensorManager.SENSOR_DELAY_GAME)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    override fun onPause() {
        super.onPause()
        surface.onPause()
        sensorManager.unregisterListener(listener)
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun setUp(x: Float, y: Float) {
        renderer.setUp(x, y)
        surface.requestRender()
    }
}
