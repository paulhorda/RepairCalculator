package com.example.repaircalculator.ui.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.repaircalculator.R
import com.example.repaircalculator.data.entity.Project
import com.example.repaircalculator.databinding.ActivityMainBinding
import com.example.repaircalculator.ui.screen.newProject.NewProjectActivity
import com.example.repaircalculator.ui.screen.project.ProjectActivity
import com.example.repaircalculator.ui.screen.singIn.SingInActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.internal.ContextUtils.getActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.materialToolbar.title = getString(R.string.projects)
        setSupportActionBar(binding.materialToolbar)

        binding.projectsRv.adapter = viewModel.adapter
        binding.projectsRv.addItemDecoration(DividerItemDecoration(this@MainActivity,
            LinearLayout.VERTICAL))
        val thread: Thread = object : Thread() {
            override fun run() {
                viewModel.observe()
            }
        }
        thread.start()
        viewModel.adapter.categoryCallback = {
            val intent = Intent(this, ProjectActivity::class.java)
            intent.putExtra("KEY_PROJECT", it.id)
            startActivity(intent)
            null
        }
        binding.newProjectEfab.setOnClickListener { v: View? ->
            startActivity(Intent(this,
                NewProjectActivity::class.java))
        }
        binding.textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel!!.getDataByQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })

        (this as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.log_out -> {
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setMessage("Ви дійсно хочете вийти з профілю?")
                            .setPositiveButton("Так") { _, _ ->
                                this@MainActivity.getSharedPreferences("user_id",
                                    Context.MODE_PRIVATE)?.edit()?.remove("user_id")?.apply()
                                startActivity(Intent(this@MainActivity, SingInActivity::class.java))
                                this@MainActivity.finish()
                            }
                            .setNegativeButton("Ні") { _, _ -> }
                            .create()
                            .show()
                    }
                }
                return true
            }
        }, this, Lifecycle.State.RESUMED)
    }
}