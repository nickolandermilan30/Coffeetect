package com.example.coffeetech;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
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

        ImageButton deleteImageButton = findViewById(R.id.deleteImageButton);
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build an AlertDialog for confirmation
                new AlertDialog.Builder(ChartDetailActivity.this)
                        .setTitle("Delete Yearly Report")
                        .setMessage("Are you sure you want to delete this yearly report?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Retrieve the image path from the intent
                                String imagePath = getIntent().getStringExtra("imagePath");

                                // Delete the image from both lists
                                deleteImageFromList(imagePath);

                                // Finish the activity
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked "No," do nothing
                            }
                        })
                        .show();
            }
        });


        // Find the Share ImageButton and set its click listener
        ImageButton shareImageButton = findViewById(R.id.shareImageButton);
        shareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the image path from the intent
                String imagePath = getIntent().getStringExtra("imagePath");

                // Get the Bitmap from the ImageView
                Bitmap bitmap = getBitmapFromImageView(imageView);

                // Share the image using an Intent
                shareImage(bitmap, imagePath);
            }
        });

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

    private void deleteImageFromList(String imagePath) {
        // Implement code to delete the image from the list in StorageActivity
        Intent intent = new Intent(ChartDetailActivity.this, StorageActivity.class);
        intent.putExtra("deleteImagePath", imagePath);
        startActivity(intent);

        // Implement code to delete the image from the list in ChartDetailActivity
        // For example, you can remove it from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(imagePath);
        editor.apply();

        // Delete the image file from storage
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
    }

    private Bitmap getBitmapFromImageView(ImageView imageView) {
        // Get the Bitmap from the ImageView
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private void shareImage(Bitmap bitmap, String imagePath) {
        // Save the bitmap to a temporary file
        File tempFile = saveBitmapTemporarily(bitmap);

        if (tempFile != null) {
            // Share the image using an Intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*"); // Set the MIME type to image
            // Uri.fromFile is deprecated, use FileProvider for Android 7.0 (Nougat) and above
            Uri imageUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", tempFile);

            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri); // Set the image URI as an extra

            // Grant temporary read permission to the content URI
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Start the activity with the share Intent
            startActivity(Intent.createChooser(shareIntent, "Share image using"));
        }
    }



    private File saveBitmapTemporarily(Bitmap bitmap) {
        try {
            // Save the bitmap to a temporary file
            File tempFile = File.createTempFile("shared_image", ".png", getCacheDir());
            tempFile.deleteOnExit();

            // Write the bitmap to the file
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new java.io.FileOutputStream(tempFile));

            return tempFile;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
