package com.example.coffeetech;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChartListAdapter extends ArrayAdapter<ChartItem> {

    private int resource;

    public ChartListAdapter(Context context, int resource, List<ChartItem> chartItems) {
        super(context, resource, chartItems);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(resource, null);
        }

        ChartItem chartItem = getItem(position);

        if (chartItem != null) {
            // Populate the list item views with data from the ChartItem
            ImageView imageView = view.findViewById(R.id.imageView);
            TextView dateTextView = view.findViewById(R.id.dateTextView);
            TextView timeTextView = view.findViewById(R.id.timeTextView); // Add this TextView

            Bitmap bitmap = BitmapFactory.decodeFile(chartItem.getImagePath());
            imageView.setImageBitmap(bitmap);

            dateTextView.setText(chartItem.getDate()); // Use getDate() instead of getDateAndTime()
            timeTextView.setText(chartItem.getTime()); // Add this line to display time
        }

        return view;
    }
}