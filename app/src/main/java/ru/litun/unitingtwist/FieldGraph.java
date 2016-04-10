package ru.litun.unitingtwist;

import android.opengl.Matrix;

/**
 * Created by Litun on 07.04.2016.
 */
public class FieldGraph {
    int gn;
    GraphPoint[][] points;
    GraphPoint center;
    public static final float SCALE = 0.1f;

    public FieldGraph(int n) {
        gn = n * 2 - 1;
        points = new GraphPoint[gn][gn];
        for (int i = 0; i < gn; i++)
            for (int j = 0; j < gn; j++) {
                Point point = new Point(((i - n + 1) - (j - n + 1) * 0.5f) * SCALE,
                        (j - n + 1) * 0.866f * SCALE, 0f);
                points[i][j] = new GraphPoint(i, j, point);
            }
        center = points[n-1][n-1];
    }

    private final float[] rotationMatrix = new float[16];

    public void rotate(float angle) {
        //TODO: fix 180 jump
        Matrix.setRotateM(rotationMatrix, 0, /*mAngle*/ angle, 0, 0, 1.0f);
        for (int i = 0; i < gn; i++)
            for (int j = 0; j < gn; j++) {
                GraphPoint po = points[i][j];
                Point p = po.getPoint();
                float[] point = new float[4];
                float[] previousPoint = new float[]{p.getDefaultX(), p.getDefaultY(), p.getDefaultZ(), 1};
                Matrix.multiplyMV(point, 0, rotationMatrix, 0, previousPoint, 0);
                //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
                p.setX(point[0]);
                p.setY(point[1]);
            }
    }

}
