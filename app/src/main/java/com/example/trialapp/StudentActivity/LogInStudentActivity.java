package com.example.trialapp.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trialapp.MainActivity;
import com.example.trialapp.R;
import com.example.trialapp.TeacherActivity.LogInTeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInStudentActivity extends AppCompatActivity {

    TextView goToSignUp;
    EditText myEmail, myPassword;
    Button login;
    ProgressBar progressBar;

    private FirebaseAuth myAuth;

    static SharedPreferences sharedPreferences;
    int AUTO_SAVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_student);

        goToSignUp = findViewById(R.id.registerLinkStudent);
        myEmail = findViewById(R.id.username);
        myPassword = findViewById(R.id.pwd);
        login = findViewById(R.id.login);
        progressBar=findViewById(R.id.progressBarForStudent);

        myAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int pref = sharedPreferences.getInt("key", 0);

        if(pref > 0) {
            startActivity(new Intent(getApplicationContext(), StudentHomeScreen.class));
        }

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInStudentActivity.this, SignUpStudentActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybaord(view);
                loginUserWithFirebase();
            }
        });
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
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


                    startActivity(new Intent(LogInStudentActivity.this,StudentHomeScreen.class));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LogInStudentActivity.this, MainActivity.class));
    }

    private void showErrorBox() {
        new AlertDialog.Builder(this)
                .setTitle("Ooooops!!")
                .setMessage("There was a problem logging in")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}