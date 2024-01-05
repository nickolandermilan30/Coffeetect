package com.example.coffeetech;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomMarkerView extends MarkerView {

    private TextView markerText;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        markerText = findViewById(R.id.markerText);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        // Customize the content displayed in the marker view
        markerText.setText("Value: " + e.getY());

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        // Adjust the offset to move the marker view above the point
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
