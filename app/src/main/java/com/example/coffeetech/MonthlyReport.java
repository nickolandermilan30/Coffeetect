    package com.example.coffeetech;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.ImageButton;
    import android.widget.LinearLayout.LayoutParams;
    import android.widget.ListView;
    import android.widget.TextView;

    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;

    import com.github.mikephil.charting.charts.PieChart;
    import com.github.mikephil.charting.data.Entry;
    import com.github.mikephil.charting.data.PieData;
    import com.github.mikephil.charting.data.PieDataSet;
    import com.github.mikephil.charting.data.PieEntry;
    import com.github.mikephil.charting.formatter.PercentFormatter;
    import com.github.mikephil.charting.highlight.Highlight;
    import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.HashSet;
    import java.util.Map;
    import java.util.Set;

    public class MonthlyReport extends AppCompatActivity {

        private ArrayList<String> diseaseList;
        private ArrayAdapter<String> adapter;

        // SharedPreferences key for storing the disease list
        private static final String PREFS_NAME = "MyPrefs";
        private static final String DISEASE_LIST_KEY = "diseaseList";
        private static final int MAX_SICKNESS_COUNT = 6; // Maximum number of sicknesses
        private static final int TOTAL_PERCENTAGE = 100; // Total percentage for the Pie Chart
        private long diseaseCounter = 1; // Initialize the counter
        ImageButton home, leaf, cam, history, cal;
        View clearButton;
        PieChart pieChart;  // Add this line
        ImageButton legendButton;  // Add this line

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_monthly_report);

            // Hide the action bar (app bar or title bar)
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            // Set onClickListener for the button to show the highest disease
            findViewById(R.id.linegraph).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHighestDisease();
                }
            });

            // Initialize the disease list and adapter
            diseaseList = loadDiseaseList();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, diseaseList);
            pieChart = findViewById(R.id.graphBarChart);
            legendButton = findViewById(R.id.legendButton);

            home = findViewById(R.id.home);
            leaf = findViewById(R.id.leaf);
            cam = findViewById(R.id.cam);
            history = findViewById(R.id.tree);
            cal = findViewById(R.id.monthly);
            clearButton = findViewById(R.id.clearButton);
            ImageButton backButton = findViewById(R.id.backButton);



            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MonthlyReport.this, HomePage.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            leaf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MonthlyReport.this, DiseasesInformation.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            cam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MonthlyReport.this, CameraPage.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MonthlyReport.this, TreeVisualization.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            cal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MonthlyReport.this, MonthlyReport.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearDiseaseList();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // Set onClickListener for the legend button
            legendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLegendDialog();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // Set an onClickListener for the button to generate the pie chart
            findViewById(R.id.generatePieChartButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generatePieChart();
                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            // Set the adapter to the ListView
            ListView listView = findViewById(R.id.diseaseListView);
            listView.setAdapter(adapter);

            // Example: Receive the added disease from TreeResult1
            Intent intent = getIntent();
            if (intent != null) {
                String addedDisease = intent.getStringExtra("diseaseName");
                if (addedDisease != null) {
                    addDiseaseToList(addedDisease);
                    // Save the updated disease list
                    saveDiseaseList();
                }
            }
        }

        // Method to show the activity displaying the highest occurring disease
        private void showHighestDisease() {
            // Implement your logic to determine the highest occurring disease
            String highestDisease = getHighestOccurringDisease();

            // Start a new activity to display the highest occurring disease
            Intent intent = new Intent(MonthlyReport.this, MonthlyReport2.class);
            intent.putExtra("highestDisease", highestDisease);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        // Method to get the highest occurring disease
        private String getHighestOccurringDisease() {
            // Implement your logic to determine the highest occurring disease
            // You may use the data from the pie chart to find this information
            // For now, let's assume it's the first disease in the list
            if (!diseaseList.isEmpty()) {
                return diseaseList.get(0);
            } else {
                return "No data available";
            }
        }

        // Method to show the legend dialog
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
                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 8); // Adjust margin as needed
                legendTextView.setLayoutParams(params);


                // Add color views to paletteLayout
                View colorView = new View(this);
                colorView.setId(View.generateViewId());
                colorView.setLayoutParams(new LayoutParams(24, 24)); // Adjust size as needed
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

        private void generatePieChart() {
            // Create entries for the pie chart
            ArrayList<PieEntry> entries = new ArrayList<>();

            // Create a map to store the count for each base disease and its corresponding color
            HashMap<String, Integer> baseDiseaseCountMap = new HashMap<>();
            HashMap<String, Integer> baseDiseaseColorMap = new HashMap<>();

            // Assign colors to each base disease
            baseDiseaseColorMap.put("Cercospora", android.graphics.Color.parseColor("#FF5733"));
            baseDiseaseColorMap.put("Leaf Miner", android.graphics.Color.parseColor("#3366FF"));
            baseDiseaseColorMap.put("Leaf Rust", android.graphics.Color.parseColor("#FF33CC"));
            baseDiseaseColorMap.put("Phoma", android.graphics.Color.parseColor("#FFFF33"));
            baseDiseaseColorMap.put("Sooty Mold", android.graphics.Color.parseColor("#8C33FF"));
            baseDiseaseColorMap.put("Healthy Leaf", android.graphics.Color.parseColor("#33FF57"));

            // Count occurrences of each base disease
            for (String disease : diseaseList) {
                String baseDisease = getBaseDisease(disease);
                if (baseDiseaseCountMap.containsKey(baseDisease)) {
                    // If the base disease is already in the map, increment its count
                    int count = baseDiseaseCountMap.get(baseDisease);
                    baseDiseaseCountMap.put(baseDisease, count + 1);
                } else {
                    // If the base disease is not in the map, add it with count 1
                    baseDiseaseCountMap.put(baseDisease, 1);
                }
            }

            // Calculate total count of base diseases
            int totalBaseDiseases = 0;
            for (int count : baseDiseaseCountMap.values()) {
                totalBaseDiseases += count;
            }

            // Calculate the percentage for each base disease based on a constant total (TOTAL_PERCENTAGE)
            for (Map.Entry<String, Integer> entry : baseDiseaseCountMap.entrySet()) {
                String baseDisease = entry.getKey();
                int count = entry.getValue();
                int color = baseDiseaseColorMap.containsKey(baseDisease) ? baseDiseaseColorMap.get(baseDisease) : android.graphics.Color.BLACK;

                float percentage = (count / (float) totalBaseDiseases) * TOTAL_PERCENTAGE;
                entries.add(new ColoredPieEntry(percentage, baseDisease, color));
            }


            // Create a data set
            PieDataSet dataSet = new PieDataSet(entries, "");

            // Set colors for the data set based on the color assigned for each base disease
            int[] colors = entries.stream().mapToInt(entry -> ((ColoredPieEntry) entry).getColor()).toArray();
            dataSet.setColors(colors);

            // Create a data object from the data set
            PieData data = new PieData(dataSet);

            // Set data to the pie chart
            pieChart.setData(data);

            // Set legend to null to hide it
            pieChart.getLegend().setEnabled(false);

            // Set description to an empty string to hide the description label
            pieChart.getDescription().setEnabled(false);

            // Set percentage format for the values
            data.setValueFormatter(new PercentFormatter(pieChart));

            // Set text size and color for the data set
            dataSet.setValueTextSize(10f); // Adjust the size as needed
            dataSet.setValueTextColor(android.graphics.Color.BLACK); // Set text color to black

            // Disable drawing values (labels) outside the pie chart
            data.setDrawValues(false);

            // Disable drawing values (labels) on the pie chart
            dataSet.setDrawValues(false);

            // Enable drawing values (labels) on the pie chart only when selected
            dataSet.setDrawValues(true);

            // Set a listener to handle pie chart slice clicks
            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    // Display the label for the selected slice
                    pieChart.setCenterText(entries.get((int) h.getX()).getLabel());
                }

                @Override
                public void onNothingSelected() {
                    // Reset the center text when nothing is selected
                    pieChart.setCenterText("");
                }
            });

            // Refresh the pie chart
            pieChart.invalidate();
        }




            // Helper method to get the base disease name
        private String getBaseDisease(String disease) {
            // Implement your logic to extract the base disease name
            // Customize this logic based on your disease naming conventions.

            if (disease.startsWith("Healthy Leaf")) {
                return "Healthy Leaf";
            } else if (disease.startsWith("Cercospora")) {
                return "Cercospora";
            } else if (disease.startsWith("Leaf Miner")) {
                return "Leaf Miner";
            } else if (disease.startsWith("Leaf Rust")) {
                return "Leaf Rust";
            } else if (disease.startsWith("Phoma")) {
                return "Phoma";
            } else if (disease.startsWith("Sooty Mold")) {
                return "Sooty Mold";
            } else {
                return disease; // Default to the original disease name
            }
        }




        // Implement your logic to get the count for each disease
        private int getCountForDisease(String disease) {
            return 0;
        }

        // Method to add a disease to the list
        private void addDiseaseToList(String disease) {
            // Append a unique identifier to each disease to distinguish them
            String uniqueDisease = disease + "_" + System.currentTimeMillis(); // Use timestamp as a unique identifier

            // Disease is not a duplicate, add it to the list
            diseaseList.add(uniqueDisease);
            adapter.notifyDataSetChanged();

            // Save the updated disease list
            saveDiseaseList();
        }


        // Method to add a disease to the list without showing a dialog and without duplicate check
        private void addDiseaseToListWithoutDialog(String disease) {
            // Disease is not a duplicate, add it to the list
            diseaseList.add(disease);
            adapter.notifyDataSetChanged();

            // Save the updated disease list
            saveDiseaseList();
        }



        // Save disease list to SharedPreferences
        private void saveDiseaseList() {
            Set<String> diseaseSet = new HashSet<>(diseaseList);
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(DISEASE_LIST_KEY, diseaseSet);
            editor.apply();
        }

        // Load disease list from SharedPreferences
        private ArrayList<String> loadDiseaseList() {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            Set<String> defaultSet = new HashSet<>(); // default empty set
            Set<String> savedSet = preferences.getStringSet(DISEASE_LIST_KEY, defaultSet);

            // Convert the set to a list
            return new ArrayList<>(savedSet);
        }

        // Clear disease list from SharedPreferences
        private void clearDiseaseList() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Clear Disease List");
            builder.setMessage("Are you sure you want to clear the disease list?");

            // Add positive button (Yes)
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Clear the diseaseList locally
                    diseaseList.clear();
                    adapter.notifyDataSetChanged();

                    // Clear disease list from SharedPreferences
                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove(DISEASE_LIST_KEY);
                    editor.apply();
                }
            });

            // Add negative button (No)
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });

            // Create and show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }




        @Override
        public void onBackPressed() {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
