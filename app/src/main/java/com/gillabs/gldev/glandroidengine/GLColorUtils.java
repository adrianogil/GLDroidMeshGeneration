package com.gillabs.gldev.glandroidengine;

import java.util.List;

/**
 * Created by gil on 26/12/17.
 */
public class GLColorUtils {
    public static float[] ArrayFromList(List<GLColor> colors) {
        float[] colorData = new float[colors.size() * 4];

        for (int i = 0; i < colors.size(); i++) {
            colorData[4*i+0] = colors.get(i).R();
            colorData[4*i+1] = colors.get(i).G();
            colorData[4*i+2] = colors.get(i).B();
            colorData[4*i+3] = colors.get(i).A();
        }

        return colorData;
    }
}
