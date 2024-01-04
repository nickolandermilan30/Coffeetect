package com.example.coffeetech;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChartDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_detail);

        ImageView imageView = findViewById(R.id.detailImageView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);

        // Retrieve the image path and date from the intent
        String imagePath = getIntent().getStringExtra("imagePath");
        String savedDate = getSavedDate(imagePath);

        if (imagePath != null) {
            // Load the image into the ImageView
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);

            // Display the saved date in the TextViews
            if (savedDate != null) {
                String[] dateTimeParts = formatDateTime(savedDate);

                // Ensure that we have both date and time parts
                if (dateTimeParts.length == 2) {
                    dateTextView.setText("Date: " + dateTimeParts[0]);
                    timeTextView.setText("Time: " + dateTimeParts[1]);
                }
            }
        }
    }

    private String getSavedDate(String imagePath) {
        // Retrieve the saved date from SharedPreferences using the image path as the key
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(imagePath, null);
    }


    private String[] formatDateTime(String rawDate) {
        // Parse the raw date string
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        SimpleDateFormat outputTimeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

        try {
            Date date = inputFormat.parse(rawDate);
            String formattedDate = outputDateFormat.format(date);
            String formattedTime = outputTimeFormat.format(date);

            return new String[]{formattedDate, formattedTime};
        } catch (ParseException e) {
            e.printStackTrace();
            return new String[]{rawDate, ""}; // Return raw date and an empty time string in case of an error
        }
    }
    
}