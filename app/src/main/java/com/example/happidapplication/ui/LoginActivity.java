package com.example.happidapplication.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.chaos.view.PinView;
import com.example.happidapplication.R;
import com.example.happidapplication.databinding.ActivityloginBinding;
import com.example.happidapplication.databinding.ActivityonboardingBinding;


public class LoginActivity extends AppCompatActivity {

    ActivityloginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activitylogin);

        requestotp();

        binding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });


        SpannableString spannableString = new SpannableString("By creating passcode you agree with our Terms & Conditions and Privacy Policy");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Handle click action
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED); // Change color here
                ds.setUnderlineText(true); // Add underline
            }
        };

        spannableString.setSpan(clickableSpan, 40, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.termsAndConditions.setText(spannableString);
        binding.termsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());

    }

    // Function to generate OTP
    String generateOTP(String mobileNumber) {
        String firstTwoDigits = mobileNumber.substring(0, 2);
        String lastTwoDigits = mobileNumber.substring(mobileNumber.length() - 2);
        return firstTwoDigits + lastTwoDigits;
    }


    private void requestotp() {

        binding.reqOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mobnumber.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Mobile Number to Login", Toast.LENGTH_SHORT).show();
                } else if (binding.mobnumber.getText().toString().length() < 10) {
                    Toast.makeText(LoginActivity.this, "Please Enter Mobile 10 Digits Mobile Number", Toast.LENGTH_SHORT).show();

                } else {
                    String generatedOTP = generateOTP(binding.mobnumber.getText().toString());


                    Intent intent = new Intent(LoginActivity.this, OTPverificationActivity.class);
                    intent.putExtra("generatedOTP", generatedOTP);
                    startActivity(intent);
//                    showOtpPopup(generatedOTP);
                }
            }
        });


    }


    private void showOtpPopup(String generatedOTP) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.otp_popup, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView otpnumber = alertDialog.findViewById(R.id.otpnumber);

        otpnumber.setText(generatedOTP);


    }


}
