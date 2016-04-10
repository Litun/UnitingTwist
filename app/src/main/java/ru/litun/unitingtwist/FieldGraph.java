package ru.litun.unitingtwist;

import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Litun on 07.04.2016.
 */
public class FieldGraph {
    int gn;
    private GraphPoint[][] points;
    private GraphPoint center;
    private Set<GraphPoint> opened = new TreeSet<>();
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
        center = points[n - 1][n - 1];
    }

    private final float[] rotationMatrix = new float[16];

    public void rotate(float angle) {
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

    public Set<GraphPoint> getOpened() {
        return opened;
    }

    public GraphPoint getCenter() {
        return center;
    }

    int[][] edges = new int[][]{
            new int[]{1, 0},
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{0, -1},
            new int[]{1, 1},
            new int[]{-1, -1}
    };

    public void recountOpens() {
        opened.clear();
        Queue<GraphPoint> queue = new LinkedList<>();
        queue.add(center);
        while (queue.size() > 0) {
            GraphPoint point = queue.poll();
            point.setVisited(true);
            if (!point.getHasObject()) {
                opened.add(point);
                continue;
            }
            int x = point.getX();
            int y = point.getY();
            for (int[] edge : edges) {
                GraphPoint nextPoint = points[x + edge[0]][y + edge[1]];
                if (!nextPoint.getVisited())
                    queue.add(nextPoint);
            }
        }
        resetVisits();
    }

    void resetVisits() {
        for (int i = 0; i < gn; i++)
            for (int j = 0; j < gn; j++)
                points[i][j].setVisited(false);
    }

    public void hexAdded(GraphPoint openPoint) {
        opened.remove(openPoint);

        int x = openPoint.getX();
        int y = openPoint.getY();
        for (int[] edge : edges) {
            GraphPoint nextPoint = points[x + edge[0]][y + edge[1]];
            if (!nextPoint.getHasObject())
                opened.add(nextPoint);
        }
    }
}
