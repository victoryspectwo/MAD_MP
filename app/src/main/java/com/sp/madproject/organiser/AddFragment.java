package com.sp.madproject.organiser;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.sp.madproject.ActionBarVisibilityListener;
import com.sp.madproject.R;
import com.sp.madproject.volunteer.GPSTracker;


public class AddFragment extends Fragment {

    private ImageView headerImage;
    private ImageView iv_qr;
    private FloatingActionButton imageAdd;
    private TextInputLayout titleLayout;
    private TextInputEditText eventTitle;
    private TextInputLayout descLayout;
    private TextInputEditText eventDesc;
    private TextInputLayout locationLayout;
    private TextInputEditText eventLocation;

    private Button getLocationButton;
    private Button submitButton;

    private String imgURI;

    public AddFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        //TextInputEditTexts
        eventTitle = view.findViewById(R.id.eventTitle);
        eventDesc = view.findViewById(R.id.eventDesc);
        eventLocation = view.findViewById(R.id.eventLocation);

        //TextInputLayouts
        titleLayout = view.findViewById(R.id.titleLayout);
        descLayout = view.findViewById(R.id.descLayout);
        locationLayout = view.findViewById(R.id.locationLayout);

        headerImage = view.findViewById(R.id.headerImage);
        imageAdd = view.findViewById(R.id.addImage);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    getContent.launch(new String[]{"image/*"});
                }
            }
        });

        getLocationButton = view.findViewById(R.id.get_location_button);
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gpsTracker = new GPSTracker(getActivity());

                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    eventLocation.setText(String.valueOf(latitude) + ", " + String.valueOf(longitude));
                } else
                    Toast.makeText(getActivity(), "Error gaining location.", Toast.LENGTH_SHORT).show();
            }
        });


        submitButton = view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_image);
                iv_qr = dialog.findViewById(R.id.dialog_image_view);

                generateQR(); // Move the method call after iv_qr initialization

                Button closeButton = dialog.findViewById(R.id.close_dialog_button);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View r) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        ((ActionBarVisibilityListener) requireActivity()).setActionBarVisibility(false);
        return view;
    }


    ActivityResultLauncher<String[]> getContent = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        headerImage.setImageURI(result);
                        imgURI = result.toString(); //the image has now been saved as a string
                    }
                }
            });

    @Override
    public void onResume() {
        super.onResume();
        // clunky navbar readjustment
        ((ActionBarVisibilityListener) requireActivity()).setActionBarVisibility(false);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    private void generateQR() {
        String text = "tgyh"; //edit_input.getText().toString().trim();
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 1200, 1200);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            iv_qr.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}