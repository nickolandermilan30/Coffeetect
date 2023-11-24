package com.example.coffeetech;

import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class DiseaseData {
    private static List<PieEntry> pieEntries;

    // Initialize default data
    static {
        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(25f, "Disease 1"));
        pieEntries.add(new PieEntry(35f, "Disease 2"));
        pieEntries.add(new PieEntry(40f, "Disease 3"));
        pieEntries.add(new PieEntry(60f, "Disease 4"));
        pieEntries.add(new PieEntry(75f, "Disease 5"));
        pieEntries.add(new PieEntry(80f, "Disease 6"));
    }

    public static List<PieEntry> getPieEntries() {
        return pieEntries;
    }

    public static List<String> getDiseaseNames() {
        List<String> diseaseNames = new ArrayList<>();
        for (PieEntry entry : pieEntries) {
            diseaseNames.add(entry.getLabel());
        }
        return diseaseNames;
    }

    public static void clearPieEntries() {
        if (pieEntries != null) {
            pieEntries.clear();
        }
    }

    public static void setPieEntries(List<PieEntry> entries) {
        pieEntries = entries;
    }

    public static void addPieEntry(PieEntry entry) {
        if (pieEntries == null) {
            pieEntries = new ArrayList<>();
        }
        pieEntries.add(entry);
    }
}
