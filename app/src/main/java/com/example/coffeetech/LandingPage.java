package com.example.coffeetech;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity {

    Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if Terms of Agreement has been accepted before
        if (isTermsAccepted()) {
            // If already accepted, redirect to Homepage
            startNextActivity();
            finish(); // Finish the current activity to prevent going back to it
        } else {
            setContentView(R.layout.activity_landing_page);

            // Hide the action bar (app bar or title bar)
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            Next = findViewById(R.id.next);

            Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTermsAndConditionsDialog();
                }
            });
        }
    }

    private boolean isTermsAccepted() {
        // Check if the Terms of Agreement has been accepted
        return getSharedPreferences("MyPrefs", MODE_PRIVATE).getBoolean("isTermsAccepted", false);
    }

    private void showTermsAndConditionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_terms_and_conditions, null);

        // Dapat mayroon kang id para sa CheckBox sa iyong dialog XML.
        CheckBox acceptCheckBox = dialogView.findViewById(R.id.checkBox);

        builder.setView(dialogView)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (acceptCheckBox.isChecked()) {
                            // Handle acceptance of terms here
                            // Save the acceptance state in SharedPreferences
                            saveTermsAcceptanceState(true);

                            // Redirect to Homepage or perform any desired action
                            startNextActivity();
                        } else {
                            // Show a message to inform the user that the checkbox needs to be checked.
                            Toast.makeText(LandingPage.this, "Please accept the Terms of Agreement.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle cancellation or refusal of terms here
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startNextActivity() {
        Intent intent = new Intent(LandingPage.this, HomePage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void saveTermsAcceptanceState(boolean isAccepted) {
        // Save the acceptance state in SharedPreferences
        getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().putBoolean("isTermsAccepted", isAccepted).apply();
    }
}
