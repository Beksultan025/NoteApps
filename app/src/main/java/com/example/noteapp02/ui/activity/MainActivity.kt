package com.example.noteapp02.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp02.R
import com.example.noteapp02.databinding.ActivityMainBinding
import com.example.noteapp02.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedPreferences = PreferenceHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val splashScreenDuration = 3500L
        Handler(Looper.getMainLooper()).postDelayed({
            binding.lottieSplash.visibility = View.GONE
            binding.container.visibility = View.VISIBLE
        }, splashScreenDuration)

        sharedPreferences.unit(this)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(R.navigation.nav_graph)

        if (sharedPreferences.isShowOnBoard) {
            navGraph.setStartDestination(R.id.signUpFragment)
        }

        if (sharedPreferences.isGoogleAuthSuccess){
            navGraph.setStartDestination(R.id.noteFragment)
        }

        navHostFragment.navController.graph = navGraph
    }
}