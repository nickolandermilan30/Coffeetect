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

public class Homepage extends AppCompatActivity {

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

        slideModels.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img3, ScaleTypes.FIT));


        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Find the "Next" ImageButton by its ID
        ImageButton btnNext1 = findViewById(R.id.home);
        ImageButton btnNext2 = findViewById(R.id.leaf);
        ImageButton btnNext3 = findViewById(R.id.cam);
        ImageButton btnNext4 = findViewById(R.id.history);
        ImageButton btnNext5 = findViewById(R.id.mon);
        ImageButton btnNext6 = findViewById(R.id.cal);

        // Set an OnClickListener for the ImageButton
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity when the ImageButton is clicked
                Intent intent = new Intent(Homepage.this, Homepage.class);
                startActivity(intent);
                // Apply the transition animation

            }
        });

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Leaf.class);
                startActivity(intent);
            }
        });


        btnNext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Camera_page.class);
                startActivity(intent);
            }
        });

        btnNext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Folders.class);
                startActivity(intent);
            }
        });


        btnNext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Report.class);
                startActivity(intent);
            }
        });

        btnNext6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Calendar.class);
                startActivity(intent);
            }
        });



    }
}


