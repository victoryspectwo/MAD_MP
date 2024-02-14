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
import com.sp.madproject.organiser.OrganiserMainActivity;
import com.sp.madproject.R;
import com.sp.madproject.volunteer.VolunteerMain;

public class Login extends AppCompatActivity {

    TextInputEditText lgPassword, lgEmail;
    Button loginButton;
    ProgressBar progressBar;
    FirebaseAuth mAuth;


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
                        if (task.isSuccessful()){
                            if("organiser".equals(userType)){
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Login.this, "Welcome back, organiser!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), OrganiserMainActivity.class));
                            } else if ("volunteer".equals(userType)) {
                                Toast.makeText(Login.this, "Welcome back, volunteer!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), VolunteerMain.class));
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Login failed." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

    }

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