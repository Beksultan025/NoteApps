package com.example.noteapp02.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.noteapp02.R
import com.example.noteapp02.databinding.ActivityMainBinding
import com.example.noteapp02.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedPreferences = PreferenceHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences.unit(this)
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(R.navigation.nav_graph)

        if (sharedPreferences.isShowOnBoard){
            navGraph.setStartDestination(R.id.noteFragment)
            navHostFragment.navController.graph = navGraph
        }
    }
}