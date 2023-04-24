package com.example.repaircalculator.ui.screen.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.ActivityMainBinding
import com.example.repaircalculator.databinding.ActivityProjectBinding
import com.example.repaircalculator.ui.screen.singIn.SingInActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

//        (this as MenuHost).addMenuProvider(object : MenuProvider {
//            override fun onPrepareMenu(menu: Menu) {
//                // Handle for example visibility of menu items
//            }
//
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.home_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when (menuItem.itemId) {
//                    R.id.project_profile -> {
//                        MaterialAlertDialogBuilder(this@ProjectActivity)
//                            .setMessage("Ви дійсно хочете вийти з профілю?")
//                            .setPositiveButton("Так") { _, _ ->
//                                this@ProjectActivity.getSharedPreferences("user_id",
//                                    Context.MODE_PRIVATE)?.edit()?.remove("user_id")?.apply()
//                                startActivity(Intent(this@ProjectActivity, SingInActivity::class.java))
//                                this@ProjectActivity.finish()
//                            }
//                            .setNegativeButton("Ні") { _, _ -> }
//                            .create()
//                            .show()
//                    }
//                }
//                return true
//            }
//        }, this, Lifecycle.State.RESUMED)

    }
}