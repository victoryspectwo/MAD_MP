package com.sp.madproject.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.madproject.organiser.OrganiserMainActivity;
import com.sp.madproject.R;
import com.sp.madproject.volunteer.VolunteerMain;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private String userType;
    TextInputEditText signupUsername, signupPassword, signupEmail;
    Button signupButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    FirebaseFirestore mStore;
    String userID;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Retrieve user type from intent extras
        userType = getIntent().getStringExtra("userType");
        if (userType != null) {
            // Do something with the user type
            Log.d("UserType", "Signing up as: " + userType);
        }

        signupUsername = findViewById(R.id.edit_textusername);
        signupPassword = findViewById(R.id.edit_textpassword);
        signupEmail = findViewById(R.id.edit_textemail);
        signupButton = findViewById(R.id.signupButton);
        progressBar = findViewById(R.id.progressBar);

        // add phone number later
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        /*
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),OrganiserMainActivity.class));
            finish();
        }

         */

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, username, usertype;
                username = signupUsername.getText().toString();
                email = signupEmail.getText().toString();
                password = signupPassword.getText().toString();



                if (TextUtils.isEmpty(username)){
                    Toast.makeText(Signup.this, "Username required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Signup.this, "Valid email required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Signup.this, "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6){
                    signupPassword.setError("Password must be more than 6 characters");
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        /***NEED TO ACCOUNT FOR ORGANISER AND USER CASES ***/
                        if (task.isSuccessful()){
                            if("organiser".equals(userType)){
                                // Sign up success, update UI with the signed-in user's information
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = mStore.collection("organisers").document(userID);
                                Map<String, Object> organiser = new HashMap<>();
                                /**FIREBASE CAN'T ACCEPT AN EDITTEXT INPUT DIRECTLY**/
                                organiser.put("username", username);
                                organiser.put("email", email);
                                organiser.put("type", userType);
                                //document users deal with user signup details
                                Toast.makeText(Signup.this, "Organiser " + username + " created", Toast.LENGTH_SHORT).show();
                                documentReference.set(organiser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: User Profile is created for " + userID + ", " + userType + " type");
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), OrganiserMainActivity.class));
                            }
                            else if ("volunteer".equals(userType)){
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = mStore.collection("volunteers").document(userID);
                                Map<String, Object> volunteer = new HashMap<>();
                                /**FIREBASE CAN'T ACCEPT AN EDITTEXT INPUT DIRECTLY**/
                                volunteer.put("username", username);
                                volunteer.put("email", email);
                                volunteer.put("type", userType);
                                Toast.makeText(Signup.this, "Volunteer " + username +" created", Toast.LENGTH_SHORT).show();
                                //document users deal with user signup details
                                documentReference.set(volunteer).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: User Profile is created for " + userID + ", " + userType + " type");
                                    }
                                });
                                //document users deal with user signup details
                                startActivity(new Intent(getApplicationContext(), VolunteerMain.class));
                            }
                        } else {
                            Toast.makeText(Signup.this, "Signup failed. Please try again." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    public void onLoginTextClick(View view){
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("Logging in as:", userType);
        Log.d("UserType", "Logging in as " + userType);
        startActivity(intent);
    }

    public void navigateToUserSelect(View view){
        Intent intent = new Intent(this, UserSelectActivity.class);
        startActivity(intent);
    }
}
