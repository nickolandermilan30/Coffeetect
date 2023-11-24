package com.example.coffeetech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Leaf extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaf);


        // Hide the action bar (app bar or title bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageButton camera, gallery, home, leaf, cam, history, cal ;

        home = findViewById(R.id.home);
        leaf = findViewById(R.id.leaf);
        cam = findViewById(R.id.cam);
        history = findViewById(R.id.history);
        cal = findViewById(R.id.cal);

        ImageButton cercospora, phoma, sootymold, leafrust, leafminer;

        cercospora = findViewById(R.id.cercospora);
        phoma = findViewById(R.id.phoma);
        sootymold = findViewById(R.id. sootymold);
        leafrust = findViewById(R.id.leafrust);
        leafminer = findViewById(R.id.leafminer);












        cercospora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, CERCOSPORA.class);
                startActivity(intent);
                  }
        });

        phoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, PHOMA.class);
                startActivity(intent);
                }
        });
        sootymold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, SOOTY_MOLD.class);
                startActivity(intent);
                 }
        });

        leafrust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, RUST.class);
                startActivity(intent);
                 }
        });
        leafminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, MINER.class);
                startActivity(intent);
               }
        });







        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, Homepage.class);
                startActivity(intent);
                 }
        });

        leaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, Leaf.class);
                startActivity(intent);
                 }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, Camera_page.class);
                startActivity(intent);
                }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, Folders.class);
                startActivity(intent);
                }
        });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Leaf.this, Calendar.class);
                startActivity(intent);
               }
        });


        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
