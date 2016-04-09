package ru.litun.unitingtwist;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.*;

/**
 * Created by Litun on 20.03.2016.
 */
public class Hexagon {

    private static Hexagon instance;

    public static Hexagon init() {
        instance = new Hexagon();
        return instance;
    }

    public static Hexagon getInstance() {
        return instance;
    }

    private final float SCALE = 0.05f;

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
        initVertexBuffer();

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

    public void initVertexBuffer() {
        float[] coords = hexagonCoords.clone();
        for (int i = 0; i < coords.length; i++)
            coords[i] *= SCALE;
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
    }

    float[] scratch = new float[16];
    float[] mMoveMatrix = new float[16];

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     *                  this shape.
     */
    public void draw(float[] mvpMatrix) {
        Matrix.setIdentityM(mMoveMatrix, 0);
        Matrix.translateM(mMoveMatrix, 0, x, y, 0);
        Matrix.rotateM(mMoveMatrix, 0, /*mAngle*/ angle, 0, 0, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mvpMatrix, 0, mMoveMatrix, 0);

        // Add program to OpenGL environment
        glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        glUniform4fv(mColorHandle, 1, ColorUtils.greenColor, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        glUniformMatrix4fv(mMVPMatrixHandle, 1, false, scratch, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the square
        glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        glDisableVertexAttribArray(mPositionHandle);

        /*
        MMatrix = identity;
        MVPMatrix = VPMatrix * MMatrix;
        glUniformMatrix4fv(MVPHandle, 1, FALSE, MVPMatrix.ptr());
        glDrawArrays(GL_TRIANGLES, 0, 3); //draw triangle at 0,0,0

        MMatrix.translate(1,0,0);
        MVPMatrix = VPMatrix * MMatrix;
        glUniformMatrix4fv(MVPHandle, 1, FALSE, MVPMatrix.ptr());
        glDrawArrays(GL_TRIANGLES, 0, 3); //draw triangle at 1,0,0

        MMatrix.translate(1,0,0);
        MVPMatrix = VPMatrix * MMatrix;
        glUniformMatrix4fv(MVPHandle, 1, FALSE, MVPMatrix.ptr());
        glDrawArrays(GL_TRIANGLES, 0, 3); //draw triangle at 2,0,0

        repeat for as many objects as you want..
        This will leave you with three triangles, at (0,0,0), (1,0,0), and (2,0,0).
        */
    }

    float angle = 0f;
    float x = 0f, y = 0f;

    public void translate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(float angle) {
        this.angle = angle;
    }

    public void setColor(float[] color) {
        this.color = color;
    }
}
