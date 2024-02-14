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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.madproject.organiser.OrganiserMainActivity;
import com.sp.madproject.R;
import com.sp.madproject.volunteer.VolunteerMain;

public class Login extends AppCompatActivity {

    TextInputEditText lgPassword, lgEmail;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String userType = getIntent().getStringExtra("userType");
        if (userType != null) {
            Log.d("UserType", "Logging in as: " + userType);
        }

        lgPassword = findViewById(R.id.lgPassword);
        lgEmail = findViewById(R.id.lgEmail);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(lgEmail.getText());
                password = String.valueOf(lgPassword.getText());


                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Valid email required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6){
                    lgPassword.setError("Password must be more than 6 characters");
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String loggedInUserId = mAuth.getCurrentUser().getUid();
                            String collectionName;
                            switch (userType) {
                                case "organiser":
                                    collectionName = "organisers";
                                    break;
                                case "volunteer":
                                    collectionName = "volunteers";
                                    break;
                                default:
                                    // Handle unexpected user types
                                    Log.e(TAG, "Unexpected user type: " + userType);
                                    Toast.makeText(Login.this, "Unexpected user type. Please try again.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    return;
                            }

                            DocumentReference userRef = mStore.collection(collectionName).document(loggedInUserId);
                            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            String loggedInUserType = document.getString("type");
                                                // Continue with the login process
                                                if ("organiser".equals(loggedInUserType)) {
                                                    Toast.makeText(Login.this, "Welcome back, organiser!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), OrganiserMainActivity.class));
                                                } else if ("volunteer".equals(loggedInUserType)) {
                                                    Toast.makeText(Login.this, "Welcome back, volunteer!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), VolunteerMain.class));
                                                } else if (!loggedInUserType.equals(userType)) {
                                                    // User is attempting to log in as the opposite user type
                                                    Toast.makeText(Login.this, "You are not allowed to log in as the opposite user type.", Toast.LENGTH_SHORT).show();
                                                    mAuth.signOut(); // Sign out the user
                                            }
                                        } else {
                                            Log.d(TAG, "User document does not exist");
                                            mAuth.signOut();
                                            Toast.makeText(Login.this, "Login failed. Check your login details, or if your email is registered elsewhere.", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    } else {
                                        Log.e(TAG, "Error getting user document: ", task.getException());
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Login failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

    }

    //can be used as remember me option.

    public void onSignUpTextClick(View view){
        Intent intent = new Intent(this, Signup.class);
        intent.putExtra("userType", getIntent().getStringExtra("userType"));
        startActivity(intent);
    }
    public void navigateToUserSelect(View view){
        Intent intent = new Intent(this, UserSelectActivity.class);
        startActivity(intent);
    }
}