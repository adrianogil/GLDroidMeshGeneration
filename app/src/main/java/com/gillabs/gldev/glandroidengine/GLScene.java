package com.gillabs.gldev.glandroidengine;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gil on 10/12/17.
 */
public class GLScene {

    private boolean mTransparentMode;
    private GLColor mBackgroundColor;
    private String mSceneName;
    private List<GLObject> mObjects;

    public GLScene(String sceneName)
    {
        this.mSceneName = sceneName;
        this.mObjects = new ArrayList<GLObject>(0);
        this.mBackgroundColor = GLColor.Black; // Clear to black, fully opaque
        this.mTransparentMode = false;
    }

    public void addObject(GLObject obj)
    {
        mObjects.add(obj);
    }

    public void onStart()
    {
        // Set the background clear color to black
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Use culling to remove back faces
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        // Enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        GLCamera.mainCamera().onStart();

        for (int i = 0; i < mObjects.size(); i++) {
            mObjects.get(i).onStart();
        }
    }

    public void draw()
    {
        onSceneFrameBegin();

        for (int i = 0; i < mObjects.size(); i++) {
            mObjects.get(i).draw();
        }
    }

    private void onSceneFrameBegin() {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < mObjects.size(); i++) {
            mObjects.get(i).onFrameStart();
        }
    }
}
