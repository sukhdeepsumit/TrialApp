package com.example.trialapp.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.trialapp.R;
import com.example.trialapp.TeacherActivity.LogInTeacherActivity;
import com.example.trialapp.TeacherActivity.TeacherAccountInfo;
import com.example.trialapp.TeacherDatabase.StudentDetails;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class StudentHomeScreen extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_screen);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=findViewById(R.id.navStudentMenu);
        drawerLayout=findViewById(R.id.studentDrawer);

        mToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                        //startActivity(new Intent(StudentDetails.this, TeacherAccountInfo.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logOut:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StudentHomeScreen.this, LogInStudentActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });



    }


}