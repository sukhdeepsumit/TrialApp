package com.example.trialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogInTeacherActivity extends AppCompatActivity {
//This is Teacher's activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_teacher);
    }
    public void screen1(View v) {

            Intent intent = new Intent(LogInTeacherActivity.this, LogInStudentActivity.class);
            startActivity(intent);

        }
    }

