package com.example.trialapp.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trialapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpStudentActivity extends AppCompatActivity {
    Button signUpStudentBtn;
    private FirebaseAuth mAuth;
    EditText fName, lName, mEmail,mContact,mPwd,mCnfPwd;
    ProgressBar progressBar;
    DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Student's Profile");




//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        //updateUI(currentUser);
//        Toast.makeText(this, "Already In", Toast.LENGTH_SHORT).show();
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);


        signUpStudentBtn=findViewById(R.id.signUpButton);
        fName=findViewById(R.id.firstName);
        lName=findViewById(R.id.lastName);
        mEmail=findViewById(R.id.studentEmailId);
        mContact=findViewById(R.id.mobileNum);
        mPwd=findViewById(R.id.signUpPassword);
        mCnfPwd=findViewById(R.id.confirmPassword);
        progressBar=findViewById(R.id.progressBar);
        mAuth= FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser()!=null)
//        {
//            startActivity(new Intent(getApplicationContext(),LogInStudentActivity.class));
//            finish();
//        }


        signUpStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeybaord(v);

                final  String myEmail=mEmail.getText().toString().trim();
                final String mobNum=mContact.getText().toString().trim();
                final String password= mPwd.getText().toString().trim();
                final String cnfPassword=mCnfPwd.getText().toString().trim();

                if(TextUtils.isEmpty(myEmail))
                {
                    mEmail.setError("Enter email !");
                    mEmail.requestFocus();
                }

                else if(TextUtils.isEmpty(mobNum))
                {
                    mContact.setError("Enter email !");
                    mContact.requestFocus();
                }

                else if(TextUtils.isEmpty(password) )
                {
                    mPwd.setError("Enter password !");
                    mPwd.requestFocus();
                }
               else if(password.length()<=6)
                {
                    mPwd.setError("Password length too short");
                    mPwd.requestFocus();
                }
                else if(TextUtils.isEmpty(cnfPassword) )
                {
                    mCnfPwd.setError("Enter password again !");
                    mCnfPwd.requestFocus();
                }
               else if(!password.equals(cnfPassword))
                {
                    mCnfPwd.setError("Incorrect password! ");
                    mCnfPwd.requestFocus();
                }
               else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(myEmail, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.i("TAG", "createUserWithEmail:success");
                                        Toast.makeText(SignUpStudentActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                        saveStudentProfileDetails();
                                        startActivity(new Intent(SignUpStudentActivity.this, LogInStudentActivity.class));


                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUpStudentActivity.this, "Failure" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    private void saveStudentProfileDetails()
    {
        String fn=fName.getText().toString();
        String ln = lName.getText().toString();
        String em =  mEmail.getText().toString();
        String ct =  mContact.getText().toString();

        StudentModel sm= new StudentModel(fn,ln,em,ct);
        String user= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        myRef.child(user).setValue(sm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Record Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }



}