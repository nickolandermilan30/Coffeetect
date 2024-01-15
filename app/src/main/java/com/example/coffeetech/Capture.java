package com.example.coffeetech;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    private static final String PREF_HIGHLIGHTED_BUTTON = "highlighted_button";

    private SharedPreferences sharedPreferences;
    private Dialog dialog;

    private Map<String, Boolean> buttonStates;

    private Button lastClickedButton;

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
        // Use the Application Context for SharedPreferences
        sharedPreferences = SavedPreference.getSharedPreferences();
        buttonStates = getButtonStates();
        // Other initializations and setup
        setContentView(R.layout.activity_capture);

        // Create and set OnClickListener for the custom dialog button
        ImageButton customDialogButton = findViewById(R.id.customDialogButton);
        customDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });


        // Create and set OnClickListener for the custom dialog button 2
        ImageButton customDialogButton2 = findViewById(R.id.customDialogButton2);
        customDialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog2();
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



    private void showCustomDialog2() {
        // Create the custom dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog2);

        // Find all buttons by their IDs
        Button buttonT1 = dialog.findViewById(R.id.button_t1);
        Button buttonT2 = dialog.findViewById(R.id.button_t2);
        Button buttonT3 = dialog.findViewById(R.id.button_t3);
        Button buttonT4 = dialog.findViewById(R.id.button_t4);
        Button buttonT5 = dialog.findViewById(R.id.button_t5);
        Button buttonT6 = dialog.findViewById(R.id.button_t6);
        Button buttonT7 = dialog.findViewById(R.id.button_t7);
        Button buttonT8 = dialog.findViewById(R.id.button_t8);
        Button buttonT9 = dialog.findViewById(R.id.button_t9);
        Button buttonT10 = dialog.findViewById(R.id.button_t10);

        // Set initial states of the buttons
        setButtonState(buttonT1);
        setButtonState(buttonT2);
        setButtonState(buttonT3);
        setButtonState(buttonT4);
        setButtonState(buttonT5);
        setButtonState(buttonT6);
        setButtonState(buttonT7);
        setButtonState(buttonT8);
        setButtonState(buttonT9);
        setButtonState(buttonT10);

        // Restore the last clicked button if it was previously highlighted
        String lastButtonId = sharedPreferences.getString(PREF_HIGHLIGHTED_BUTTON, "");
        if (!lastButtonId.isEmpty()) {
            int id = getResources().getIdentifier(lastButtonId, "id", getPackageName());
            lastClickedButton = dialog.findViewById(id);
            if (lastClickedButton != null) {
                highlightButton(lastClickedButton);
            }
        }

        // Set click listeners for each button
        buttonT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT1);
            }
        });

        buttonT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT2);
            }
        });
        buttonT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT3);
            }
        });
        buttonT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT4);
            }
        });
        buttonT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT5);
            }
        });
        buttonT6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT6);
            }
        });
        buttonT7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT7);
            }
        });
        buttonT8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT8);
            }
        });
        buttonT9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT9);
            }
        });
        buttonT10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButtonState(buttonT10);
            }
        });
        // Show the dialog
        dialog.show();
        // Restore the button states when the dialog is shown
        restoreButtonStates();

        // Set up the doneButton click listener after the dialog is shown
        Button doneButton = dialog.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the button states before dismissing the dialog
                saveButtonStates();
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
    }

    private void setButtonState(Button button) {
        boolean isHighlighted = buttonStates.getOrDefault(getButtonKey(button), false);
        if (isHighlighted) {
            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.brown));
        } else {
            button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));
        }
    }

    private void toggleButtonState(Button button) {
        boolean isHighlighted = buttonStates.getOrDefault(getButtonKey(button), false);
        buttonStates.put(getButtonKey(button), !isHighlighted);
        setButtonState(button);
        saveButtonStates();
    }

    private String getButtonKey(Button button) {
        return String.valueOf(button.getId());
    }

    // Method to save button states using Application Context for SharedPreferences
    private void saveButtonStates() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<String, Boolean> entry : buttonStates.entrySet()) {
            editor.putBoolean(PREF_HIGHLIGHTED_BUTTON + entry.getKey(), entry.getValue());
        }
        editor.apply();
    }


    private Map<String, Boolean> getButtonStates() {
        Map<String, Boolean> states = new HashMap<>();

        states.put("button_t1", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t1", false));
        states.put("button_t2", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t2", false));
        states.put("button_t3", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t3", false));
        states.put("button_t4", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t4", false));
        states.put("button_t5", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t5", false));
        states.put("button_t6", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t6", false));
        states.put("button_t7", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t7", false));
        states.put("button_t8", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t8", false));
        states.put("button_t9", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t9", false));
        states.put("button_t10", sharedPreferences.getBoolean(PREF_HIGHLIGHTED_BUTTON + "button_t10", false));
        return states;
    }

    private void restoreButtonStates() {
        sharedPreferences = getApplicationContext().getSharedPreferences("ButtonStates", Context.MODE_PRIVATE);
        for (Map.Entry<String, Boolean> entry : buttonStates.entrySet()) {
            boolean isHighlighted = sharedPreferences.getBoolean(entry.getKey(), false);
            int buttonId = getResources().getIdentifier(entry.getKey(), "id", getPackageName());
            Button button = dialog.findViewById(buttonId);
            if (button != null) {
                if (isHighlighted) {
                    // Reapply the highlighted state to the buttons
                    setButtonState(button);
                }
            }
        }
        // Set up the done button
        Button doneButton = dialog.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the button states before dismissing the dialog
                saveButtonStates();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });
    }

    // Method to highlight the clicked button
    // Inside highlightButton() method
    private void highlightButton(Button button) {
        // Change the background color of the clicked button to green
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green));

        // Save the ID of the clicked button to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getButtonKey(button), true); // Save the highlighted state
        editor.apply();

        // Update the last clicked button reference
        lastClickedButton = button;

        // Set up the done button
        Button doneButton = dialog.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the button states before dismissing the dialog
                saveButtonStates();

                // Dismiss the dialog
                dialog.dismiss();
            }
        });
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
            Intent intent = new Intent(this, ResultActivity2.class);
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

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Button getLastClickedButton() {
        return lastClickedButton;
    }

    public void setLastClickedButton(Button lastClickedButton) {
        this.lastClickedButton = lastClickedButton;
    }
}
