package com.sp.madproject.startup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.sp.madproject.ActionBarVisibilityListener;
import com.sp.madproject.R;

public class UserSelectActivity extends AppCompatActivity implements ActionBarVisibilityListener {

    private ImageButton organiserSelect;
    private ImageButton volunteerSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection);
        volunteerSelect = findViewById(R.id.volunteerButton);
        organiserSelect = findViewById(R.id.organiserButton);

        volunteerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSelectActivity.this, Login.class);
                intent.putExtra("userType", "volunteer");
                startActivity(intent);
                Log.d("UserType", "User type: volunteer");
            }
        });

        organiserSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSelectActivity.this, Login.class);
                intent.putExtra("userType", "organiser");
                startActivity(intent);
                Log.d("UserType", "User type: organiser");
            }
        });

    }
    protected void onStart() {
        super.onStart();
        setActionBarVisibility(false); // Show ActionBar when the activity starts
    }

    @Override
    public void setActionBarVisibility(boolean visible) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (visible) {
                actionBar.show();
            } else {
                actionBar.hide();
            }
        }
    }

}