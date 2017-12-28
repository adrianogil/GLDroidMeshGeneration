package com.gillabs.gldev.glandroidengine;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.gillabs.gldev.gldroidmeshgeneration.MainActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by gil on 10/12/17.
 */
public class GLManager implements GLSurfaceView.Renderer {

    private GLScene mCurrentScene = null;

    private static GLManager mInstance = null;

    private boolean alreadyStarted = false;

    private GLManager(Context context) {

    }

    public static GLManager GetInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new GLManager(context);
        }

        return mInstance;
    }

    public static GLManager GetInstance()
    {
        return mInstance;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {

        GLCamera.mainCamera().onViewportChanged(width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLTime.OnFrameStart();

        if (mCurrentScene != null)
        {
            if (!alreadyStarted)
            {
                mCurrentScene.onStart();
                alreadyStarted = true;
            }
            mCurrentScene.draw();
        }
    }

    public GLScene getCurrentScene() {
        return mCurrentScene;
    }

    public void setCurrentScene(GLScene currentScene) {
        this.mCurrentScene = currentScene;
    }
}
