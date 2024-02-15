package com.sp.madproject.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sp.madproject.R;

public class VolunteerMapFragment extends Fragment {
    private GPSTracker gpsTracker;
    private double latitude = 0.0d;
    private double longitude = 0.0d;
    private double eventLatitude;
    private double eventLongitude;

    // Constructor to receive event location
    public VolunteerMapFragment(double eventLatitude, double eventLongitude) {
        this.eventLatitude = eventLatitude;
        this.eventLongitude = eventLongitude;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gpsTracker = new GPSTracker(getActivity());

            if (gpsTracker.canGetLocation()) {
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                LatLng volunteer_position = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(volunteer_position).title("You!"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(volunteer_position));
            } else
                Toast.makeText(getActivity(), "Error gaining location.\nSome map functionalities may be disabled.", Toast.LENGTH_SHORT).show();
        }
    };

    public VolunteerMapFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volunteer_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gpsTracker.stopUsingGPS();
    }
}