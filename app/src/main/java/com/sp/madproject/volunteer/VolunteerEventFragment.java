package com.sp.madproject.volunteer;

import static com.sp.madproject.organiser.AddFragment.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
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

public class VolunteerEventFragment extends Fragment implements VolunAvailableEventAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private VolunAvailableEventAdapter adapter;
    private VolunActiveEventAdapter adapter2;
    private ArrayList<VolunAvailableEvent> volunAvailableEventArrayList;
    private ArrayList<VolunActiveEvent> volunActiveEventArrayList;
    private TabHost host;
    private String volunID;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_event, container, false);

        mAuth = FirebaseAuth.getInstance();
        volunID = mAuth.getCurrentUser().getUid();

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.volun_available_event);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView2 = view.findViewById(R.id.volun_active_event);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        mStore = FirebaseFirestore.getInstance();
        volunAvailableEventArrayList = new ArrayList<VolunAvailableEvent>();
        volunActiveEventArrayList = new ArrayList<VolunActiveEvent>();
        adapter = new VolunAvailableEventAdapter(getContext(), volunAvailableEventArrayList, this);
        adapter2 = new VolunActiveEventAdapter(getContext(), volunActiveEventArrayList, this);

        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);

        // Call method to listen for Firestore events
        eventChangeListener();
        eventChangeListener2();

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

    private void eventChangeListener2() {
        mStore.collection("acceptedEvents")
                .whereEqualTo("volunteer", volunID).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc1 : value.getDocumentChanges()){

                            if (dc1.getType() == DocumentChange.Type.ADDED){

                                volunActiveEventArrayList.add(dc1.getDocument().toObject(VolunActiveEvent.class));
                            }

                            adapter2.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(VolunAvailableEvent item) {
        VolunteerEventDescriptionFragment fragment = VolunteerEventDescriptionFragment.newInstance(item.getEvent_title(), item.getEvent_desc(), item.getEvent_img(), item.getEvent_location());
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.volunFragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}