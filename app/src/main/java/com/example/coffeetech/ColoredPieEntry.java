package com.example.coffeetech;

import com.github.mikephil.charting.data.PieEntry;

public class ColoredPieEntry extends PieEntry {

    private int color;

    public ColoredPieEntry(float value, String label, int color) {
        super(value, label);
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
