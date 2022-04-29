package com.example.bookkart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bookkart.databinding.ActivitySignUpOptionsBinding;
import com.example.bookkart_android.MainActivity;
import com.example.bookkart_android.SignInActivity;

public class SignUpOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpOptionsBinding binding = ActivitySignUpOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.emailSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        });
        binding.googleSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        binding.facebookSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        binding.goToSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });
    }
}