package com.example.trialapp.AllTeacherRecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.trialapp.R;
import com.example.trialapp.StudentActivity.StudentHomeScreen;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AllTeacherRecords extends AppCompatActivity {

    RecyclerView recyclerView;
    TeacherRecordAdapter adapter;

    String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teacher_Records");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teacher_records);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ModelTeacherRecord> options =
                new FirebaseRecyclerOptions.Builder<ModelTeacherRecord>()
                .setQuery(reference.child(user), ModelTeacherRecord.class)
                .build();

        adapter = new TeacherRecordAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AllTeacherRecords.this, StudentHomeScreen.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}