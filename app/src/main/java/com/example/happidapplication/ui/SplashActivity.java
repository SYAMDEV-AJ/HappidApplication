package com.example.happidapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.happidapplication.R;
import com.example.happidapplication.databinding.ActivitysplashBinding;


public class SplashActivity extends AppCompatActivity {

    ActivitysplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activitysplash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, OnBoardingActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);

    }
}
