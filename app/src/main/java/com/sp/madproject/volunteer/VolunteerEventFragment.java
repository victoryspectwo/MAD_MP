package com.sp.madproject.volunteer;

import static com.sp.madproject.organiser.AddFragment.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sp.madproject.R;
import com.sp.madproject.organiser.EventAdapter;
import com.sp.madproject.organiser.OrgEvents;

import java.util.ArrayList;

public class VolunteerEventFragment extends Fragment {
    RecyclerView recyclerView;
    VolunAvailableEventAdapter adapter;
    ArrayList<VolunAvailableEvent> volunAvailableEventArrayList;
    private TabHost host;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_event, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.volun_available_event);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mStore = FirebaseFirestore.getInstance();
        volunAvailableEventArrayList = new ArrayList<VolunAvailableEvent>();
        adapter = new VolunAvailableEventAdapter(getContext(), volunAvailableEventArrayList);

        recyclerView.setAdapter(adapter);

        // Call method to listen for Firestore events
        eventChangeListener();

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

        host.setCurrentTab(0);

        return view;
    }

    private void eventChangeListener() {
        mStore.collection("events")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                volunAvailableEventArrayList.add(dc.getDocument().toObject(VolunAvailableEvent.class));
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}