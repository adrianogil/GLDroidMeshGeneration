package com.gillabs.gldev.glandroidengine;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by gil on 12/12/17.
 */
public class GLBuffer {

    public enum GLBufferType {
        ElementArrayBuffer,
        ArrayBuffer
    }

    private FloatBuffer mBufferData;
    /** How many bytes per float. */
    private final int mBytesPerFloat = 4;

    private int mDataSize;
    private GLBufferType mGLBufferType;


    public GLBuffer(GLBufferType gLBufferType) {
        this.mDataSize = 1;
        this.mGLBufferType = gLBufferType;
    }

    public GLBuffer(GLBufferType gLBufferType, int dataSize) {
        this.mDataSize = dataSize;
        this.mGLBufferType = gLBufferType;
    }

    public void createBuffer(float[] data)
    {
        mBufferData = ByteBuffer.allocateDirect(data.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mBufferData.put(data).position(0);
    }

    public void enableBuffer(int dataHandle)
    {
        // Pass in the buffer information
        mBufferData.position(0);
        GLES20.glVertexAttribPointer(dataHandle, mDataSize, GLES20.GL_FLOAT, false,
                0, mBufferData);
        GLES20.glEnableVertexAttribArray(dataHandle);
    }
}
