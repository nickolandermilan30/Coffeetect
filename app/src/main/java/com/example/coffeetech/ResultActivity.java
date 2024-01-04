package com.example.coffeetech;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ResultActivity extends AppCompatActivity {

    Button backr, saveButton;
    private boolean isSaving = false;
    private boolean isConnected;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        TextView resultTextView = findViewById(R.id.resultTextView);
        backr = findViewById(R.id.capagain);
        saveButton = findViewById(R.id.saveButton);
        ImageView imageView = findViewById(R.id.resultImageView); // Add ImageView

        // Retrieve the result from the Intent
        String result = getIntent().getStringExtra("result");
        String imagePath = getIntent().getStringExtra("imagePath");


        // Set the result in the TextView
        resultTextView.setText("" + result);

        // Retrieve the image byte array from the Intent
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        if (byteArray != null) {
            // Convert the byte array to a Bitmap and display it in the ImageView
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(imageBitmap);
        }

        backr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, TreeVisualization.class);
                intent.putExtra("diseaseName", "SampleDiseaseName");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        // ...

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSaving) {
                    isSaving = true;

                    // Check internet connection
                    if (isConnectedToInternet()) {
                        // The device is connected to the internet, proceed with saving

                        // Retrieve the disease name from the Intent
                        String result = getIntent().getStringExtra("result");

                        // Retrieve the byte array from the intent
                        byte[] byteArray = getIntent().getByteArrayExtra("image");

                        // Convert the byte array back to a Bitmap
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

                        Bitmap imageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);


                        // Set the text for disease and severity
                        resultTextView.setText(result);

                        // Set the image in the ImageView
                        imageView.setImageBitmap(imageBitmap);

                        // Create a SavedResult object
                        SavedResult savedResult = new SavedResult(result, imageBitmap);

                        // Redirect to another activity (TreeVisualization)
                        Intent intent = new Intent(ResultActivity.this, CameraPage.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
// Check if `History` is full and add to the appropriate history list
                        int maxSavedResults = 10;
                        if (SavedResultsManager.getSavedResultsCount() < maxSavedResults) {
                            SavedResultsManager.addSavedResult(savedResult);
                        } else {

                            // Check if `History` is full and add to the appropriate history list
                            if (SavedResultsManager.getSavedResultsCount() < 10) {
                                SavedResultsManager.addSavedResult(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory2() < 10) {
                                SavedResultsManager.addSavedResultToHistory2(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory3() < 10) {
                                SavedResultsManager.addSavedResultToHistory3(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory4() < 10) {
                                SavedResultsManager.addSavedResultToHistory4(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory5() < 10) {
                                SavedResultsManager.addSavedResultToHistory5(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory6() < 10) {
                                SavedResultsManager.addSavedResultToHistory6(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory7() < 10) {
                                SavedResultsManager.addSavedResultToHistory7(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory8() < 10) {
                                SavedResultsManager.addSavedResultToHistory8(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory9() < 10) {
                                SavedResultsManager.addSavedResultToHistory9(savedResult);
                            } else if (SavedResultsManager.getSavedResultsCountHistory10() < 10) {
                                SavedResultsManager.addSavedResultToHistory10(savedResult);
                            } else {
                                // Show dialog alert if the number of saved results in all histories reaches 10
                                showMaxSavedResultsDialog();
                                isSaving = false;
                                return; // Do not proceed further
                            }
                        }

                        // Save the image to Firebase Storage
                        saveImageToFirebaseStorage(savedResult, imageBitmap);
                    } else {
                        // The device is not connected to the internet, show an alert dialog
                        showNoInternetDialog();
                        isSaving = false;
                    }
                }
            }

            private void showMaxSavedResultsDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                builder.setTitle("Maximum Saved Results Reached");
                builder.setMessage("You have reached the maximum limit of saved results (10). You can clear some results to save new ones.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
                builder.show();
            }

            private boolean isConnectedToInternet() {
                // Check internet connection
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                return connectivityManager.getActiveNetworkInfo() != null
                        && connectivityManager.getActiveNetworkInfo().isConnected();
            }

            private void showNoInternetDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                builder.setTitle("No Internet Connection");
                builder.setMessage("Please check your internet connection and try again.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
                builder.show();
            }
        });



        // Add back button functionality
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void saveImageToFirebaseStorage(SavedResult savedResult, Bitmap imageBitmap) {
        // Convert the Bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Retrieve the disease name from the Intent
        String result = getIntent().getStringExtra("result");

        // Sanitize the result string for Firebase Storage filename
        String sanitizedResult = result.replaceAll("[^a-zA-Z0-9_]", "_");

        // Generate a unique filename (e.g., using a timestamp)
        String filename = sanitizedResult + "_" + System.currentTimeMillis() + ".png";

        // Get a reference to the Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a reference to the folder where you want to save the image
        StorageReference folderReference = storage.getReference().child("images");

        // Create a reference to the specific image file
        StorageReference imageReference = folderReference.child(filename);

        // Upload the image to Firebase Storage
        UploadTask uploadTask = imageReference.putBytes(byteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the download URL for the uploaded image
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {
                        String imageUrl = downloadUrl.toString();

                        // Set the image URL in the saved result
                        savedResult.setImageUrl(imageUrl);

                        // You may want to save the savedResult object to a database or a list here
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure to upload the image
                Toast.makeText(ResultActivity.this, "Failed to save the image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
