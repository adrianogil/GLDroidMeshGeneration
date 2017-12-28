package com.gillabs.gldev.glandroidengine;

/**
 * Created by gil on 10/12/17.
 */
public class GLColor{
    private float[] mRGBA;

    public static GLColor Black = new GLColor(0.0f,0.0f,0.0f,1.0f);

    public GLColor(float r, float g, float b, float a) {
        mRGBA = new float[] {r,g,b,a};
    }

    public float R() { return mRGBA[0]; }
    public float G() { return mRGBA[1]; }
    public float B() { return mRGBA[2]; }
    public float A() { return mRGBA[3]; }
}
