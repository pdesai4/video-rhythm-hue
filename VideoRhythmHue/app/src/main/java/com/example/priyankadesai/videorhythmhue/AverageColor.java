package com.example.priyankadesai.videorhythmhue;

/**
 * Created by rajvi on 04-12-2017.
 */
import android.graphics.Color;
import android.graphics.Bitmap;

public class AverageColor {
    public static int calculateAverageColor(Bitmap bitmap, int pixelSpacing) {
        if(bitmap == null){
            return -1;
        }
        int R = 0; int G = 0; int B = 0;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i += pixelSpacing) {
            int color = pixels[i];
            R += Color.red(color);
            G += Color.green(color);
            B += Color.blue(color);
            n++;
        }

        //System.out.print(Color.rgb(R / n, G / n, B / n));
        return Color.rgb(R / n, G / n, B / n);
    }
}
