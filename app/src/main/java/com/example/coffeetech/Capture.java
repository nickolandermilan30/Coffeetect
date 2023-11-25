package com.example.coffeetech;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.coffeetech.ml.Dataset6;
import com.google.common.util.concurrent.ListenableFuture;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Capture extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private PreviewView previewView;
    private TextView diseaseTextView;

    private String currentResult = "";

    private String[] classes = {
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

    private Map<String, Float> confidenceThresholds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        // Create and set OnClickListener for the custom dialog button
        ImageButton customDialogButton = findViewById(R.id.customDialogButton); // Replace with your actual button ID
        customDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        previewView = findViewById(R.id.previewView);
        diseaseTextView = findViewById(R.id.diseaseTextView);


        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

        // Initialize confidence thresholds for each class
        confidenceThresholds = new HashMap<>();
        confidenceThresholds.put("Cercospora Critical", 0.8f);
        confidenceThresholds.put("Cercospora Moderate", 0.7f);
        confidenceThresholds.put("Cercospora Mild", 0.6f);
        confidenceThresholds.put("Healthy Leaf", 0.75f);
        confidenceThresholds.put("Leaf Miner Critical", 0.85f);
        confidenceThresholds.put("Leaf Miner Mild", 0.7f);
        confidenceThresholds.put("Leaf Miner Moderate", 0.65f);
        confidenceThresholds.put("Leaf Rust Critical", 0.9f);
        confidenceThresholds.put("Leaf Rust Mild", 0.8f);
        confidenceThresholds.put("Leaf Rust Moderate", 0.75f);
        confidenceThresholds.put("Phoma Critical", 0.85f);
        confidenceThresholds.put("Phoma Mild", 0.75f);
        confidenceThresholds.put("Phoma Moderate", 0.7f);
        confidenceThresholds.put("Sooty Mold Critical", 0.8f);
        confidenceThresholds.put("Sooty Mold Mild", 0.7f);
        confidenceThresholds.put("Sooty Mold Moderate", 0.6f);

    }
    private void showCustomDialog() {
        // Create the custom dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        // Set up the OK button
        Button okButton = dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();

            }
        });

        dialog.show();
    }



    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Log.d("Camera", "Camera initialized successfully");
    }


    private void captureImage() {
        if (imageCapture != null) {
            File photoFile = createImageFile();
            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(photoFile).build();

            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            new Thread(() -> {
                                Bitmap capturedBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                                classifyCapturedImage(capturedBitmap);
                            }).start();
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            exception.printStackTrace();
                        }
                    });
        }
    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void classifyCapturedImage(Bitmap image) {

        try {
            Dataset6 model = Dataset6.newInstance(getApplicationContext());

            int modelInputSize = 32;

            Bitmap resizedImage = Bitmap.createScaledBitmap(image, modelInputSize, modelInputSize, true);

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, modelInputSize, modelInputSize, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * modelInputSize * modelInputSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[modelInputSize * modelInputSize];
            resizedImage.getPixels(intValues, 0, resizedImage.getWidth(), 0, 0, resizedImage.getWidth(), resizedImage.getHeight());
            int pixel = 0;

            for (int i = 0; i < modelInputSize; i++) {
                for (int j = 0; j < modelInputSize; j++) {
                    if (pixel < intValues.length) {
                        int val = intValues[pixel++];
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                    } else {
                        pixel = intValues.length - 1;
                    }
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            Dataset6.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            for (int i = 0; i < confidences.length; i++) {
                Log.d("Confidence", classes[i] + ": " + confidences[i]);
            }

            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String predictedClass = classes[maxPos];

            if (maxConfidence >= confidenceThresholds.get(predictedClass)) {
                final int finalMaxPos = maxPos;
                runOnUiThread(() -> diseaseTextView.setText(predictedClass));

                currentResult = predictedClass;

                openResultActivity(resizedImage);
            } else {
                runOnUiThread(() -> diseaseTextView.setText("Result below confidence threshold"));
            }

            model.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openResultActivity(Bitmap image) {
        if (!currentResult.isEmpty()) {
            Intent intent = new Intent(this, Result_Activity2.class);
            intent.putExtra("result", currentResult);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            intent.putExtra("image", byteArray);

            startActivity(intent);
        }
    }

    public void onCaptureButtonClick(View view) {
        captureImage();
    }
}
