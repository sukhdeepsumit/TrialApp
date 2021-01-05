package com.example.trialapp.TeacherDatabase;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trialapp.R;

import com.example.trialapp.TeacherActivity.LogInTeacherActivity;
import com.example.trialapp.TeacherActivity.TeacherAccountInfo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class StudentDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    MyAdapter adapter;


    /* Authenticated User */
    String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Students_details");;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                    .setQuery(ref.child(user), Model.class)
                    .build();

        adapter =new MyAdapter(options);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav=findViewById(R.id.navMenu);
        drawerLayout=findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home : {
                        Toast.makeText(getApplicationContext(), "Home opened", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.myProfile :
                        Toast.makeText(getApplicationContext(), "My Account opened", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentDetails.this, TeacherAccountInfo.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logOut:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();

                        @SuppressLint("CommitPrefEdits")
                        SharedPreferences.Editor editor = LogInTeacherActivity.sharedPreferences.edit();
                        editor.putInt("key", 0);
                        editor.apply();

                        startActivity(new Intent(StudentDetails.this, LogInTeacherActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchData(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchData(String s) {
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(ref.child(user).orderByChild("subject").startAt(s).endAt(s+"\uf8ff"), Model.class)
                .build();

        adapter = new MyAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}