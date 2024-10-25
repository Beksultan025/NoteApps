package com.example.noteapp02.ui.activity

import android.Manifest
import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
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
        askNotificationPermission()
        mainSplashScreen()

        sharedPreferences.unit(this)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(R.navigation.nav_graph)

        if (sharedPreferences.isShowOnBoard) {
            navGraph.setStartDestination(R.id.signUpFragment)
        }

        if (sharedPreferences.isGoogleAuthSuccess) {
            navGraph.setStartDestination(R.id.noteFragment)
        }

        navHostFragment.navController.graph = navGraph
    }


    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }
    }

    private fun mainSplashScreen() = with(binding) {
        container.alpha = 0f

        lottieSplash.animate().alpha(1f)
        lottieSplash.addAnimatorListener(object : AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
                lottieSplash.repeatCount = 0
                lottieSplash.animate().alpha(0f)
                container.animate().alpha(1f)
            }
        })
    }
}