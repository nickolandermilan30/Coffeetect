package com.example.coffeetech;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LeafRust extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rust);

        // Hide the action bar (app bar or title bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Add back button functionality
        ImageButton backButton = findViewById(R.id.backButton2);
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
}