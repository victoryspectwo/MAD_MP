package com.sp.madproject.organiser;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sp.madproject.ActionBarVisibilityListener;
import com.sp.madproject.R;

public class EventsFragment extends Fragment {

    private RecyclerView list;



    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        list = view.findViewById(R.id.avail_events); //recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setHasFixedSize(true);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        //list.setAdapter(adapter);
        ((ActionBarVisibilityListener) requireActivity()).setActionBarVisibility(true);
        return view;
    }

}

