package ru.litun.unitingtwist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
// Using R.layout.activity_main from the main source set
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        start.setOnClickListener { startGame() }
    }

    fun startGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, 3)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
