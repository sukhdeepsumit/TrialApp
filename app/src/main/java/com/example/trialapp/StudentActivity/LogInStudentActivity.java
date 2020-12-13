package com.example.trialapp.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trialapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInStudentActivity extends AppCompatActivity {

    TextView goToSignUp;
    EditText myEmail, myPassword;
    Button login;

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_student);

        goToSignUp = findViewById(R.id.registerLinkStudent);
        myEmail = findViewById(R.id.username);
        myPassword = findViewById(R.id.pwd);
        login = findViewById(R.id.login);

        myAuth = FirebaseAuth.getInstance();

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInStudentActivity.this, SignUpStudentActivity.class));
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

        Toast.makeText(getApplicationContext(), "Logging you in...", Toast.LENGTH_SHORT).show();

        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("LOGIN", "Was user logged in : " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    showErrorBox();
                    Log.i("FINDCODE", "Message : " + task.getException());
                }
                else {
                    startActivity(new Intent(LogInStudentActivity.this,StudentHomeScreen.class));
                }
            }
        });
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