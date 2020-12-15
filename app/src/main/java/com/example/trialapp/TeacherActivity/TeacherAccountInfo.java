package com.example.trialapp.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trialapp.R;
import com.example.trialapp.TeacherDatabase.StudentDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//class about teacher
public class TeacherAccountInfo extends AppCompatActivity {

    String firstName, lastName, contact, email, subject;

    TextView firstNameText, lastNameText, contactText, emailText, subjectText;
    Button updateButton, backButton;

    String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Teachers_profile").child(user);

    TeacherModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_account_info);

        firstNameText = findViewById(R.id.firstNameShow);
        lastNameText = findViewById(R.id.lastNameShow);
        contactText = findViewById(R.id.PhoneTextShow);
        emailText = findViewById(R.id.EmailTextShow);
        subjectText = findViewById(R.id.subjectShow);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(TeacherModel.class);
                assert model != null;
                firstName = model.firstName;
                lastName = model.lastName;
                contact = model.myContact;
                email = model.myEmail;
                subject = model.mySubject;

                firstNameText.setText(firstName);
                lastNameText.setText(lastName);
                contactText.setText(contact);
                emailText.setText(email);
                subjectText.setText(subject);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        updateButton = findViewById(R.id.updateProfile);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus =DialogPlus.newDialog(firstNameText.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_content_profile))
                        .setExpanded(true, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .create();

                View myView = dialogPlus.getHolderView();
                final EditText firstName = myView.findViewById(R.id.updateFirstNameProfile);
                final EditText lastName = myView.findViewById(R.id.updateLastNameProfile);
                final EditText email = myView.findViewById(R.id.updateEmailProfile);
                final EditText contact = myView.findViewById(R.id.updateContactProfile);
                final EditText subject = myView.findViewById(R.id.updateSubjectProfile);
                Button submit = myView.findViewById(R.id.submitProfile);

                firstName.setText(model.getFirstName());
                lastName.setText(model.getLastName());
                email.setText(model.getMyEmail());
                contact.setText(model.getMyContact());
                subject.setText(model.getMySubject());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("firstName", firstName.getText().toString());
                        map.put("lastName", lastName.getText().toString());
                        map.put("myEmail", email.getText().toString());
                        map.put("myContact", contact.getText().toString());
                        map.put("mySubject", subject.getText().toString());

                        ref.updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(firstName.getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(firstName.getContext(), "Updating Unsuccessful", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        
        backButton = findViewById(R.id.backProfile);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherAccountInfo.this, StudentDetails.class));
            }
        });
    }
}