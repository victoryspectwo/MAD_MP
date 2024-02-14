package com.sp.madproject.organiser;

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

import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.madproject.ActionBarVisibilityListener;
import com.sp.madproject.R;


public class AddFragment extends Fragment {

    FirebaseAuth cmAuth;
    FirebaseFirestore mStore;
    private ImageView headerImage;
    private FloatingActionButton imageAdd;
    private TextInputLayout titleLayout;
    private TextInputEditText eventTitle;
    private TextInputLayout descLayout;
    private TextInputEditText eventDesc;
    private TextInputLayout locationLayout;
    private TextInputEditText eventLocation;

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

        submitButton = view.findViewById(R.id.submit_button);
        //submitButton.setOnClickListener();


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

}