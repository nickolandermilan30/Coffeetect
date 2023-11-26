package com.example.coffeetech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class OtherTree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_tree);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        ImageButton home, leaf, cam, history, cal ;
        ImageButton t7, t8, t9, t10 ;
        EditText tr13, tr14, tr15, tr16;

        tr13 = findViewById(R.id.textView13);
        tr14 = findViewById(R.id.textView14);
        tr15 = findViewById(R.id.textView15);
        tr16 = findViewById(R.id.textView16);

        home = findViewById(R.id.home);
        leaf = findViewById(R.id.leaf);
        cam = findViewById(R.id.cam);
        history = findViewById(R.id.tree);
        cal = findViewById(R.id.monthly);


        t7 = findViewById(R.id.t7);
        t8 = findViewById(R.id.t8);
        t9 = findViewById(R.id.t9);
        t10 = findViewById(R.id.t10);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Retrieve and set the saved text for the first EditText
        String savedText13 = sharedPreferences.getString("savedText13", "");
        tr13.setText(savedText13);

        // Retrieve and set the saved text for another EditText (e.g., tr14)
        String savedText14 = sharedPreferences.getString("savedText14", "");
        tr14.setText(savedText14);

        // Retrieve and set the saved text for another EditText (e.g., tr15)
        String savedText15 = sharedPreferences.getString("savedText15", "");
        tr15.setText(savedText15);

        // Retrieve and set the saved text for another EditText (e.g., tr16)
        String savedText16 = sharedPreferences.getString("savedText16", "");
        tr16.setText(savedText16);


        // Save the text to SharedPreferences when a button is clicked
        Button saveButton = findViewById(R.id.saveButton2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Save the text from the first EditText
                String textToSave13 = tr13.getText().toString();
                editor.putString("savedText13", textToSave13);

                // Save the text from another EditText (e.g., tr2)
                String textToSave14 = tr14.getText().toString();
                editor.putString("savedText14", textToSave14);

                // Save the text from another EditText (e.g., tr3)
                String textToSave15 = tr15.getText().toString();
                editor.putString("savedText15", textToSave15);

                // Save the text from another EditText (e.g., tr4)
                String textToSave16 = tr16.getText().toString();
                editor.putString("savedText16", textToSave16);


                editor.apply();
            }
        });






        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, History7.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                 }
        });

        t8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, History8.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
              }
        });

        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, History9.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               }
        });

        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, History10.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
        });



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, HomePage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
        });

        leaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, DiseasesInformation.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, CameraPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, TreeVisualization.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
              }
        });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTree.this, MonthlyReport.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                 }
        });





        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }}
