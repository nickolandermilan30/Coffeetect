package com.example.coffeetech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



        // Hide the action bar (app bar or title bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        imageSlider = findViewById(R.id.imageSlider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.img1_cam,    ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img2_upload, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img3_image,  ScaleTypes.FIT));


        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Find the "Next" ImageButton by its ID
        ImageButton btnNext1 = findViewById(R.id.home);
        ImageButton btnNext2 = findViewById(R.id.leaf);
        ImageButton btnNext3 = findViewById(R.id.cam);
        ImageButton btnNext4 = findViewById(R.id.tree);
        ImageButton btnNext5 = findViewById(R.id.help);
        ImageButton btnNext6 = findViewById(R.id.monthly);

        // Set an OnClickListener for the ImageButton
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity when the ImageButton is clicked
                Intent intent = new Intent(HomePage.this, HomePage.class);
                startActivity(intent);
                // Apply the transition animation
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, DiseasesInformation.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, CameraPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                                                                            }
        });

        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, TreeVisualization.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnNext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AboutUs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnNext6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MonthlyReport.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }
}


