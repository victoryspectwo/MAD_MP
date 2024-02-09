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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sp.madproject.R;

public class VolunteerProfileFragment extends Fragment {
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.volunteer_edit_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.volun_edit_name_password) {
            intent = new Intent(requireActivity(), edit_profile.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}