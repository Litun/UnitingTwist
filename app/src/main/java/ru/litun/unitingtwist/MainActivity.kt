package ru.litun.unitingtwist

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import ru.litun.unitingtwist.GameListener;

// Using R.layout.activity_main from the main source set
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity(), AngleListener {

    val renderer: MyGLRenderer by lazy { MyGLRenderer(scene) }
    val sensorManager: SensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gyroscope: Sensor by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) }
    val listener: GyroscopeListener by lazy { GyroscopeListener(this) }
    val field by lazy { GameField() }
    val scene by lazy { Scene(field) }

    var score by  Delegates.observable(0) {
        prop, old, new ->
        scoreView.text = new.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an OpenGL ES 2.0 context.
        surface.setEGLContextClientVersion(2)
        surface.setRenderer(renderer)

        // Render the view only when there is a change in the drawing data
        surface.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        field.setGameListener (object : GameListener {
            override fun onCut(n: Int) {
                score += n
            }

            override fun onLose() {
                lose()
            }

        })

    }

    override fun onResume() {
        super.onResume()
        surface.onResume()
        sensorManager.registerListener(listener, gyroscope, SensorManager.SENSOR_DELAY_GAME)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        scene.resume()

        window.decorView.systemUiVisibility = ( (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
                or ((View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                or (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)));
    }

    override fun onPause() {
        super.onPause()
        surface.onPause()
        sensorManager.unregisterListener(listener)
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        scene.pause()
    }

    override fun setUp(x: Float, y: Float) {
        renderer.setUp(x, y)
        field.newUp(x, y)
        scene.update()
        surface.requestRender()
    }

    fun lose() {
        val intent = Intent();
        intent.putExtra("score", score);
        setResult(RESULT_OK, intent);
        finish();
    }
}
