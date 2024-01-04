package com.example.coffeetech;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StorageActivity extends AppCompatActivity {

    private ListView listView;
    private List<ChartItem> chartItems;
    private static final String CHART_ITEMS_PREFS_NAME = "ChartItemsPrefs";
    private static final String CHART_ITEMS_KEY = "ChartItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        listView = findViewById(R.id.listview);
        chartItems = new ArrayList<>();

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button deleteListButton = findViewById(R.id.deleteListButton);
        deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the list of saved chart items
                chartItems.clear();

                // Save the updated list to SharedPreferences
                saveChartItems();

                // Notify the adapter about the change
                ((ChartListAdapter) listView.getAdapter()).notifyDataSetChanged();
            }
        });

        // Retrieve the image path and selected year from the intent
        String imagePath = getIntent().getStringExtra("imagePath");
        int selectedYear = getIntent().getIntExtra("selectedYear", 1);

        // Retrieve the list of saved chart items from SharedPreferences
        retrieveSavedChartItems(selectedYear);

        // Populate the chartItems list with ChartItem object
        if (imagePath != null) {
            // Use current date and time as the image name
            String currentDate = getCurrentDate("MMMM d, yyyy");
            String currentTime = getCurrentDate("kk:mm:ss");
            String imageName = "Chart_" + currentDate + "_" + currentTime + ".png";

            ChartItem chartItem = new ChartItem(imagePath, selectedYear, imageName, currentDate, currentTime);
            addChartItem(chartItem); // Add the new chart item to the list
        }

        // Create and set the custom adapter for the ListView
        ChartListAdapter adapter = new ChartListAdapter(this, R.layout.list_item_chart, chartItems);
        listView.setAdapter(adapter);

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChartItem clickedItem = chartItems.get(position);
                showClickedChart(clickedItem.getImagePath());
            }
        });
    }

    private void retrieveSavedChartItems(int selectedYear) {
        SharedPreferences preferences = getSharedPreferences(CHART_ITEMS_PREFS_NAME, MODE_PRIVATE);
        String savedChartItemsJson = preferences.getString(CHART_ITEMS_KEY, "");

        if (!savedChartItemsJson.isEmpty()) {
            // Deserialize the JSON string to a list of ChartItem objects
            Gson gson = new Gson();
            Type type = new TypeToken<List<ChartItem>>() {
            }.getType();
            List<ChartItem> savedChartItems = gson.fromJson(savedChartItemsJson, type);

            // Add the saved chart items to the current list
            chartItems.addAll(savedChartItems);
        }
    }

    private void saveChartItems() {
        SharedPreferences preferences = getSharedPreferences(CHART_ITEMS_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Serialize the list of ChartItem objects to a JSON string
        Gson gson = new Gson();
        String chartItemsJson = gson.toJson(chartItems);

        // Save the JSON string to SharedPreferences
        editor.putString(CHART_ITEMS_KEY, chartItemsJson);
        editor.apply();
    }

    private void addChartItem(ChartItem chartItem) {
        chartItems.add(chartItem);
        saveChartItems();
    }

    private void showClickedChart(String imagePath) {
        Intent intent = new Intent(StorageActivity.this, ChartDetailActivity.class);
        intent.putExtra("imagePath", imagePath);
        startActivity(intent);
    }

    private String getCurrentDate(String s) {
        Calendar calendar = Calendar.getInstance();
        // Use "yyyyMMdd_kkmmss" format for image name
        return DateFormat.format("yyyyMMdd_kkmmss", calendar).toString();
    }
}
