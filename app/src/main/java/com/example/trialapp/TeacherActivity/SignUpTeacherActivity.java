package com.example.trialapp.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trialapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignUpTeacherActivity extends AppCompatActivity {

    Button signUpTeacherBtn;
    EditText firstName, lastName, myEmail,myContact,myPwd,myCnfPwd, mySubject;

    private FirebaseAuth myAuth;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Teachers_profile");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_teacher);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        myEmail = findViewById(R.id.teacherEmailId);
        myContact = findViewById(R.id.mobileNum);
        mySubject = findViewById(R.id.subjectTeach);
        myPwd = findViewById(R.id.signUpPassword);
        myCnfPwd = findViewById(R.id.confirmPassword);

        myAuth = FirebaseAuth.getInstance();

        signUpTeacherBtn = findViewById(R.id.signUpButton);

        signUpTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybaord(view);
                registerUser();
            }
        });
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    private void registerUser() {
        myEmail.setError(null);
        myPwd.setError(null);

        String email = myEmail.getText().toString();
        String password = myPwd.getText().toString();

        boolean cancel =false;
        View focusView = null;

        //password validation
        if(TextUtils.isEmpty(password) && !checkPassword(password)) {
            myPwd.setError("Your Password is too short");
            focusView = myPwd;
            cancel = true;
        }

        //email validation
        if(TextUtils.isEmpty(email)) {
            myEmail.setError("Your Email is Invalid");
            focusView = myEmail;
            cancel = true;
        }
        else if(!checkEmail(email)) {
            myEmail.setError("Your Email is Invalid");
            focusView = myEmail;
            cancel = true;
        }

        if(cancel) {
            focusView.requestFocus();
        }
        else {
            createUser();
        }
    }

    //Validation for email
    private boolean checkEmail(String email) {
        return email.contains("@");
    }

    //Validation for password
    private boolean checkPassword(String password) {
        String cnfPassword = myCnfPwd.getText().toString();
        return cnfPassword.equals(password) && password.length() > 6;
    }

    private void createUser() {

        String email = myEmail.getText().toString();
        String password = myPwd.getText().toString();

        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //delete before production
                Log.i("FINDCODE", "User creation was : " + task.isSuccessful());

                if (!task.isSuccessful()) {
                    showErrorBox("Oh No ! Registration failed");
                } else {
                    //saveUserName();
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    //save details method
                    saveProfileDetails();
                    //move user to login screen on success
                    Intent intent = new Intent(SignUpTeacherActivity.this, LogInTeacherActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    //create alert box for errors
    private void showErrorBox(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Heyyyy")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void saveProfileDetails() {

        String fn =  firstName.getText().toString();
        String ln = lastName.getText().toString();
        String em =  myEmail.getText().toString();
        String ct =  myContact.getText().toString();
        String sub =  mySubject.getText().toString();

        TeacherModel tm = new TeacherModel(fn, ln, em, ct, sub);
        String userName = Objects.requireNonNull(myAuth.getCurrentUser()).getUid();
        ref.child(userName).setValue(tm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Record Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}