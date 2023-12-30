package com.example.coffeetech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// ... (Iba pang imports)

public class Line_chart extends AppCompatActivity {

    private TextView diseaseTextView;

    private LineChart lineChart;
    private static final String PREF_KEY = "saved_data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        diseaseTextView = findViewById(R.id.diseaseTextView);

        Intent intent = getIntent();
        if (intent != null) {
            HashMap<String, Float> diseasePercentageMap = (HashMap<String, Float>) intent.getSerializableExtra("diseasePercentageMap");
            int currentMonth = intent.getIntExtra("currentMonth", 0);

            if (diseasePercentageMap != null) {
                lineChart = findViewById(R.id.lineChart);
                setupLineChart(lineChart);

                List<String> diseases = new ArrayList<>(diseasePercentageMap.keySet());

                // Create a list to store LineDataSet objects
                List<LineDataSet> dataSets = new ArrayList<>();

                saveChartData(diseasePercentageMap, currentMonth);

                for (int i = 0; i < diseases.size(); i++) {
                    String disease = diseases.get(i);
                    List<Entry> entries = new ArrayList<>();


                    // Get percentage values for the current disease and month
                    List<Float> percentages = getSavedChartData(disease, currentMonth);

                    for (int j = 0; j < percentages.size(); j++) {
                        entries.add(new Entry(j, percentages.get(j)));
                    }

                    LineDataSet dataSet = new LineDataSet(entries, disease);
                    dataSet.setColor(getDiseaseColor(i));
                    dataSet.setCircleColor(getDiseaseColor(i));
                    dataSet.setDrawCircles(false);
                    dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    dataSets.add(dataSet);

                    // Enable drawing circles and set circle radius
                    dataSet.setDrawCircles(true);
                    dataSet.setCircleRadius(5f);
                }

                // Create a LineData object and add all LineDataSet objects to it
                LineData lineData = new LineData(dataSets.toArray(new LineDataSet[0]));

                lineChart.setData(lineData);
                lineChart.invalidate();

                // Set up OnChartValueSelectedListener to update the TextView
                lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        int dataSetIndex = h.getDataSetIndex();
                        String selectedDisease = diseases.get(dataSetIndex);
                        diseaseTextView.setText("Selected Disease: " + selectedDisease);
                        diseaseTextView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNothingSelected() {
                        diseaseTextView.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    private void saveChartData(HashMap<String, Float> diseasePercentageMap, int currentMonth) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        for (String disease : diseasePercentageMap.keySet()) {
            String key = generateKey(disease, currentMonth);
            Float percentage = diseasePercentageMap.get(disease);
            editor.putFloat(key, percentage != null ? percentage : 0f);
        }

        editor.apply();
    }

    private List<Float> getSavedChartData(String disease, int currentMonth) {
        List<Float> percentages = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        for (int i = 0; i < 12; i++) {
            String key = generateKey(disease, i);
            Float percentage = preferences.getFloat(key, 0f);
            percentages.add(percentage);
        }

        return percentages;
    }

    private String generateKey(String disease, int month) {
        return disease + "_" + month;
    }

    // Add this method to retrieve percentage values for a specific disease and month
    private List<Float> getPercentageValuesForDisease(HashMap<String, Float> diseasePercentageMap, String disease, int currentMonth) {
        List<Float> percentages = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            if (i == currentMonth) {
                Float percentage = diseasePercentageMap.get(disease);
                percentages.add(percentage != null ? percentage : 0f);
            } else {
                percentages.add(0f);
            }
        }

        return percentages;
    }

    private void setupLineChart(LineChart lineChart) {
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // Tanggaling ang legend
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new YearAxisValueFormatter());

        // I-customize ang XAxis para maging mas maliit ang pagitan ng mga label
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12); // Bilang ng mga label sa axis
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12f); // Maximum axis value

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);

        lineChart.getAxisRight().setEnabled(false);
    }

    static class YearAxisValueFormatter extends ValueFormatter {
        private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            int position = (int) value;
            if (position >= 0 && position < months.length) {
                return months[position];
            }
            return "";
        }
    }




    private int getDiseaseColor(int position) {
        int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN};
        return colors[position % colors.length];
    }
}

