package com.example.coffeetech;

public class DiseaseLegend {
    private String name;
    private int color;

    // Custom color palette
    public static final int[] CUSTOM_COLORS = {
            android.graphics.Color.parseColor("#FF5733"),
            android.graphics.Color.parseColor("#33FF57"),
            android.graphics.Color.parseColor("#3366FF"),
            android.graphics.Color.parseColor("#FF33CC"),
            android.graphics.Color.parseColor("#FFFF33"),
            android.graphics.Color.parseColor("#8C33FF")
    };

    public DiseaseLegend(String name, int color) {
    }

    // Constructor to use the custom color palette
    public DiseaseLegend(String name, boolean useCustomColorPalette) {
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
