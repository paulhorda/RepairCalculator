package com.example.repaircalculator.ui.screen.singIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.repaircalculator.R
import com.example.repaircalculator.databinding.ActivitySingInBinding
import com.example.repaircalculator.ui.screen.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingInBinding

    private val viewModel: SingInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingInBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val key = getString(R.string.shared_preferences_key)
        val sharedPreferences = getSharedPreferences(key, Context.MODE_PRIVATE)
        val oldUserId = sharedPreferences.getInt(key, 0)

        if (oldUserId != 0) {
            startActivity(Intent(this@SingInActivity, MainActivity::class.java))
            finish()
        }

        binding.singInMb.setOnClickListener {
            lifecycleScope.launch {
                val login = binding.singInLoginTil.editText?.text?.trim().toString()
                val password = binding.singInPasswordTil.editText?.text?.trim().toString()

                val user = viewModel.getUserOrNull(login, password)

                if (user != null && login.isNotEmpty() && password.isNotEmpty()) {
                    val editor = sharedPreferences?.edit()
                    editor?.putInt(key, user.id.toInt())
                    editor?.apply()

                    startActivity(Intent(this@SingInActivity, MainActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.put_field),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.toSingUpMb.setOnClickListener {
            lifecycleScope.launch {
                val login = binding.singInLoginTil.editText?.text?.trim().toString()
                val password = binding.singInPasswordTil.editText?.text?.trim().toString()

                val user = viewModel.getUserOrNull(login, password)

                if (user == null && login.isNotEmpty() && password.isNotEmpty()) {

                    viewModel.insertUser(login, password)
                    val insertedUser = viewModel.getUserOrNull(login, password)
                    val editor = sharedPreferences?.edit()
                    editor?.putInt(key, (insertedUser?.id ?: -1).toInt())
                    editor?.apply()

                    startActivity(Intent(this@SingInActivity, MainActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.put_field),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}