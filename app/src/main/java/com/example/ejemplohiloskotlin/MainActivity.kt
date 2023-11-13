package com.example.ejemplohiloskotlin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity


// Segundo Hilo con runOnUiThread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvCrono: TextView = findViewById(R.id.tvCrono)

        Thread {
            var remaining = 10
            while (remaining > 0) {
                runOnUiThread {
                    tvCrono.text = remaining.toString()
                }
                Thread.sleep(1_000)
                remaining--
            }

            tvCrono.post {
                tvCrono.text = "Terminado"
            }
        }.start()
    }
}
