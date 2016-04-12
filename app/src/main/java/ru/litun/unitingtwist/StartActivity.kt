package ru.litun.unitingtwist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
// Using R.layout.activity_main from the main source set
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    var best: Int = -1
    val prefs by lazy { getSharedPreferences("prefs", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        start.setOnClickListener { startGame() }
        val best = prefs.getInt("best", -1)
        setText(-1, best)
    }

    fun startGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, 3)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 3 && resultCode == RESULT_OK) {
            val score = data?.getIntExtra("score", -1) ?: -1
            if (score > best) {
                best = score
                prefs.edit().putInt("best", best).apply()
            }
            setText(score, best)
        }
    }

    fun setText(score: Int, best: Int) {
        scoreView.text = (if (score > -1) "Score: $score \n" else "") +
                (if (best > -1) "Best score: $best" else "")
    }
}
