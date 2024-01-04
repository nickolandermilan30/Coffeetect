package com.example.coffeetech;

import android.text.format.DateFormat;

import java.util.Calendar;

public class ChartItem {
    private String imagePath;
    private int selectedYear;
    private String imageName;
    private String date; // Field for date
    private String time; // Field for time

    public ChartItem(String imagePath, int selectedYear, String imageName, String currentDate, String currentTime) {
        this.imagePath = imagePath;
        this.selectedYear = selectedYear;
        this.imageName = imageName;
        this.date = getCurrentDate(); // Set the current date when creating the ChartItem
        this.time = getCurrentTime(); // Set the current time when creating the ChartItem
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public String getImageName() {
        return imageName;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return DateFormat.format("MMMM d, yyyy", calendar).toString(); // Format the date as "January 2, 2024"
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return DateFormat.format("hh:mm:ss A", calendar).toString(); // Format the time as "12:30:45 PM"
    }
}
