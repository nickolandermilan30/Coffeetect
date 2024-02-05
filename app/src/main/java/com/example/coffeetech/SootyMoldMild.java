package com.example.coffeetech;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SootyMoldMild extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sootymold_mild);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

