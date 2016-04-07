package ru.litun.unitingtwist;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Litun on 20.03.2016.
 */
public class Hexagon {

    private final float scale = 0.15f;
    private float position[] = {0f, 0f, 0f};

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float hexagonCoords[] = {
            0.0f, 0.0f, 0.0f, // center
//            0.5f, 0.0f, 0.0f, // right
//            0.25f, 0.433f, 0.0f, // top right
//            -0.25f, 0.433f, 0.0f, // top left
//            -0.5f, 0.0f, 0.0f, // left
//            -0.25f, -0.433f, 0.0f, // bottom left
//            0.25f, -0.433f, 0.0f  // bottom right
            0f, -1f, 0f, //bottom
            -0.866f, -0.5f, 0f, //left bottom
            -0.866f, 0.5f, 0f, //left top
            0f, 1f, 0f, //top
            0.866f, 0.5f, 0f, //right top
            0.866f, -0.5f, 0f //right bottom
    };

    private final short drawOrder[] = {0, 1, 2, 2, 3, 0, 0, 3, 4, 4, 5, 0, 0, 5, 6, 6, 1, 0}; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    float color[] = {0.2f, 0.709803922f, 0.898039216f, 1.0f};

    ByteBuffer bb;

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Hexagon() {
        // initialize vertex byte buffer for shape coordinates
        bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                hexagonCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(hexagonCoords);
        vertexBuffer.position(0);


        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }

    public void setPosition(float[] position) {
        this.position = position;
        updateBuffer();
    }

    public void updateBuffer() {
        float[] coords = hexagonCoords.clone();
        for (int i = 0; i < coords.length; i++)
            coords[i] *= scale;
        for (int i = 0; i < coords.length; i += 3) {
            coords[i] += position[0];
            coords[i + 1] += position[1];
            coords[i + 2] += position[2];
        }
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     *                  this shape.
     */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
