package com.sp.madproject.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.sp.madproject.R;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class edit_avatar_frame extends AppCompatActivity {

    private de.hdodenhof.circleimageview.CircleImageView profileImageView;
    private de.hdodenhof.circleimageview.CircleImageView avatarImageView;
    private Spinner imageSpinner;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_avatar_frame);

        profileImageView = findViewById(R.id.volun_edit_avatar);
        avatarImageView = findViewById(R.id.volun_edit_avatar_frame);
        imageSpinner = findViewById(R.id.volun_edit_spinner);
        saveButton = findViewById(R.id.save_avatar_frame_button);

        // Define the array of options based on your variable
        String[] options = new String[0];
        switch (5) {
            default:
            case 0:
                options = new String[]{"Red"};
                break;
            case 1:
                options = new String[]{"Red", "Orange"};
                break;
            case 2:
                options = new String[]{"Red", "Orange", "Yellow"};
                break;
            case 3:
                options = new String[]{"Red", "Orange", "Yellow", "Green"};
                break;
            case 4:
                options = new String[]{"Red", "Orange", "Yellow", "Green", "Blue"};
                break;
            case 5:
                options = new String[]{"Red", "Orange", "Yellow", "Green", "Blue", "Purple"};
                break;
        }

        // Populate spinner with filtered image options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);

        // Set listener for spinner item selection
        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Load image based on selected number
                loadImage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity when Save button is clicked
                finish();
            }
        });

        // Initialize Glide and load placeholder image
        Glide.with(this).load(R.drawable.placeholder).into(profileImageView);
    }

    private void loadImage(int position) {
        // Load image based on position
        switch (position) {
            case 0:
                avatarImageView.setBackgroundResource(R.drawable.avatar_frame_red);
                break;
            case 1:
                avatarImageView.setBackgroundResource(R.drawable.avatar_frame_orange);
                break;
            case 2:
                avatarImageView.setBackgroundResource(R.drawable.avatar_frame_yellow);
                break;
            case 3:
                avatarImageView.setBackgroundResource(R.drawable.avatar_frame_green);
                break;
            case 4:
                avatarImageView.setBackgroundResource(R.drawable.avatar_frame_blue);
                break;
            case 5:
                avatarImageView.setBackgroundResource(R.drawable.avatar_frame_purple);
                break;
            default:
                break;
        }
    }
}