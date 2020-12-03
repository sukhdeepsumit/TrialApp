package com.example.trialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LogInTeacherActivity extends AppCompatActivity {

    TextView goToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_teacher);

        goToSignUp = findViewById(R.id.registerLinkTeacher);

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInTeacherActivity.this, SignUpTeacherActivity.class));
            }
        });
    }
}

