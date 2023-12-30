package com.example.coffeetech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        // Get data for the current position
        String diseaseInfo = getItem(position);

        // Split the disease info into disease name and percentage
        String[] parts = diseaseInfo.split(": ");
        String diseaseName = parts[0];
        String percentage = parts[1];

        // Set data to the custom layout
        TextView diseaseNameTextView = convertView.findViewById(R.id.diseaseNameTextView);
        TextView percentageTextView = convertView.findViewById(R.id.percentageTextView);

        diseaseNameTextView.setText(diseaseName);
        percentageTextView.setText(percentage);

        return convertView;
    }
}
