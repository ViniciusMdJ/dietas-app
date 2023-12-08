package com.example.dietasapp.UI

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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainVM: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mainVM = ViewModelProvider(this).get(MainViewModel::class.java)

//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            add<LoginFragment>(R.id.fragment_container_view_main)
//        }

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
            Log.v("MAIN_ACTIVITY", "Logado: $it")
        }
    }
}