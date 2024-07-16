package com.example.happidapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.happidapplication.R;
import com.example.happidapplication.databinding.ActivityonboardingBinding;


public class OnBoardingActivity extends AppCompatActivity {

    ActivityonboardingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activityonboarding);

        btnclick();

    }

    private void btnclick() {
        binding.getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
