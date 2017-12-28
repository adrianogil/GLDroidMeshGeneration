package com.gillabs.gldev.glandroidengine;

import android.opengl.GLES20;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gil on 12/12/17.
 */
public class GLMaterial {
    private static final String TAG = "GLMaterial";
    private List<String> mUniformLocationsString;
    private List<String> mAttribLocationsString;

    String mVertexShaderSource;
    String mFragmentShaderSource;

    int mShaderProgramHandle;

    private HashMap<String, Integer> mAttribLocations;
    private HashMap<String, Integer> mUniformLocations;

    public GLMaterial(List<String> attribNames, List<String> uniformNames) {

        mAttribLocationsString = attribNames;
        mUniformLocationsString = uniformNames;

        mAttribLocations = new HashMap<String, Integer>();
        mUniformLocations = new HashMap<String, Integer>();
    }

    public String getVertexShaderSource() {
        return mVertexShaderSource;
    }

    public void setVertexShaderSource(String vertexShaderSource) {
        this.mVertexShaderSource = vertexShaderSource;
    }

    public String getFragmentShaderSource() {
        return mFragmentShaderSource;
    }

    public void setFragmentShaderSource(String fragmentShaderSource) {
        this.mFragmentShaderSource = fragmentShaderSource;
    }

    public int getAttribHandle(String attribName)
    {
        if (mAttribLocations.containsKey(attribName))
        {
            return mAttribLocations.get(attribName);
        }

        return -1;
    }

    public int getUniformHandle(String uniformName)
    {
        if (mUniformLocations.containsKey(uniformName))
        {
            return mUniformLocations.get(uniformName);
        }

        return -1;
    }

    public void onStart()
    {
        final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, mVertexShaderSource);
        final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, mFragmentShaderSource);

        String[] attrStrings = new String[mAttribLocationsString.size()];

        mShaderProgramHandle = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                mAttribLocationsString.toArray(attrStrings));

        String currentHandleName;

        for (int i = 0; i < mAttribLocationsString.size(); i++) {
            currentHandleName = mAttribLocationsString.get(i);

            mAttribLocations.put(currentHandleName,
                    GLES20.glGetAttribLocation(mShaderProgramHandle, currentHandleName)
                    );
        }

        for (int i = 0; i < mUniformLocationsString.size(); i++) {
            currentHandleName = mUniformLocationsString.get(i);

            mUniformLocations.put(currentHandleName,
                    GLES20.glGetUniformLocation(mShaderProgramHandle, currentHandleName)
            );
        }
    }

    public void enableMaterial()
    {
        GLES20.glUseProgram(mShaderProgramHandle);
    }

    /**
     * Helper function to compile a shader.
     *
     * @param shaderType The shader type.
     * @param shaderSource The shader source code.
     * @return An OpenGL handle to the shader.
     */
    private int compileShader(final int shaderType, final String shaderSource)
    {
        int shaderHandle = GLES20.glCreateShader(shaderType);

        if (shaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderSource);

            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0)
        {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    /**
     * Helper function to compile and link a program.
     *
     * @param vertexShaderHandle An OpenGL handle to an already-compiled vertex shader.
     * @param fragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
     * @param attributes Attributes that need to be bound to the program.
     * @return An OpenGL handle to the program.
     */
    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes)
    {
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0)
        {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            if (attributes != null)
            {
                final int size = attributes.length;
                for (int i = 0; i < size; i++)
                {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0)
        {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }
}
