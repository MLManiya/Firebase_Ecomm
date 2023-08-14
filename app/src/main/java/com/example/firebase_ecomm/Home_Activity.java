package com.example.firebase_ecomm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase_ecomm.databinding.ActivityHomeBinding;

public class Home_Activity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.activity_home);*/
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signup.setOnClickListener(view -> {
            Intent intent = new Intent(Home_Activity.this, Signup_Activity.class);
            startActivity(intent);
        });
        binding.signin.setOnClickListener(view -> {
            Intent intent = new Intent(Home_Activity.this, Signin_Activity.class);
            startActivity(intent);
        });
        binding.phoneno.setOnClickListener(view -> {
            Intent intent = new Intent(Home_Activity.this, Phone_No_Activity.class);
            startActivity(intent);
        });
        binding.google.setOnClickListener(view -> {
            Intent intent = new Intent(Home_Activity.this, Google_Auth_Activity.class);
            startActivity(intent);
        });
    }
}