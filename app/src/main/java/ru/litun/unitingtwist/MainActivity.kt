package ru.litun.unitingtwist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

// Using R.layout.activity_main from the main source set
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        surface.setRenderer(MyGLRenderer())
    }
}
