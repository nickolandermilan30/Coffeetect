package com.example.coffeetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonthlyReport2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report2);

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> diseaseNames = intent.getStringArrayListExtra("diseaseNames");

            HashMap<String, Float> diseasePercentageMap = new HashMap<>();

            if (diseaseNames != null) {
                for (String diseaseName : diseaseNames) {
                    float percentage = intent.getFloatExtra(diseaseName + "_percentage", 0.0f);
                    diseasePercentageMap.put(diseaseName, percentage);
                }

                ListView listView = findViewById(R.id.listView);
                // Baguhin ang ArrayAdapter na gumamit ng custom layout
                MyAdapter adapter = new MyAdapter(this, new ArrayList<>(diseasePercentageMap.keySet()), diseasePercentageMap);
                listView.setAdapter(adapter);
            }
        }
    }

    // Gumawa ng MyAdapter class na nagmamana sa ArrayAdapter
    private static class MyAdapter extends ArrayAdapter<String> {
        private final HashMap<String, Float> diseasePercentageMap;

        public MyAdapter(MonthlyReport2 context, List<String> items, HashMap<String, Float> diseasePercentageMap) {
            super(context, R.layout.list_item_layout, items);
            this.diseasePercentageMap = diseasePercentageMap;
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

            String diseaseName = getItem(position);
            Float percentage = diseasePercentageMap.get(diseaseName);

            if (diseaseName != null && percentage != null) {
                textViewDiseaseName.setText(diseaseName);
                textViewPercentage.setText(String.format("%.2f%%", percentage));
            }

            return view;
        }
    }
}