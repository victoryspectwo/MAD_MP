package com.sp.madproject.volunteer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sp.madproject.R;

public class VolunteerEventFragment extends Fragment {
    private TabHost host;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_volunteer_event, null);

        host = view.findViewById(R.id.volunteer_event_tabHost);
        host.setup();

        //Available Tab
        TabHost.TabSpec spec = host.newTabSpec("Available");
        spec.setContent(R.id.available_tab);
        spec.setIndicator("Available");
        host.addTab(spec);

        //Active Tab
        spec = host.newTabSpec("Active");
        spec.setContent(R.id.active_tab);
        spec.setIndicator("Active");
        host.addTab(spec);
        host.setCurrentTab(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volunteer_event, container, false);
    }
}