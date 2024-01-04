package com.example.coffeetech;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// ... (Iba pang imports)

public class Line_chart extends AppCompatActivity {

    private TextView diseaseTextView;

    private LineChart lineChart;
    private static final String PREF_KEY = "saved_data";

    private NumberPicker yearPicker;
    private int selectedYear;
    private Map<String, Integer> baseDiseaseColorMap;
    ImageButton legendButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);



        // Initialize your baseDiseaseColorMap
        baseDiseaseColorMap = new HashMap<>();
        baseDiseaseColorMap.put("Cercospora", android.graphics.Color.parseColor("#FF5733"));
        baseDiseaseColorMap.put("Leaf Miner", android.graphics.Color.parseColor("#3366FF"));
        baseDiseaseColorMap.put("Leaf Rust", android.graphics.Color.parseColor("#FF33CC"));
        baseDiseaseColorMap.put("Phoma", android.graphics.Color.parseColor("#FFFF33"));
        baseDiseaseColorMap.put("Sooty Mold", android.graphics.Color.parseColor("#8C33FF"));
        baseDiseaseColorMap.put("Healthy Leaf", android.graphics.Color.parseColor("#33FF57"));

        ImageButton backButton = findViewById(R.id.backButton);
        legendButton = findViewById(R.id.legendButton2);
        Button resetButton = findViewById(R.id.resetButton);
        diseaseTextView = findViewById(R.id.diseaseTextView);
        TextView monthYearTextView = findViewById(R.id.monthYearTextView);
        yearPicker = findViewById(R.id.yearPicker);
        setupYearPicker();

        Button saveImageButton = findViewById(R.id.saveImageButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        legendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLegendDialog();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a method to save the current state of the chart as an image
                saveChartAsImage();

                // Start StorageActivity
                Intent storageIntent = new Intent(Line_chart.this, StorageActivity.class);
                storageIntent.putExtra("imagePath", getLastSavedImagePath());
                storageIntent.putExtra("selectedYear", selectedYear);
                startActivity(storageIntent);
            }
        });




        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // I-reset ang LineChart at iba pang progress dito
                showResetConfirmationDialog();
            }
        });

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hideYearPicker();
                int selectedYear = newVal;
                updateLineChart(selectedYear);
            }

            private void updateLineChart(int selectedYear) {
                // Kumuha ng dati at bagong data batay sa napiling taon
                HashMap<String, Float> allDiseasePercentageMap = new HashMap<>();

                // Include data from all previous years up to the selected year
                for (int year = 2023; year <= selectedYear; year++) {
                    allDiseasePercentageMap.putAll(getSavedChartDataForYear(year));
                }

                // Kung walang data, puwede mong gawing empty o i-clear ang chart
                if (allDiseasePercentageMap.isEmpty()) {
                    lineChart.clear();
                    return;
                }

                // Itakda ang existing at bagong data
                setupLineChart(lineChart);
                List<String> diseases = new ArrayList<>(allDiseasePercentageMap.keySet());

                // Create a list to store LineDataSet objects
                List<LineDataSet> dataSets = new ArrayList<>();

                int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

                // Update existing data with new data
                for (int i = 0; i < diseases.size(); i++) {
                    String disease = diseases.get(i);
                    List<Entry> entries = new ArrayList<>();

                    // Get percentage values for the current disease and year
                    List<Float> percentages = getSavedChartData(disease, selectedYear);

                    // Fill entries with percentages up to the current month
                    for (int j = 0; j <= currentMonth && j < percentages.size(); j++) {
                        entries.add(new Entry(j, percentages.get(j)));
                    }

                    for (LineDataSet dataSet : dataSets) {
                        dataSet.setLineWidth(2f);
                        dataSet.setDrawFilled(true);
                        dataSet.setFillColor(Color.BLUE);
                        dataSet.setFillAlpha(50);
                    }


                    LineDataSet dataSet = new LineDataSet(entries, disease);
                    dataSet.setColor(getDiseaseColor(disease));
                    dataSet.setCircleColor(getDiseaseColor(disease));

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


            // Method para sa pagkuha ng dati nang data batay sa napiling taon
            private HashMap<String, Float> getSavedChartDataForYear(int year) {
                HashMap<String, Float> oldDiseasePercentageMap = new HashMap<>();

                // Isalaysay ang logic para sa pagkuha ng dati nang data dito

                return oldDiseasePercentageMap;
            }

            // Method para sa pagkuha ng bagong data batay sa napiling taon
            private HashMap<String, Float> getDataForYear(int year) {
                HashMap<String, Float> newDiseasePercentageMap = new HashMap<>();

                // Isalaysay ang logic para sa pagkuha ng bagong data dito

                return newDiseasePercentageMap;
            }


        });

        Intent intent = getIntent();
        if (intent != null) {
            HashMap<String, Float> diseasePercentageMap = (HashMap<String, Float>) intent.getSerializableExtra("diseasePercentageMap");
            int currentMonth = intent.getIntExtra("currentMonth", 0);

            // Ang currentMonth ay mula sa intent extras
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            String currentMonthName = months[currentMonth];
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            String monthYearText = currentMonthName + " " + currentYear;
            monthYearTextView.setText(monthYearText);
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
                    dataSet.setColor(getDiseaseColor(disease));
                    dataSet.setCircleColor(getDiseaseColor(disease));

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

                lineChart.setHighlightPerTapEnabled(true);
                lineChart.setHighlightPerDragEnabled(true);
                lineChart.setDrawMarkers(true);

// Customize the marker view (you can create your own custom marker if needed)
                MarkerView markerView = new CustomMarkerView(this, R.layout.custom_marker_view);
                lineChart.setMarker(markerView);

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

    private void showLegendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Legend");

        // Inflate the custom layout for the legend dialog
        View view = getLayoutInflater().inflate(R.layout.legend, null);
        builder.setView(view);

        // DiseaseLegend array with names and colors
        DiseaseLegend[] diseaseLegends = {
                new DiseaseLegend("Cercospora", android.graphics.Color.parseColor("#FF5733")),
                new DiseaseLegend("Healthy Leaf", android.graphics.Color.parseColor("#33FF57")),
                new DiseaseLegend("Leaf Miner", android.graphics.Color.parseColor("#3366FF")),
                new DiseaseLegend("Leaf Rust", android.graphics.Color.parseColor("#FF33CC")),
                new DiseaseLegend("Phoma", android.graphics.Color.parseColor("#FFFF33")),
                new DiseaseLegend("Sooty Mold", android.graphics.Color.parseColor("#8C33FF"))
        };

        // Add legend TextViews and palette color views dynamically
        for (DiseaseLegend diseaseLegend : diseaseLegends) {
            TextView legendTextView = new TextView(this);
            legendTextView.setText(diseaseLegend.getName());
            legendTextView.setTextColor(diseaseLegend.getColor());

            // Set layout parameters for the TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 8); // Adjust margin as needed
            legendTextView.setLayoutParams(params);


            // Add color views to paletteLayout
            View colorView = new View(this);
            colorView.setId(View.generateViewId());
            colorView.setLayoutParams(new LinearLayout.LayoutParams(24, 24)); // Adjust size as needed
            colorView.setBackgroundColor(diseaseLegend.getColor());
        }

        // Set a button in the dialog to close it
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Add this method to retrieve the last saved image path from SharedPreferences
    private String getLastSavedImagePath() {
        SharedPreferences preferences = getSharedPreferences("ImagePathPrefs", MODE_PRIVATE);
        return preferences.getString("imagePath", null);
    }

    private void saveChartAsImage() {
        // Check if the LineChart is not null
        if (lineChart != null) {
            // Create a Bitmap from the LineChart
            Bitmap chartBitmap = lineChart.getChartBitmap();

            // Generate a unique filename using timestamp
            String timestamp = getCurrentTimestamp();  // Implement the getCurrentTimestamp method
            String filename = "chart_image_" + timestamp + ".png";

            // Save the Bitmap to storage (external or internal, based on your requirement)
            String imagePath = saveBitmapToStorage(chartBitmap, filename);

            // Pass the image path and selected year to the StorageActivity
            if (imagePath != null) {
                // I-save ang image path sa SharedPreferences
                saveChartDate(imagePath);

                SharedPreferences preferences = getSharedPreferences("ImagePathPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("imagePath", imagePath); // imagePath ay ang path ng iyong na-save na image
                editor.apply();
            }
        }
    }


    private String getCurrentTimestamp() {
        // Get the current timestamp as a string
        return String.valueOf(System.currentTimeMillis());
    }

    private void saveChartDate(String imagePath) {
        // Save the current date in SharedPreferences using the image path as the key
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(imagePath, getCurrentDate());
        editor.apply();
    }

    private String getCurrentDate() {
        // Get the current date in a desired format (e.g., "yyyy-MM-dd HH:mm:ss")
        // Use SimpleDateFormat or other methods to format the date
        // Example using SimpleDateFormat:
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }


    private String saveBitmapToStorage(Bitmap bitmap, String filename) {
        try {
            // Create a file to save the image (you can customize the file path and name)
            File file = new File(getExternalCacheDir(), filename);

            // Create an output stream to write the bitmap data to the file
            FileOutputStream fos = new FileOutputStream(file);

            // Compress the bitmap and write it to the file as PNG
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            // Close the output stream
            fos.close();

            // Return the absolute path of the saved image
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showYearPicker() {
        yearPicker.setMinValue(2023); // Set your start year
        yearPicker.setMaxValue(Calendar.getInstance().get(Calendar.YEAR)); // Set current year as max
        yearPicker.setValue(Calendar.getInstance().get(Calendar.YEAR)); // Set current year as default value
        yearPicker.setVisibility(View.VISIBLE);
    }

    private void hideYearPicker() {
        yearPicker.setVisibility(View.GONE);
    }

    private void showResetConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Chart");
        builder.setMessage("Are you sure you want to delete the chart?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "Yes," reset the LineChart
                resetLineChart();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "No," do nothing
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetLineChart() {
        if (lineChart != null) {
            lineChart.clear();
        }

        // Clear data from SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }



    private void setupYearPicker() {
        int startYear = 2023; // Pumili ng taon ng simula
        int endYear = 2030;   // Pumili ng taon ng wakas

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Siguruhing ang startYear ay hindi higit sa kasalukuyang taon
        startYear = Math.max(startYear, currentYear);

        // Kung ang currentYear ay mas malaki kaysa sa endYear, i-set ang currentYear bilang endYear
        endYear = Math.max(endYear, currentYear);

        yearPicker.setMinValue(startYear); // Minimum year
        yearPicker.setMaxValue(endYear);   // Maximum year
        yearPicker.setValue(startYear);    // Default value

    }



    private void saveChartData(HashMap<String, Float> diseasePercentageMap, int currentMonth) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        for (String disease : diseasePercentageMap.keySet()) {
            String key = generateKey(disease, currentMonth);
            Float percentage = diseasePercentageMap.get(disease);

            // Check if the percentage is non-zero before saving
            if (percentage != null && percentage != 0f) {
                editor.putFloat(key, percentage);
            }
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

        // Remove the legend
        lineChart.getLegend().setEnabled(false);
        lineChart.getLegend().setTextColor(Color.BLACK);
        lineChart.getLegend().setForm(Legend.LegendForm.CIRCLE);

        lineChart.animateX(1500, Easing.EaseInOutCubic);  // Duration and easing type


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new YearAxisValueFormatter());
        xAxis.setTextColor(Color.BLACK);
        xAxis.setAxisLineColor(Color.BLACK);

        // Customize XAxis to reduce the gap between labels
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12); // Number of labels on the axis
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12f); // Maximum axis value

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisLineColor(Color.BLACK);

        // Customize the grid lines
        xAxis.setDrawGridLines(true);
        leftAxis.setDrawGridLines(true);

        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f); // Update the maximum value to 500

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




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private int getDiseaseColor(String disease) {
        return getColorForDisease(disease);
    }

    private int getColorForDisease(String disease) {
        // Check if the disease exists in the map
        if (baseDiseaseColorMap.containsKey(disease)) {
            return baseDiseaseColorMap.get(disease);
        }
        return Color.BLACK; // Default color if disease is not found in the map
    }

}
