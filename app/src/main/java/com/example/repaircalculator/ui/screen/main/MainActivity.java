package com.example.repaircalculator.ui.screen.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.repaircalculator.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.materialToolbar);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.projectsRv.setAdapter(viewModel.getAdapter());
        binding.projectsRv.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayout.VERTICAL));

        Thread thread = new Thread() {
            @Override
            public void run() {
                viewModel.observe();
            }
        };
        thread.start();

        binding.textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getDataByQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}