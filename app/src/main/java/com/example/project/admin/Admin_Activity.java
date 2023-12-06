package com.example.project.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.project.admin_login.LogOut;
import com.example.project.admin_login.Login;
import com.example.project.R;
import com.example.project.teacher.Search_Teacher;
import com.example.project.teacher.Teacher_Display;
import com.example.project.teacher.Add_Teacher;
import com.example.project.teacher.Del_Teacher;
import com.google.android.material.navigation.NavigationView;

public class Admin_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set the hamburger icon color to white
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));

        // Load the default fragment
        loadFrag(new Login(), 0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Handle navigation only if the user is logged in
                if (id == R.id.add) {
                    loadFrag(new Add(), 1);
                } else if (id == R.id.edit) {
                    loadFrag(new Edit(), 1);
                } else if (id == R.id.delete) {
                    loadFrag(new Delete(), 1);
                } else if (id == R.id.search) {
                    loadFrag(new Search(), 1);
                } else if (id == R.id.teacher) {
                    loadFrag(new Add_Teacher(), 1);
                } else if (id == R.id.del) {
                    loadFrag(new Del_Teacher(), 1);
                } else if (id == R.id.logout) {
                    loadFrag(new LogOut(), 1);
                } else if (id == R.id.search1) {
                    loadFrag(new Search_Teacher(), 1);
                } else if (id == R.id.teach) {
                    loadFrag(new Teacher_Display(), 1);
                }

                // Close the drawer when any item is selected
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    // Override the onBackPressed method
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Get the current fragment
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

            // Check if the current fragment is the main fragment (Login)
            if (currentFragment instanceof Login) {
                // If it is the main fragment, do nothing (or handle it as needed)
                super.onBackPressed();
            } else {
                // If it is not the main fragment, load the main fragment
                loadFrag(new Login(), 0);
            }
        }
    }

    private void loadFrag(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0) {
            ft.replace(R.id.container, fragment);
        } else {
            ft.replace(R.id.container, fragment);
        }
        ft.commit();
    }
}
