package com.example.dietasapp.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.dietasapp.R
import com.example.dietasapp.databinding.ActivityHomeBinding
import com.example.dietasapp.databinding.ActivityMainBinding
import com.example.dietasapp.viewModel.MainViewModel
import com.google.firebase.FirebaseApp
/**
 * Activity representing the main home screen.
 */
class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private val mainVM: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.userPerfilIcon.setOnClickListener(this)

        setToolbar()
    }

    private fun setToolbar() {
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.fragment_container_view_home) as NavHostFragment
        navController = navHostFrag.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarHome.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.user_perfil_icon){
            navController.navigate(R.id.userProfileFragment)
        }
    }
}