package com.example.coffeetech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MonthlyReport2 extends AppCompatActivity {

    private MyAdapter adapter;
    private Button compareButton;
    private SharedPreferences monthSharedPreferences;

    private String sharedPreferencesName; // Variable para sa pangalan ng SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report2);

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> diseaseNames = intent.getStringArrayListExtra("diseaseNames");

            HashMap<String, Float> diseasePercentageMap = new HashMap<>();

            if (diseaseNames != null) {
                diseasePercentageMap = new HashMap<>();
                for (String diseaseName : diseaseNames) {
                    float percentage = intent.getFloatExtra(diseaseName + "_percentage", 0.0f);
                    diseasePercentageMap.put(diseaseName, percentage);
                }

                ListView listView = findViewById(R.id.listView);
                adapter = new MyAdapter(this, new ArrayList<>(diseasePercentageMap.keySet()), diseasePercentageMap);
                listView.setAdapter(adapter);
                compareButton = findViewById(R.id.compareButton);
                compareButton.setVisibility(View.GONE);

                Button saveButton = findViewById(R.id.saveButton);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveData();
                        compareButton.setVisibility(View.VISIBLE);
                    }
                });

                Button compareButton = findViewById(R.id.compareButton);
                HashMap<String, Float> finalDiseasePercentageMap = diseasePercentageMap;
                compareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                        Intent compareIntent = new Intent(MonthlyReport2.this, LineChart.class);
                        compareIntent.putExtra("diseasePercentageMap", finalDiseasePercentageMap);
                        compareIntent.putExtra("currentMonth", currentMonth);
                        startActivity(compareIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });

                // I-set ang pangalan ng SharedPreferences base sa buwan
                int currentMonth = getCurrentMonthFromIntent();
                sharedPreferencesName = getCurrentMonthSharedPreferencesName(currentMonth);

                // Gumawa ng instance ng SharedPreferences gamit ang pangalan ng buwan
                monthSharedPreferences = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE);
            }
        }
    }

    private String getCurrentMonthSharedPreferencesName(int currentMonth) {
        String[] monthNames = new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return monthNames[currentMonth];
    }

    private int getCurrentMonthFromIntent() {
        return 0; // Dapat maayos ang logic mo dito para makuha ang tamang buwan
    }

    private void saveData() {
        SharedPreferences.Editor editor = monthSharedPreferences.edit();

        String dateTime = getCurrentDateTime();
        editor.putString("DateTime", dateTime);

        for (String diseaseName : adapter.diseasePercentageMap.keySet()) {
            float percentage = adapter.diseasePercentageMap.get(diseaseName);
            editor.putFloat(diseaseName, percentage);
            adapter.updateDateTime(diseaseName, dateTime);
        }

        editor.apply();

        Toast.makeText(MonthlyReport2.this, "Data Saved!", Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }

    private static class MyAdapter extends ArrayAdapter<String> {
        private final HashMap<String, Float> diseasePercentageMap;
        private final HashMap<String, String> diseaseDateTimeMap;

        public MyAdapter(MonthlyReport2 context, List<String> items, HashMap<String, Float> diseasePercentageMap) {
            super(context, R.layout.list_item_layout, items);
            this.diseasePercentageMap = diseasePercentageMap;
            this.diseaseDateTimeMap = new HashMap<>();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.list_item_layout, parent, false);
            }

            TextView textViewDiseaseName = view.findViewById(R.id.textViewDiseaseName);
            TextView textViewPercentage = view.findViewById(R.id.textViewPercentage);
            TextView textViewDateTime = view.findViewById(R.id.textViewDateTime);

            String diseaseName = getItem(position);
            Float percentage = diseasePercentageMap.get(diseaseName);

            if (diseaseName != null && percentage != null) {
                textViewDiseaseName.setText(diseaseName);
                textViewPercentage.setText(String.format("%.2f%%", percentage));

                String dateTime = diseaseDateTimeMap.get(diseaseName);
                if (dateTime == null) {
                    dateTime = getCurrentDateTime();
                    diseaseDateTimeMap.put(diseaseName, dateTime);
                }
                textViewDateTime.setText(dateTime);
            }

            return view;
        }

        private String getCurrentDateTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            return sdf.format(new Date());
        }

        public void updateDateTime(String diseaseName, String dateTime) {
            diseaseDateTimeMap.put(diseaseName, dateTime);
            notifyDataSetChanged();
        }
    }
}
