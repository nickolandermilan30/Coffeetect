package com.example.coffeetech;

import android.graphics.Bitmap;

public class SavedResult {
    private String diseaseName;
    private Bitmap imageBitmap;
    private String imageUrl; // Idagdag ang imageUrl property

    public SavedResult(String diseaseName, Bitmap imageBitmap) {
        this.diseaseName = diseaseName;
        this.imageBitmap = imageBitmap;
        this.imageUrl = imageUrl;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
