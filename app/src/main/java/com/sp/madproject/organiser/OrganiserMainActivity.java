package com.sp.madproject.organiser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.sp.madproject.ActionBarVisibilityListener;
import com.sp.madproject.R;
import com.sp.madproject.organiser.AddFragment;
import com.sp.madproject.organiser.EventsFragment;
import com.sp.madproject.organiser.MapsFragment;
import com.sp.madproject.startup.Login;

import android.view.Menu;
import android.widget.ImageButton;
import android.view.MenuItem;


public class OrganiserMainActivity extends AppCompatActivity implements ActionBarVisibilityListener {

    //User type selection
    private ImageButton organiserButton;
    private ImageButton volunteerButton;
    private BottomNavigationView navView;

    private AddFragment addFragment;
    private EventsFragment eventsFragment;
    private MapsFragment mapsFragment;
    private int bottomSelectedMenu = R.id.addEvents;

    private RequestQueue queue;
    private FragmentManager fragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_main);

        navView = findViewById(R.id.organiserNavigationView);
        navView.setOnItemSelectedListener(menuSelected);
        addFragment = new AddFragment();
        eventsFragment = new EventsFragment();
        mapsFragment = new MapsFragment();
    }

    @Override
    protected void onStart(){
        navView.setSelectedItemId(R.id.addEvents); //set this as the default page on startup
        super.onStart();
    }

    NavigationBarView.OnItemSelectedListener menuSelected = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            invalidateOptionsMenu();
            if (id == R.id.addEvents){
                fragmentManager.beginTransaction()
                        .replace(R.id.organiserFragmentContainer, addFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            else if (id == R.id.currentEvents){
                fragmentManager.beginTransaction()
                        .replace(R.id.organiserFragmentContainer, eventsFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

            } else if (id == R.id.getMap){
                fragmentManager.beginTransaction()
                        .replace(R.id.organiserFragmentContainer, mapsFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
            return false;
        }
    };

    public void setActionBarVisibility(boolean visible){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            if(visible){
                actionBar.show();
            } else {
                actionBar.hide();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            //Add maybe a fleeting screen here
            //Or set a flag
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}