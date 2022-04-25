package com.example.rawmotors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val btnContinue: Button by lazy {
        findViewById<Button>(R.id.btnContinue)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnContinue.setOnClickListener {
            val intent: Intent =
                Intent(
                    this@MainActivity,
                    LoginActivity::class.java
                )
            startActivity(intent)
        }
    }
}