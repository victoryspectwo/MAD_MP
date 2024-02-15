package com.sp.madproject.volunteer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.madproject.R;

public class VolunteerProfileFragment extends Fragment {

    private TextView usernameView;
    private TextView rankView;
    private TextView expView;
    private ProgressBar progressbar;
    private Button editNamePasswordAvatarButton;
    private Button editFrameButton;

    private String volunID;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;

    public VolunteerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_profile, container, false);

        // Initialize views
        rankView = view.findViewById(R.id.volun_current_rank);
        usernameView = view.findViewById(R.id.volun_name);
        expView = view.findViewById(R.id.volun_current_exp);
        progressbar = view.findViewById(R.id.volun_exp_bar);
        editNamePasswordAvatarButton = view.findViewById(R.id.volun_edit_name_password);
        editFrameButton = view.findViewById(R.id.volun_edit_avatar_frame);

        // Get current user's ID
        volunID = mAuth.getCurrentUser().getUid();

        // Get username from Firestore and set it to the appropriate TextView
        mStore.collection("volunteers").document(volunID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("username");
                            if (username != null) {
                                // Set the username to the appropriate TextView
                                usernameView.setText(username);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore error", "Error getting username", e);
                    }
                });

        // Other button click listeners...
        editNamePasswordAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), edit_profile.class);
                startActivity(intent);
            }
        });

        editFrameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), edit_avatar_frame.class);
                startActivity(intent);
            }
        });

        return view;
    }
}