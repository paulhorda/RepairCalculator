package com.example.repaircalculator.ui.screen.project

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.ActivityMainBinding
import com.example.repaircalculator.databinding.ActivityProjectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectActivity : AppCompatActivity() {
    private val navHostFragment: NavHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment

    private lateinit var binding: ActivityProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val projectId = intent.getIntExtra("KEY_PROJECT",0)

        setSupportActionBar(binding.materialToolbar)
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.roomsFragment
                -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
                else -> {
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
            title = destination.label
        }

        binding.materialToolbar.setNavigationOnClickListener {
            navHostFragment.navController.popBackStack()
        }
    }
}