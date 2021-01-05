package com.example.trialapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trialapp.StudentActivity.StudentHomeScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherAddForm extends AppCompatActivity {
    EditText firstName, lastName, contact, email, subject;
    Button save;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Teachers_details");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_form);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.teacherEmailId);
        contact = findViewById(R.id.mobileNum);
        subject=findViewById(R.id.subject);



        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });
    }
    private void registerUser() {
        email.setError(null);


        String myEmail = email.getText().toString();

        String myContact=contact.getText().toString();

        boolean cancel =false;
        View focusView = null;


        //email validation
        if(TextUtils.isEmpty(myEmail)) {
            email.setError("Your Email is Invalid");
            focusView = email;
            cancel = true;
        }
        else if(!checkEmail(myEmail)) {
            email.setError("Your Email is Invalid");
            focusView = email;
            cancel = true;
        }
        if(!checkContact(myContact))
        {
            contact.setError("Your contact is invalid");
            focusView=contact;
            cancel=true;

        }

        if(cancel) {
            focusView.requestFocus();
        }
        else {
            InsertData();
            startActivity(new Intent(TeacherAddForm.this, StudentHomeScreen.class));
        }
    }
    private boolean checkEmail(String email) {
        return email.contains("@");
    }
    private boolean checkContact(String contact)
    {
        Pattern p = Pattern.compile("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$");

        Matcher m = p.matcher(contact);
        return (m.find() && m.group().equals(contact));


    }
    private void InsertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", firstName.getText().toString());
        map.put("lastName", lastName.getText().toString());
        map.put("email", email.getText().toString());
        map.put("contact", contact.getText().toString());
        map.put("subject", subject.getText().toString());


        ref.child(new StudentHomeScreen().user).push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firstName.setText("");
                        lastName.setText("");
                        contact.setText("");
                        email.setText("");
                        subject.setText("");

                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Insertion Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}