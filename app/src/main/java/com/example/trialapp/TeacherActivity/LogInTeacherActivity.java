package com.example.trialapp.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trialapp.MainActivity;
import com.example.trialapp.R;
import com.example.trialapp.TeacherDatabase.StudentDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInTeacherActivity extends AppCompatActivity {

    TextView goToSignUp;
    EditText myEmail, myPassword;
    Button login;
    ProgressBar progressBar;

    private FirebaseAuth myAuth;

    public static SharedPreferences sharedPreferences;
    int AUTO_SAVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_teacher);

        goToSignUp = findViewById(R.id.registerLinkTeacher);


        myEmail = findViewById(R.id.username);
        myPassword = findViewById(R.id.pwd);
        login = findViewById(R.id.login);

        myAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarForTeacher);

        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int pref = sharedPreferences.getInt("key", 0);

        if(pref > 0) {
            startActivity(new Intent(getApplicationContext(), StudentDetails.class));
        }

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInTeacherActivity.this,SignUpTeacherActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserWithFirebase();
            }
        });
    }

    private void loginUserWithFirebase() {

        String email = myEmail.getText().toString();
        String password = myPassword.getText().toString();

        if(email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), "Logging you in...", Toast.LENGTH_SHORT).show();

        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("LOGIN", "Was user logged in : " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    showErrorBox();
                    progressBar.setVisibility(View.GONE);
                    Log.i("FINDCODE", "Message : " + task.getException());
                }
                else {
                    AUTO_SAVE = 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("key", AUTO_SAVE);
                    editor.apply();

                    Intent intent = new Intent(LogInTeacherActivity.this, StudentDetails.class);
                    finish();
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LogInTeacherActivity.this, MainActivity.class));
    }

    private void showErrorBox() {
        new AlertDialog.Builder(this)
                .setTitle("Ooooops!!")
                .setMessage("You entered the wrong email ID or password")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

