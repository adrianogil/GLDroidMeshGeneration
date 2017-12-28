package com.gillabs.gldev.glandroidengine;

import android.opengl.Matrix;

/**
 * Created by gil on 10/12/17.
 */
public class GLObject {

    private GLTransform mTransform;
    private String mObjectName;

    /**
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    public GLObject(String objectName) {
        mObjectName = objectName;
        mTransform = new GLTransform();
    }

    public void onStart()
    {

    }

    public void draw() {

    }

    public void onFrameStart() {

    }

    public GLTransform getTransform() {
        return mTransform;
    }

    public float[] getModelMatrix()
    {
        // Draw one translated a bit down and rotated to be flat on the ground.
        Matrix.setIdentityM(mModelMatrix, 0);

        Matrix.translateM(mModelMatrix, 0,
                mTransform.getPosition().x(),
                mTransform.getPosition().y(),
                mTransform.getPosition().z());

        Matrix.rotateM(mModelMatrix, 0, mTransform.getRotationAngles().x(),
                1.0f, 0.0f, 0.0f);
        Matrix.rotateM(mModelMatrix, 0, mTransform.getRotationAngles().y(),
                0.0f, 1.0f, 0.0f);
        Matrix.rotateM(mModelMatrix, 0, mTransform.getRotationAngles().z(),
                0.0f, 0.0f, 1.0f);

        return mModelMatrix;
    }
}
