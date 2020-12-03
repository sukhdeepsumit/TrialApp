package com.example.trialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
//This is Teacher's activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void screen1(View v) {

            Intent intent = new Intent(MainActivity.this, LogInStudentActivity.class);
            startActivity(intent);

        }
    }

