package com.gillabs.gldev.gldroidmeshgeneration;

import android.content.Context;
import android.util.Log;

import com.gillabs.gldev.glandroidengine.GLColor;
import com.gillabs.gldev.glandroidengine.GLColorUtils;
import com.gillabs.gldev.glandroidengine.GLManager;
import com.gillabs.gldev.glandroidengine.GLMeshObject;
import com.gillabs.gldev.glandroidengine.GLScene;
import com.gillabs.gldev.glandroidengine.Vector3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gil on 23/12/17.
 */
public class SimpleTriangleGeneration {

    private static final String TAG = "SimpleTGeneration";

    public static void drawScene(Context context) {
        GLScene simpleTriangleScene = new GLScene("SimpleTriangleScene");

        GLMeshObject simpleTriangle = new GLMeshObject("simpleTriangle");
        drawTriangle(simpleTriangle, context);

        simpleTriangleScene.addObject(simpleTriangle);

        GLManager.GetInstance().setCurrentScene(simpleTriangleScene);
    }

    private static void drawTriangle(GLMeshObject simpleTriangle, Context context) {
        List<Vector3> vertices = new ArrayList<Vector3>();
        vertices.add(new Vector3(-0.5f, -0.25f, 0.0f));
        vertices.add(new Vector3(0.5f, -0.25f, 0.0f));
        vertices.add(new Vector3(0.0f, 0.559016994f, 0.0f));
        simpleTriangle.setVertices(vertices);

        List<GLColor> colors = new ArrayList<GLColor>();
        colors.add(new GLColor(1.0f, 1.0f, 1.0f, 1.0f));
        colors.add(new GLColor(0.5f, 0.5f, 0.5f, 1.0f));
        colors.add(new GLColor(0.0f, 0.0f, 0.0f, 1.0f));
        simpleTriangle.addCustomBuffer("a_Color", GLColorUtils.ArrayFromList(colors), 4);

        simpleTriangle.getTransform().setPosition(new Vector3(0f, -1f, 0f));
        simpleTriangle.getTransform().setRotationAngles(new Vector3(0f, 0f, 30f));

        simpleTriangle.generateMaterial();
        simpleTriangle.getMaterial().setVertexShaderSource(getVertexShader(context));
        simpleTriangle.getMaterial().setFragmentShaderSource(getFragmentShader(context));
    }

    protected static String getVertexShader(Context context)
    {
        return readFile("shaders/SimpleTriangle.vert", context);
    }

    protected static String getFragmentShader(Context context)
    {
        return readFile("shaders/SimpleTriangle.frag", context);
    }

    protected static String readFile(String path, Context context)
    {
        String text = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(path)));

            // do reading, usually loop until end of file reading
            String mLine;
            StringBuilder sb = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                //process line
                sb.append(mLine);
                sb.append("\n");
            }
            text = sb.toString();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        Log.d(TAG, text);

        return text;
    }
}
