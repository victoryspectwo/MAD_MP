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

public class VolunteerRankingFragment extends Fragment {
    private TabHost host;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_volunteer_ranking, null);

        host = view.findViewById(R.id.volunteer_ranking_tabHost);
        host.setup();

        //Available Tab
        TabHost.TabSpec spec = host.newTabSpec("Global");
        spec.setContent(R.id.global_tab);
        spec.setIndicator("Global");
        host.addTab(spec);

        //Available Tab
        spec = host.newTabSpec("Local");
        spec.setContent(R.id.local_tab);
        spec.setIndicator("Local");
        host.addTab(spec);

        //Active Tab
        spec = host.newTabSpec("Friends");
        spec.setContent(R.id.friends_tab);
        spec.setIndicator("Friends");
        host.addTab(spec);
        host.setCurrentTab(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volunteer_event, container, false);
    }
}