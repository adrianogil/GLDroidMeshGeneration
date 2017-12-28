package com.gillabs.gldev.glandroidengine;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gil on 12/12/17.
 */
public class GLMeshObject extends GLObject {

    public enum GLDrawingMode
    {
        DRAW_ARRAYS,
        DRAW_ELEMENTS
    };

    private GLMaterial mMaterial;
    private GLDrawingMode mCurrentDrawingMode;

    private List<Vector3> mVertices;
    private List<Integer> mTriangles;
    private List<String> mAttribNames;
    private List<String> mUniformNames;
    private HashMap<String, GLBuffer> mBuffers;

    private String mBufferPositionName;

    private List<String> mCustomBuffersName;
    private List<float[]> mCustomBuffersData;
    private List<Integer> mCustomBuffersSize;

    private int mTotalCustomBuffer;

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];

    public GLMeshObject(String objectName) {
        super(objectName);

        mVertices = new ArrayList<Vector3>();
        mTriangles = new ArrayList<Integer>();
        mAttribNames = new ArrayList<String>();
        mUniformNames = new ArrayList<String>();

        mBuffers = new HashMap<String, GLBuffer>();

        mBufferPositionName = "aVertexPosition";

        mUniformNames.add("u_MVPMatrix");

        mCustomBuffersName = new ArrayList<String>();
        mCustomBuffersData = new ArrayList<float[]>();
        mCustomBuffersSize = new ArrayList<Integer>();

        mTotalCustomBuffer = 0;
    }

    /*** GETTERS/SETTERS ***/
    public GLMaterial getMaterial() {
        return mMaterial;
    }

    public void setMaterial(GLMaterial material) {
        this.mMaterial = material;
    }

    public GLDrawingMode getCurrentDrawingMode() {
        return mCurrentDrawingMode;
    }

    public void setCurrentDrawingMode(GLDrawingMode currentDrawingMode) {
        this.mCurrentDrawingMode = currentDrawingMode;
    }

    public List<Vector3> getVertices() {
        return mVertices;
    }

    public void setVertices(List<Vector3> vertices) {
        this.mVertices = vertices;
    }

    public List<Integer> getTriangles() {
        return mTriangles;
    }

    public void setTriangles(List<Integer> triangles) {
        this.mTriangles = triangles;
    }

    public void addCustomBuffer(String bufferName, float[] bufferData, int bufferComponentSize)
    {
        mCustomBuffersName.add(bufferName);
        mCustomBuffersData.add(bufferData);
        mCustomBuffersSize.add(bufferComponentSize);

        mTotalCustomBuffer++;
    }

    @Override
    public void draw() {
        super.draw();

        enableBuffers();

        mMaterial.enableMaterial();

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, GLCamera.mainCamera().getViewMatrix(), 0, getModelMatrix(), 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, GLCamera.mainCamera().getProjectionMatrix(), 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMaterial.getUniformHandle("u_MVPMatrix")
                , 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }

    @Override
    public void onFrameStart() {
        super.onFrameStart();


    }

    @Override
    public void onStart() {
        super.onStart();

        generateMaterial();

        createBuffers();

        mMaterial.onStart();
    }

    protected void createBuffers()
    {
        // Vertices
        mBuffers.put(mBufferPositionName,
                new GLBuffer(GLBuffer.GLBufferType.ArrayBuffer, 3)
                );
        mBuffers.get(mBufferPositionName).createBuffer(Vector3Utils.ArrayFromList(mVertices));

        // Triangles
//        if (mTriangles.size() > 0) {
//            mBuffers.put("triangles",
//                new GLBuffer(GLBuffer.GLBufferType.ElementArrayBuffer)
//                    );
// TODO: Fix conversion from int to float
//            mBuffers.get("triangles").createBuffer(mTriangles.toArray());
//        }

        // Custom buffers
        for (int i = 0; i < mTotalCustomBuffer; i++) {
            mBuffers.put(mCustomBuffersName.get(i),
                    new GLBuffer(GLBuffer.GLBufferType.ArrayBuffer, mCustomBuffersSize.get(i))
            );
            mBuffers.get(mCustomBuffersName.get(i))
                    .createBuffer(mCustomBuffersData.get(i));
        }
    }

    protected void enableBuffers()
    {
        mBuffers.get(mBufferPositionName).enableBuffer(
                mMaterial.getAttribHandle(mBufferPositionName)
        );


        // Enable custom buffers from Attrib list
        for (int i = 0; i < mTotalCustomBuffer; i++) {
            mBuffers.get(mCustomBuffersName.get(i)).enableBuffer(
                    mMaterial.getAttribHandle(mCustomBuffersName.get(i))
            );
        }
    }

    public void generateMaterial()
    {
        if (mMaterial == null)
        {
            mMaterial = new GLMaterial(mAttribNames, mUniformNames);
        }
    }
}
