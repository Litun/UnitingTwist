package ru.litun.unitingtwist;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

/**
 * Created by Litun on 10.04.2016.
 */
public class ColorUtils {
    private static float[] greyColor;
    private static float[] redColor;
    //public static float[] orangeColor;
    private static float[] yellowColor;
    private static float[] blueColor;
    private static float[] oceanColor;
    private static float[] greenColor;

    private static float[][] colors;

    public static void init(Context context) {
        int grey = ContextCompat.getColor(context, R.color.grey);
        int red = ContextCompat.getColor(context, R.color.red);
        //int orange = ContextCompat.getColor(context, R.color.orange);
        int yellow = ContextCompat.getColor(context, R.color.yellow);
        int blue = ContextCompat.getColor(context, R.color.blue);
        int ocean = ContextCompat.getColor(context, R.color.ocean);
        int green = ContextCompat.getColor(context, R.color.green);
        greyColor = new float[]{Color.red(grey), Color.green(grey), Color.blue(grey), Color.alpha(grey)};
        redColor = new float[]{Color.red(red), Color.green(red), Color.blue(red), Color.alpha(red)};
        //orangeColor = new float[]{Color.red(orange), Color.green(orange), Color.blue(orange), Color.alpha(orange)};
        yellowColor = new float[]{Color.red(yellow), Color.green(yellow), Color.blue(yellow), Color.alpha(yellow)};
        blueColor = new float[]{Color.red(blue), Color.green(blue), Color.blue(blue), Color.alpha(blue)};
        oceanColor = new float[]{Color.red(ocean), Color.green(ocean), Color.blue(ocean), Color.alpha(ocean)};
        greenColor = new float[]{Color.red(green), Color.green(green), Color.blue(green), Color.alpha(green)};
        for (int i = 0; i < greyColor.length; i++) {
            greyColor[i] /= 256f;
            redColor[i] /= 256f;
            //orangeColor[i] /= 256f;
            yellowColor[i] /= 256f;
            blueColor[i] /= 256f;
            oceanColor[i] /= 256f;
            greenColor[i] /= 256f;
        }

        colors = new float[][]{
                greyColor,
                redColor,
                //orangeColor,
                yellowColor,
                blueColor,
                oceanColor,
                greenColor
        };
    }

    public static float[] getColor(int n) {
        return colors[n % colors.length];
    }

    public static int colorsCount() {
        return colors.length;
    }
}
