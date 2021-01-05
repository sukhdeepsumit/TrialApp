package com.example.trialapp.StudentActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trialapp.R;
import com.example.trialapp.TeacherActivity.TeacherAccountInfo;
import com.example.trialapp.TeacherActivity.TeacherModel;
import com.example.trialapp.TeacherDatabase.StudentDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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

public class StudentAccountInfo extends AppCompatActivity {
    String firstName, lastName, email, contact, standard;
    TextView firstNameText, lastNameText, emailText, contactText, standardText;
    Button update, back;

    String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Students_Profile").child(user);

    StudentModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account_info);

        firstNameText=findViewById(R.id.firstNameShow);
        lastNameText=findViewById(R.id.lastNameShow);
        emailText=findViewById(R.id.EmailTextShow);
        contactText=findViewById(R.id.PhoneTextShow);
        standardText=findViewById(R.id.standardTextShow);

       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               model = snapshot.getValue(StudentModel.class);
               assert model != null;
               firstName = model.firstName;
               lastName = model.lastName;
               contact = model.myContact;
               email = model.myEmail;
               standard = model.myStandard;


               firstNameText.setText(firstName);
               lastNameText.setText(lastName);
               contactText.setText(contact);
               emailText.setText(email);
               standardText.setText(standard);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               System.out.println("The read failed: " + error.getCode());

           }
       });


       update=findViewById(R.id.updateProfile);
       update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final DialogPlus dialogPlus=DialogPlus.newDialog(firstNameText.getContext())
                       .setContentHolder(new ViewHolder(R.layout.dialog_content_student_profile))
                       .setExpanded(true, ViewGroup.LayoutParams.WRAP_CONTENT)
                       .create();

               View myView = dialogPlus.getHolderView();
               final EditText firstName = myView.findViewById(R.id.updateFirstNameProfile);
               final EditText lastName = myView.findViewById(R.id.updateLastNameProfile);
               final EditText email = myView.findViewById(R.id.updateEmailProfile);
               final EditText contact = myView.findViewById(R.id.updateContactProfile);
               final EditText standard = myView.findViewById(R.id.standardProfile);

               Button submit = myView.findViewById(R.id.submitProfile);

               firstName.setText(model.getFirstName());
               lastName.setText(model.getLastName());
               email.setText(model.getMyEmail());
               contact.setText(model.getMyContact());
               standard.setText(model.getMyStandard());

               dialogPlus.show();

               submit.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String, Object> map = new HashMap<>();
                       map.put("firstName", firstName.getText().toString());
                       map.put("lastName", lastName.getText().toString());
                       map.put("myEmail", email.getText().toString());
                       map.put("myContact", contact.getText().toString());
                       map.put("myStandard", standard.getText().toString());


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

        back = findViewById(R.id.backProfile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentAccountInfo.this, StudentHomeScreen.class));
            }
        });
    }


}