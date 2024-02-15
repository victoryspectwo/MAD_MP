package com.sp.madproject.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.madproject.R;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
                // Get the selected option from the spinner
                String selectedOption = (String) imageSpinner.getSelectedItem();

                // Get the URI of the selected option
                int drawableResourceId = getDrawableResourceId(selectedOption);
                String uri = "@drawable/" + getResources().getResourceEntryName(drawableResourceId);

                // Update the database with the URI
                updateDatabase(uri);

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

    private int getDrawableResourceId(String selectedOption) {
        // Map each option to its corresponding drawable resource ID
        switch (selectedOption) {
            case "Red":
                return R.drawable.avatar_frame_red;
            case "Orange":
                return R.drawable.avatar_frame_orange;
            case "Yellow":
                return R.drawable.avatar_frame_yellow;
            case "Green":
                return R.drawable.avatar_frame_green;
            case "Blue":
                return R.drawable.avatar_frame_blue;
            case "Purple":
                return R.drawable.avatar_frame_purple;
            default:
                return 0; // Return 0 or handle default case appropriately
        }
    }

    private void updateDatabase(String uri) {
        // Update the database with the URI
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        DocumentReference userRef = firestore.collection("volunteers").document(userId);
        userRef.update("frame_id", uri)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data updated successfully
                        Toast.makeText(edit_avatar_frame.this, "Avatar frame URI updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update data
                        Log.e("EditAvatarFrameActivity", "Error updating avatar frame URI", e);
                        Toast.makeText(edit_avatar_frame.this, "Failed to update avatar frame URI", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}