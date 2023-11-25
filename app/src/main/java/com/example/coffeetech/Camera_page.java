package com.example.coffeetech;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.coffeetech.ml.Dataset6;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Camera_page extends AppCompatActivity {

    ImageButton camera, gallery,  home, leaf, cam, history, cal,  calbtn;
    ImageView imageView;
    TextView result;
    int imageSize = 32;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);

        // Hide the action bar (app bar or title bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        home = findViewById(R.id.home);
        leaf = findViewById(R.id.leaf);
        cam = findViewById(R.id.cam);
        history = findViewById(R.id.tree);
        cal = findViewById(R.id.monthly);
        calbtn = findViewById(R.id.ana);
        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);


        camera.setOnClickListener(new View.OnClickListener() {
             @Override
           public void onClick(View v) {
            Intent intent = new Intent(Camera_page.this, Capture.class);
          startActivity(intent);
                 overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
      }});



        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_page.this, HomePage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        leaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_page.this, Diseases_Information.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_page.this, Camera_page.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_page.this, Tree_Visualization.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_page.this, Monthly_Report.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        calbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_page.this, Monthly_Report.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }
    public void classifyImage(Bitmap image) {

        try {
            Dataset6 model = Dataset6.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;

            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);



            // Runs model inference and gets result.
            Dataset6.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {
                    "Cercospora Critical",
                    "Cercospora Moderate",
                    "Cercospora Mild",
                    "Healthy Leaf",
                    "Leaf Miner Critical",
                    "Leaf Miner Mild",
                    "Leaf Miner Moderate",
                    "Leaf Rust Critical",
                    "Leaf Rust Mild",
                    "Leaf Rust Moderate",
                    "Phoma Critical",
                    "Phoma Mild",
                    "Phoma Moderate",
                    "Sooty Mold Critical",
                    "Sooty Mold Mild",
                    "Sooty Mold Moderate",
            };

            result.setText(classes[maxPos]);

            // Create an Intent to start ResultActivity and pass the result and image
            Intent intent = new Intent(this, Result_Activity.class);
            intent.putExtra("result", classes[maxPos]); // Pass the classification result
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            intent.putExtra("image", byteArray); // Pass the image as a byte array
            startActivity(intent);

        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) { // Kapag mula sa camera o gallery
                if (data != null) {
                    if (data.getData() != null) { // Uri mula sa gallery
                        Uri dat = data.getData();
                        Bitmap image = null;
                        try {
                            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (image != null) {
                            imageView.setImageBitmap(image);

                            // Retrieve classification result from Intent
                            float[] classificationResult = data.getFloatArrayExtra("classificationResult");

                            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                            classifyImage(image);
                        } else {
                            // Handle the case where there was an issue loading the image from the gallery
                        }
                    } else if (data.getExtras() != null && data.getExtras().containsKey("data")) { // Bitmap mula sa camera
                        Bitmap image = (Bitmap) data.getExtras().get("data");
                        if (image != null) {
                            int dimension = Math.min(image.getWidth(), image.getHeight());
                            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                            imageView.setImageBitmap(image);

                            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                            classifyImage(image);
                        } else {
                            // Handle the case where the camera did not provide a valid image
                        }
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    }
