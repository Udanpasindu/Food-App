package com.example.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the splash screen layout as the activity content
        setContentView(R.layout.activity_splash_screen)

        // Set a timer to wait for a specified duration before launching the main activity
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the main activity
            startActivity(Intent(this, MainActivity::class.java))

            // Finish the splash activity
            finish()
        }, SPLASH_DURATION)
    }

    companion object {
        // Duration of the splash screen in milliseconds
        private const val SPLASH_DURATION = 3000L
    }
}