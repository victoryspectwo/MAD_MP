package com.sp.madproject.organiser;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sp.madproject.ActionBarVisibilityListener;
import com.sp.madproject.R;

import java.util.ArrayList;

public class EventsFragment extends Fragment {

    RecyclerView organiserRecyclerView;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    EventAdapter adapter;
    ArrayList<OrgEvents> orgEventArrayList;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ((ActionBarVisibilityListener) requireActivity()).setActionBarVisibility(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        organiserRecyclerView = view.findViewById(R.id.posted_events); //recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        organiserRecyclerView.setHasFixedSize(true);
        organiserRecyclerView.setLayoutManager(layoutManager);
        organiserRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Attach adapter to RecyclerView

        orgEventArrayList = new ArrayList<>();
        adapter = new EventAdapter(getContext(), orgEventArrayList);
        organiserRecyclerView.setAdapter(adapter);
        // Call method to listen for Firestore events
        eventChangeListener();

        return view;
    }

    private void eventChangeListener(){
        String currentOrganiserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Query the events collection for events published by the current organiser
        mStore.collection("events")
                .orderBy("event_title", Query.Direction.ASCENDING)
                .whereEqualTo("organiser", currentOrganiserId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){



                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){

                            if(dc.getType() == DocumentChange.Type.ADDED){
                                // Add document data to ArrayList
                                orgEventArrayList.add(dc.getDocument().toObject(OrgEvents.class));

                            }
                            adapter.notifyDataSetChanged();// Notify adapter that dataset has changed
                        }
                    }
                });
    }

}

