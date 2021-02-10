package com.example.findyourapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.findyourapplication.databinding.ActivityLoginSignupBinding

class LoginSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login_signup)
    }
}