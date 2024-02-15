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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sp.madproject.R;

public class VolunteerProfileFragment extends Fragment {
    private Button editNamePasswordAvatarButton;
    private Button editFrameButton;
    public VolunteerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_profile, container, false);

        String rank = "Veteran";
        int exp = 69;

        TextView rankView = view.findViewById(R.id.volun_current_rank);
        rankView.setText(rank);

        TextView expView = view.findViewById(R.id.volun_current_exp);
        expView.setText(String.valueOf(100 - exp));
        final ProgressBar progressbar = (ProgressBar) view.findViewById(R.id.volun_exp_bar);
        progressbar.setProgress((int) exp);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.GONE);
        }

        editNamePasswordAvatarButton = view.findViewById(R.id.volun_edit_name_password);
        editNamePasswordAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), edit_profile.class);
                startActivity(intent);
            }
        });

        editFrameButton = view.findViewById(R.id.volun_edit_avatar_frame);
        editFrameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), edit_avatar_frame.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }
}