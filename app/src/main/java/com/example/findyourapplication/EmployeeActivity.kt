package com.example.findyourapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.findyourapplication.databinding.ActivityEmployeeBinding
import com.example.findyourapplication.databinding.FragmentProfileBinding

class EmployeeActivity : AppCompatActivity() {



    private lateinit var binding: ActivityEmployeeBinding
    private lateinit var navController:NavController
    private lateinit var homeRecyclerItemViewModel: HomeRecyclerItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_employee)
        init()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom__nav,menu)
        binding.bottomBar.setupWithNavController(menu!!,navController)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

    private fun init(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.employee_fragment_contoller) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        homeRecyclerItemViewModel= HomeRecyclerItemViewModel()
    }

    fun getHomeViewModel():HomeRecyclerItemViewModel{
        return homeRecyclerItemViewModel
    }

    companion object{
        fun getHomeViewModel():HomeRecyclerItemViewModel{
            return HomeRecyclerItemViewModel()
        }
    }
}