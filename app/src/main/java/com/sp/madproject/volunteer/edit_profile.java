package com.sp.madproject.volunteer;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.madproject.R;

public class edit_profile extends AppCompatActivity {
    private ImageView headerImage;
    private FloatingActionButton imageAdd;
    private String imgURI;
    private ActivityResultLauncher<String[]> getContent;
    private Button saveButton;

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText passwordRepeatEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        usernameEditText = findViewById(R.id.volunteerName);
        passwordEditText = findViewById(R.id.volunteer_Password);
        passwordRepeatEditText = findViewById(R.id.volunteer_Password_Repeat);

        headerImage = findViewById(R.id.volun_headerImage);
        imageAdd = findViewById(R.id.volun_addImage);
        getContent = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null){
                            headerImage.setImageURI(result);
                            imgURI = result.toString(); //the image has now been saved as a string
                        }
                    }
                });

        // Fetch pre-existing data from Firestore and populate input fields
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        DocumentReference userRef = firestore.collection("volunteers").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Retrieve data from Firestore document
                    String username = documentSnapshot.getString("username");
                    String imgUri = documentSnapshot.getString("pfp");

                    // Set retrieved data to input fields
                    usernameEditText.setText(username);
                    if (imgUri != null && !imgUri.isEmpty()) {
                        headerImage.setImageURI(Uri.parse(imgUri));
                        imgURI = imgUri;
                    }
                } else {
                    Log.d("EditProfileActivity", "No such document");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("EditProfileActivity", "Error fetching document", e);
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use getContent here
                getContent.launch(new String[]{"image/*"});
            }
        });

        saveButton = findViewById(R.id.save_name_password_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
                finish();
            }
        });
    }

    private void saveProfileData() {
        // Get username and password from input fields
        String username = usernameEditText.getText().toString(); // Retrieve username EditText text
        //String password = passwordEditText.getText().toString(); // Retrieve password EditText text
        //String checkpassword = passwordRepeatEditText.getText().toString();

        // Perform validation and update Firestore with username, password, and image URI
        if (username.isEmpty()) {
            // Handle empty username
            return;
        }

//        if (password.isEmpty() || !password.equals(checkpassword)) {
//            // Handle empty password
//            return;
//        }

        // Update Firestore with username, password, and image URI
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        DocumentReference userRef = firestore.collection("volunteers").document(userId);
        userRef.update("username", username, "pfp", imgURI)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data updated successfully
                        Toast.makeText(edit_profile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update data
                        Log.e("EditProfileActivity", "Error updating profile", e);
                        Toast.makeText(edit_profile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}