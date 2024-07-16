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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.happidapplication.R;
import com.example.happidapplication.databinding.ActivityOtpVerificationBinding;
import com.example.happidapplication.databinding.ActivityloginBinding;


public class OTPverificationActivity extends AppCompatActivity {

    ActivityOtpVerificationBinding binding;

    String otp = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verification);
        otp = getIntent().getStringExtra("generatedOTP");

        showOtpPopup(otp);
        otpvalidation();
        mobilenumberediting();

        binding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTPverificationActivity.this.finish();
            }
        });


        SpannableString spannableString = new SpannableString("Don't receive OTP? Resend");
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

        spannableString.setSpan(clickableSpan, 19, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.termsAndConditions.setText(spannableString);
        binding.termsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void mobilenumberediting() {
        binding.editmob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPverificationActivity.this, LoginActivity.class);
                startActivity(intent);
                OTPverificationActivity.this.finish();
            }
        });
    }

    private void otpvalidation() {
        binding.otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.otpnumber.getText().toString().isEmpty()) {
                    String value = binding.otpnumber.getText().toString();
                    if (otp.equals(value)) {
                        Toast.makeText(OTPverificationActivity.this, "OTP VerificationSuccess", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(OTPverificationActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(OTPverificationActivity.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(OTPverificationActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

                }


            }


        });

    }


    private void showOtpPopup(String generatedOTP) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OTPverificationActivity.this);
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
