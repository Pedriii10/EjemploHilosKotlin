package com.example.ejemplohiloskotlin

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvCrono: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var restartButton: Button
    private var countDownTask: CountDownTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCrono = findViewById(R.id.tvCrono)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        restartButton = findViewById(R.id.restartButton)

        startButton.setOnClickListener { startOrResumeTask() }
        stopButton.setOnClickListener { pauseTask() }
        restartButton.setOnClickListener { restartTask() }
    }

    private fun startOrResumeTask() {
        if (countDownTask == null || countDownTask?.status == AsyncTask.Status.FINISHED) {
            countDownTask = CountDownTask()
            countDownTask?.execute(10)
        } else {
            countDownTask?.resumeCounting()
        }
    }

    private fun pauseTask() {
        if (countDownTask?.status == AsyncTask.Status.RUNNING) {
            countDownTask?.pauseCounting()
        }
    }

    private fun restartTask() {
        countDownTask?.cancel(true)
        countDownTask = CountDownTask()
        countDownTask?.execute(10)
        countDownTask?.resumeCounting()
    }

    private inner class CountDownTask : AsyncTask<Int, Int, String>() {

        private var countingPaused = false
        private val pauseLock = Object()

        override fun doInBackground(vararg params: Int?): String {
            var remaining = params[0] ?: 0
            while (remaining > 0 && !isCancelled) {
                synchronized(pauseLock) {
                    while (countingPaused) {
                        try {
                            pauseLock.wait()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
                publishProgress(remaining)
                remaining--

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            return "Terminado"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            tvCrono.text = values[0].toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            tvCrono.text = result
        }

        fun pauseCounting() {
            countingPaused = true
        }

        fun resumeCounting() {
            countingPaused = false
            synchronized(pauseLock) {
                pauseLock.notifyAll()
            }
        }
    }
}
