package com.sp.madproject.volunteer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.madproject.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VolunteerEventDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VolunteerEventDescriptionFragment extends Fragment {

    // Keys for retrieving data from arguments bundle
    private static final String ARG_EVENT_TITLE = "event_title";
    private static final String ARG_EVENT_DESCRIPTION = "event_description";
    private static final String ARG_EVENT_IMAGE_URL = "event_image_url";
    private static final String ARG_EVENT_LOCATION = "event_location";
    private Button mapButton;
    private Button acceptButton;
    private Button declineButton;

    private String eventTitle;
    private String eventDescription;
    private String eventImageUrl;
    private String eventLocation;

    private String volunID;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;

    // Factory method to create a new instance of VolunteerEventDescriptionFragment
    public static VolunteerEventDescriptionFragment newInstance(String eventTitle, String eventDescription, String eventImageUrl, String eventLocation) {
        VolunteerEventDescriptionFragment fragment = new VolunteerEventDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_TITLE, eventTitle);
        args.putString(ARG_EVENT_DESCRIPTION, eventDescription);
        args.putString(ARG_EVENT_IMAGE_URL, eventImageUrl);
        args.putString(ARG_EVENT_LOCATION, eventLocation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        // Retrieve arguments and store data
        if (getArguments() != null) {
            eventTitle = getArguments().getString(ARG_EVENT_TITLE);
            eventDescription = getArguments().getString(ARG_EVENT_DESCRIPTION);
            eventImageUrl = getArguments().getString(ARG_EVENT_IMAGE_URL);
            eventLocation = getArguments().getString(ARG_EVENT_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_event_description, container, false);

        // Find TextViews
        ImageView eventImageView = view.findViewById(R.id.imageView3);
        TextView eventTitleTextView = view.findViewById(R.id.textView3);
        TextView eventDescriptionTextView = view.findViewById(R.id.textView6);

        volunID = mAuth.getCurrentUser().getUid();

        Glide.with(this)
                .load(eventImageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.organiser_icon))
                .into(eventImageView);

        // Set text of TextViews with event data
        eventTitleTextView.setText(eventTitle);
        eventDescriptionTextView.setText(eventDescription);
        mapButton = view.findViewById(R.id.button2);
        mapButton.setOnClickListener(new View.OnClickListener() {
            String[] numbers = eventLocation.split(",");

            double eventLatitude = Double.parseDouble(numbers[0]);
            double eventLongitude = Double.parseDouble(numbers[1]);
            @Override
            public void onClick(View v) {
                VolunteerMapFragment mapFragment = new VolunteerMapFragment(eventLatitude, eventLongitude);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.volunFragmentContainer, mapFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        acceptButton = view.findViewById(R.id.button3);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDocument();
                getActivity().onBackPressed();
            }
        });

        declineButton = view.findViewById(R.id.button4);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to previous fragment
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void createDocument() {
        // Create a new document with a generated ID
        Map<String, Object> data = new HashMap<>();
        data.put("eventTitle", eventTitle);
        data.put("eventDescription", eventDescription);
        data.put("volunteer", volunID);

        mStore.collection("acceptedEvents")
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            if (getContext() != null) {
                            }
                        } else {
                            if (getContext() != null) {
                                Toast.makeText(getContext(), "Failed to add event to Firestore", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        Toast.makeText(getContext(), "Event accepted and added to Firestore", Toast.LENGTH_SHORT).show();
    }
}