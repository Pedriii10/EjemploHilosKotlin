package com.example.ejemplohiloskotlin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

//Ocurre lo mismo que en java, la app no carga hasta que el hilo termina

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvCrono: TextView = findViewById(R.id.tvCrono)
        var remaining = 10
        while (remaining > 0) {
            tvCrono.text = remaining.toString()
            Thread.sleep(1_000)
            remaining--
        }
        tvCrono.text = "Terminado"
    }
}
