package com.example.pdfmaker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

/*
Splash Screen is the First Activity of our Application
*/
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Handler used to send and process Message
        Handler(Looper.getMainLooper()).postDelayed({
            /* Intent to start the Main Activity*/
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            // 1000ms = 1sec Duration time of Splash Screen
        }, 1000)
    }
}