package com.ppyy.ppweatherplus.utils;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * Created by NeuroAndroid on 2017/5/9.
 */

public class ColorUtils {
    public static int stripAlpha(@ColorInt int color) {
        return -16777216 | color;
    }

    @ColorInt
    public static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0D, to = 2.0D) float by) {
        if (by == 1.0F) {
            return color;
        } else {
            int alpha = Color.alpha(color);
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            hsv[2] *= by;
            return (alpha << 24) + (16777215 & Color.HSVToColor(hsv));
        }
    }

    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        return shiftColor(color, 0.9F);
    }

    @ColorInt
    public static int lightenColor(@ColorInt int color) {
        return shiftColor(color, 1.1F);
    }

    public static boolean isColorLight(@ColorInt int color) {
        double darkness = 1.0D - (0.299D * (double) Color.red(color) + 0.587D * (double) Color.green(color) + 0.114D * (double) Color.blue(color)) / 255.0D;
        return darkness < 0.4D;
    }

    @ColorInt
    public static int invertColor(@ColorInt int color) {
        int r = 255 - Color.red(color);
        int g = 255 - Color.green(color);
        int b = 255 - Color.blue(color);
        return Color.argb(Color.alpha(color), r, g, b);
    }

    @ColorInt
    public static int adjustAlpha(@ColorInt int color, @FloatRange(from = 0.0D, to = 1.0D) float factor) {
        int alpha = Math.round((float) Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    @ColorInt
    public static int withAlpha(@ColorInt int baseColor, @FloatRange(from = 0.0D, to = 1.0D) float alpha) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255.0F))) << 24;
        int rgb = 16777215 & baseColor;
        return a + rgb;
    }

    public static int blendColors(int color1, int color2, @FloatRange(from = 0.0D, to = 1.0D) float ratio) {
        float inverseRatio = 1.0F - ratio;
        float a = (float) Color.alpha(color1) * inverseRatio + (float) Color.alpha(color2) * ratio;
        float r = (float) Color.red(color1) * inverseRatio + (float) Color.red(color2) * ratio;
        float g = (float) Color.green(color1) * inverseRatio + (float) Color.green(color2) * ratio;
        float b = (float) Color.blue(color1) * inverseRatio + (float) Color.blue(color2) * ratio;
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    private ColorUtils() {
    }

    /**
     * color转RGB
     */
    public static int[] colorToRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color & 0xff0000) >> 16;
        rgb[1] = (color & 0x00ff00) >> 8;
        rgb[2] = color & 0x0000ff;
        return rgb;
    }

    /**
     * RGB转color
     */
    public static int rgbToColor(int r, int g, int b) {
        r = judgeRGB(r);
        g = judgeRGB(g);
        b = judgeRGB(b);
        return Color.rgb(r, g, b);
    }

    private static int judgeRGB(int colorValue) {
        if (colorValue > 255) colorValue = 255;
        if (colorValue < 0) colorValue = 0;
        return colorValue;
    }
}