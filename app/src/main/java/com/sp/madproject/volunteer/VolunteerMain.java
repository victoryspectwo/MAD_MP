package com.sp.madproject.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.sp.madproject.R;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class VolunteerMain extends AppCompatActivity {
    private DrawerLayout volunteerDrawerLayout;
    private Toolbar volunteerToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private BottomNavigationView navView;
    private VolunteerEventFragment volunteerEventFragment;
    private VolunteerMapFragment volunteerMapFragment;
    private VolunteerRankingFragment volunteerRankingFragment;
    private VolunteerScanFragment volunteerScanFragment;
    private int bottomSelectedMenu = R.id.volun_event;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_main);
        navView = findViewById(R.id.bottomNavigationView);
        navView.setOnItemSelectedListener(menuSelected);

        volunteerEventFragment = new VolunteerEventFragment();
        volunteerMapFragment = new VolunteerMapFragment();
        volunteerRankingFragment = new VolunteerRankingFragment();
        volunteerScanFragment = new VolunteerScanFragment();

        volunteerToolbar = findViewById(R.id.volunteer_toolbar);
        setSupportActionBar(volunteerToolbar);

        volunteerDrawerLayout = findViewById(R.id.volunteer_drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, volunteerDrawerLayout, R.string.nav_open, R.string.nav_close);
        volunteerDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.volunteer_nav_view);
        navigationView.setNavigationItemSelectedListener(navSelected);
    }

    @Override
    protected void onStart() {
        navView.setSelectedItemId(R.id.volun_event);
        invalidateOptionsMenu();
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (volunteerDrawerLayout.isDrawerOpen(navigationView)) {
                    volunteerDrawerLayout.closeDrawer(navigationView);
                } else {
                    volunteerDrawerLayout.openDrawer(navigationView);
                }
                return true;
        } return super.onOptionsItemSelected(item);
    }

    NavigationBarView.OnItemSelectedListener menuSelected = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            invalidateOptionsMenu();
            if (id == R.id.volun_event) {
                fragmentManager.beginTransaction()
                        .replace(R.id.volunFragmentContainer, volunteerEventFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                return true;
            } else if (id == R.id.volun_map) {
                fragmentManager.beginTransaction()
                        .replace(R.id.volunFragmentContainer, volunteerMapFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            else if (id == R.id.volun_ranking) {
                fragmentManager.beginTransaction()
                        .replace(R.id.volunFragmentContainer, volunteerRankingFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            else if (id == R.id.volun_scan) {
                fragmentManager.beginTransaction()
                        .replace(R.id.volunFragmentContainer, volunteerScanFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            return false;
        }
    };

    NavigationView.OnNavigationItemSelectedListener navSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.nav_exit) {
                finish();
            } else {
                Fragment fragment = null;
                if (id == R.id.nav_home) {
                    fragment =  new VolunteerEventFragment();
                } else if (id == R.id.nav_profile) {
                    fragment = new VolunteerProfileFragment();
                } else if (id == R.id.nav_about) {
                    fragment = new AboutFragment();
                }
                fragmentManager.beginTransaction().replace(R.id.volunFragmentContainer, fragment).commit();
                volunteerDrawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        }
    };
}