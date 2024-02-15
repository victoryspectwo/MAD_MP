package com.sp.madproject.volunteer;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sp.madproject.R;

public class edit_profile extends AppCompatActivity {
    private ImageView headerImage;
    private FloatingActionButton imageAdd;
    private String imgURI;
    private ActivityResultLauncher<String[]> getContent;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        headerImage = findViewById(R.id.volun_headerImage);
        imageAdd = findViewById(R.id.volun_addImage);
        getContent = registerForActivityResult(
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

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use getContent here
                getContent.launch(new String[]{"image/*"});
            }
        });

        saveButton = findViewById(R.id.save_name_password_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity when Save button is clicked
                finish();
            }
        });
    }
}