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

    private MyAdapter adapter; // Add this line
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
                // Baguhin ang ArrayAdapter na gumamit ng custom layout
                adapter = new MyAdapter(this, new ArrayList<>(diseasePercentageMap.keySet()), diseasePercentageMap);
                listView.setAdapter(adapter);

                // Button to save the data
                Button saveButton = findViewById(R.id.saveButton);
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Perform save operation here
                        saveData();
                    }
                });

                // Button to start another activity with line chart
                // Inside the onCreate method of MonthlyReport2 activity
                Button compareButton = findViewById(R.id.compareButton);
                HashMap<String, Float> finalDiseasePercentageMap = diseasePercentageMap;
                compareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                        Intent compareIntent = new Intent(MonthlyReport2.this, Line_chart.class);
                        compareIntent.putExtra("diseasePercentageMap", finalDiseasePercentageMap);
                        compareIntent.putExtra("currentMonth", currentMonth);
                        startActivity(compareIntent);
                    }
                });


            }
        }
    }

    private void saveData() {
        // Save data using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SavedData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save date and time
        String dateTime = getCurrentDateTime();
        editor.putString("DateTime", dateTime);

        // Save disease percentages with date and time
        for (String diseaseName : adapter.diseasePercentageMap.keySet()) {
            float percentage = adapter.diseasePercentageMap.get(diseaseName);
            editor.putFloat(diseaseName, percentage);
            // Update the date and time for each disease
            adapter.updateDateTime(diseaseName, dateTime);
        }

        editor.apply();

        // Notify the user that data is saved (you can customize this part)
        Toast.makeText(MonthlyReport2.this, "Data Saved!", Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }


    // Gumawa ng MyAdapter class na nagmamana sa ArrayAdapter
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
            TextView textViewDateTime = view.findViewById(R.id.textViewDateTime); // New TextView

            String diseaseName = getItem(position);
            Float percentage = diseasePercentageMap.get(diseaseName);

            if (diseaseName != null && percentage != null) {
                textViewDiseaseName.setText(diseaseName);
                textViewPercentage.setText(String.format("%.2f%%", percentage));

                // Set the date and time in the textViewDateTime
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
            notifyDataSetChanged(); // Refresh the ListView
        }
    }
}