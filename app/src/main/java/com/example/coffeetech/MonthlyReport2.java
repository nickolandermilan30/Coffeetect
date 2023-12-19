package com.example.coffeetech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReport2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report2);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        // Retrieve the highest disease from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String highestDisease = intent.getStringExtra("highestDisease");

            // Filter out specific severity levels (Mild, Moderate, Critical)
            highestDisease = filterSeverityLevels(highestDisease);

            // Display the filtered highest disease in a TextView or handle it as needed
            TextView highestDiseaseTextView = findViewById(R.id.highestDiseaseTextView);
            highestDiseaseTextView.setText(highestDisease);

            // Create and configure the LineChart
            LineChart lineChart = findViewById(R.id.lineChart);
            configureLineChart(lineChart);

            // Populate the LineChart with data
            populateLineChart(lineChart);
        }
    }

    private String filterSeverityLevels(String diseaseName) {
        // List of severity levels to filter out
        String[] severityLevels = {"Mild", "Moderate", "Critical"};

        // Check if diseaseName contains any severity level, and remove it
        for (String severity : severityLevels) {
            diseaseName = diseaseName.replace(severity, "").trim();
        }

        return diseaseName;
    }


    private void configureLineChart(LineChart lineChart) {
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
    }

    private void populateLineChart(LineChart lineChart) {
        List<String> months = getMonths(); // Replace this with your method to get months
        List<String> diseases = getDiseases(); // Replace this with your method to get diseases

        List<Entry> entries = new ArrayList<>();

        // Replace this with your logic to determine the data points for each month
        for (int i = 0; i < months.size(); i++) {
            // Replace this with your logic to get the value for the current month and disease
            float value = i == 11 ? 1.5f : 0.5f;
            entries.add(new Entry(i, value));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Disease Trends");
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        // Configure X-axis labels
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setLabelCount(months.size());
    }

    private List<String> getMonths() {
        // Replace this with your logic to get the months (e.g., January to December)
        List<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sept");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");
        return months;
    }

    private List<String> getDiseases() {
        // Replace this with your logic to get the list of diseases
        List<String> diseases = new ArrayList<>();
        diseases.add("Sooty Mold");
        diseases.add("Leaf Miner");
        diseases.add("Leaf Rust");
        diseases.add("Cercospora");
        diseases.add("Phoma");
        diseases.add("Healthy Leaf");
        return diseases;
    }
}
