package com.example.findyourapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent=Intent(this,LoginSignupActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, 3000)
    }

}