package com.example.firebase_ecomm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Screen_Activity extends AppCompatActivity {
    Runnable runnable;
    int login;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("MyDownload",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        login = sharedPreferences.getInt("login",0);
        runnable = new Runnable() {
            @Override
            public void run() {

                if(login==0)
                {
                    Intent intent = new Intent(Splash_Screen_Activity.this, Home_Activity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(Splash_Screen_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable,2000);
    }
}