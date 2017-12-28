package com.gillabs.gldev.glandroidengine;

import java.util.List;

/**
 * Created by gil on 23/12/17.
 */
public class Vector3Utils {

    public static float[] ArrayFromList(List<Vector3> vertices) {
        float[] verticesArray = new float[vertices.size() * 3];

        for (int i = 0; i < vertices.size(); i++) {
            verticesArray[3*i+0] = vertices.get(i).x();
            verticesArray[3*i+1] = vertices.get(i).y();
            verticesArray[3*i+2] = vertices.get(i).z();
        }
        
        return verticesArray;
    }
}
