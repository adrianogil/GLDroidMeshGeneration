package com.gillabs.gldev.glandroidengine;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Created by gil on 12/12/17.
 */
public class GLCamera {

    /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];

    public float[] getViewMatrix() { return mViewMatrix; }
    public float[] getProjectionMatrix() { return mProjectionMatrix; }

    private static GLCamera mMainCamera = null;
    public static GLCamera mainCamera()
    {
        if (mMainCamera == null)
        {
            mMainCamera = new GLCamera();
        }

        return mMainCamera;
    }

    public void onViewportChanged(int width, int height)
    {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void onStart() {
        // Position the eye in the front of the origin
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = -0.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set out up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    }
}
