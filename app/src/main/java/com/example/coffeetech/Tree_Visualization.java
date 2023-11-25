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

public class Tree_Visualization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_visualization);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ImageButton camera, gallery, home, leaf, cam, history, cal ;
        Button othernext;
        EditText tr1, tr2, tr3, tr4, tr5, tr6 ;

        tr1 = findViewById(R.id.textView7);
        tr2 = findViewById(R.id.textView8);
        tr3 = findViewById(R.id.textView9);
        tr4 = findViewById(R.id.textView10);
        tr5 = findViewById(R.id.textView11);
        tr6 = findViewById(R.id.textView12);

        home = findViewById(R.id.home);
        leaf = findViewById(R.id.leaf);
        cam = findViewById(R.id.cam);
        history = findViewById(R.id.history);
        cal = findViewById(R.id.cal);

        ImageButton t1, t2, t3, t4, t5, t6 ;

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        t6 = findViewById(R.id.t6);

        othernext = findViewById(R.id.other);



        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Retrieve and set the saved text for the first EditText
        String savedText1 = sharedPreferences.getString("savedText1", "");
        tr1.setText(savedText1);

        // Retrieve and set the saved text for another EditText (e.g., tr2)
        String savedText2 = sharedPreferences.getString("savedText2", "");
        tr2.setText(savedText2);

        // Retrieve and set the saved text for another EditText (e.g., tr3)
        String savedText3 = sharedPreferences.getString("savedText3", "");
        tr3.setText(savedText3);

        // Retrieve and set the saved text for another EditText (e.g., tr4)
        String savedText4 = sharedPreferences.getString("savedText4", "");
        tr4.setText(savedText4);

        // Retrieve and set the saved text for another EditText (e.g., tr5)
        String savedText5 = sharedPreferences.getString("savedText5", "");
        tr5.setText(savedText5);

        // Retrieve and set the saved text for another EditText (e.g., tr6)
        String savedText6 = sharedPreferences.getString("savedText6", "");
        tr6.setText(savedText6);







        // Save the text to SharedPreferences when a button is clicked
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                EditText[] editTextArray = {tr1, tr2, tr3, tr4, tr5, tr6};

                for (int i = 0; i < editTextArray.length; i++) {
                    String key = "savedText" + (i + 1);
                    String textToSave = editTextArray[i].getText().toString();
                    editor.putString(key, textToSave);
                }

                editor.apply();
            }
        });




        othernext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, Othe_tree.class);
                startActivity(intent);
              }
        });



        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, History.class);
                startActivity(intent);
               }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, History2.class);
                startActivity(intent);
              }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, History3.class);
                startActivity(intent);
                }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, History4.class);
                startActivity(intent);
                   }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, History5.class);
                startActivity(intent);
                 }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, History6.class);
                startActivity(intent);
                }
        });




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, HomePage.class);
                startActivity(intent);
                   }
        });

        leaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, Diseases_Information.class);
                startActivity(intent);
               }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, Camera_page.class);
                startActivity(intent);
               }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, Tree_Visualization.class);
                startActivity(intent);
               }
        });


        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tree_Visualization.this, Monthly_Report.class);
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



}