package com.example.coffeetech;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class ChartDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_detail);

        ImageView imageView = findViewById(R.id.detailImageView);

        // Retrieve the image path from the intent
        String imagePath = getIntent().getStringExtra("imagePath");

        if (imagePath != null) {
            // Load the image into the ImageView
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        }
    }
}
