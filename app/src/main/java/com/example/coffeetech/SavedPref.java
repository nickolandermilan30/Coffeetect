package com.example.coffeetech;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SavedPref extends Application {

    private static final String PREF_HIGHLIGHTED_BUTTON = "highlighted_button";
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("ButtonStatePrefs", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}

