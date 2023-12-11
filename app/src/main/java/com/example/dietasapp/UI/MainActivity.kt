package com.example.dietasapp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.dietasapp.R
import com.example.dietasapp.databinding.ActivityMainBinding
import com.example.dietasapp.viewModel.MainViewModel
import com.google.firebase.FirebaseApp

/**
 * The main activity responsible for initializing the app and navigating between fragments.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainVM: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        setToolbar()
    }

    fun setToolbar() {
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.fragment_container_view_main) as NavHostFragment
        val navController = navHostFrag.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarMain.setupWithNavController(navController, appBarConfiguration)
    }

    fun setObserver(){
        mainVM.getMsgFail().observe(this) {
            Log.v("MAIN_ACTIVITY", "Mensagem: $it")
        }

        mainVM.getIsAuth().observe(this){
            if(it){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
}