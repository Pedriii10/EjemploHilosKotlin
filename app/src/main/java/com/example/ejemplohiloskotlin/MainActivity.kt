package com.example.ejemplohiloskotlin

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

// Asyntask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvCrono: TextView = findViewById(R.id.tvCrono)

        val asyncTask = MyAsyncTask(tvCrono)
        asyncTask.execute(10)
    }

    private class MyAsyncTask(private val textView: TextView) :
        AsyncTask<Int, Int, String>() {

        override fun doInBackground(vararg params: Int?): String {
            var remaining = params[0] ?: 0
            while (remaining > 0) {
                publishProgress(remaining)
                Thread.sleep(1_000)
                remaining--
            }
            return "Terminado"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            textView.text = values[0].toString()
        }

        override fun onPostExecute(result: String?) {
            textView.text = result
        }
    }
}
