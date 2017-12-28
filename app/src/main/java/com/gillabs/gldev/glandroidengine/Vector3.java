package com.gillabs.gldev.glandroidengine;

/**
 * Created by gil on 10/12/17.
 */
public class Vector3 {

    private float mZ;
    private float mY;
    private float mX;

    public Vector3(float x, float y, float z) {
        mX = x;
        mY = y;
        mZ = z;
    }

    public Vector3(Vector3 v)
    {
        mX = v.x();
        mY = v.y();
        mZ = v.z();
    }

    public float x() { return mX; }
    public float y() { return mY; }
    public float z() { return mZ; }

    public void add(Vector3 v)
    {
        mX += v.x();
        mY += v.y();
        mZ += v.z();
    }

    public static Vector3 add(Vector3 v1, Vector3 v2)
    {
        Vector3 result = new Vector3(v1);

        result.add(v2);

        return result;
    }
}
