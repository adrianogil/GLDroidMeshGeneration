package com.gillabs.gldev.glandroidengine;

/**
 * Created by gil on 10/12/17.
 */
public class GLTransform {

    private Vector3 mRotationAngles;
    private Vector3 mPosition;

    public Vector3 getRotationAngles() {
        return mRotationAngles;
    }

    public void setRotationAngles(Vector3 rotationAngles) {
        this.mRotationAngles = rotationAngles;
    }

    public Vector3 getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3 position) {
        this.mPosition = position;
    }

    public GLTransform() {
        mPosition = new Vector3(0f,0f,0f);
        mRotationAngles = new Vector3(0f,0f,0f);
    }
}
