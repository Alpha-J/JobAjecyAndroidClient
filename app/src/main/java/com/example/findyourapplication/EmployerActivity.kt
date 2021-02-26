package com.example.findyourapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.findyourapplication.databinding.ActivityEmployerBinding

class EmployerActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEmployerBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_employer)
        init()
    }

    private fun init(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.employer_fragment_contoller) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.employer_bottom_nav,menu)
        binding.bottomBar.setupWithNavController(menu!!,navController)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

}