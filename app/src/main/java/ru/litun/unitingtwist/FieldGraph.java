package ru.litun.unitingtwist;

/**
 * Created by Litun on 07.04.2016.
 */
public class FieldGraph {
    int gn;
    Point[][] points;

    public FieldGraph(int n) {
        gn = n * 2 - 1;
        points = new Point[gn][gn];
        for (int i = 0; i < gn; i++)
            for (int j = 0; j < gn; j++)
                points[i][j] = new Point((i - n + 1) - (j - n + 1) * 0.5f, (j - n + 1) * 0.866f, 0f);

        for (int i = 0; i < gn; i++)
            for (int j = 0; j < gn; j++) {
                Point point = points[i][j];
                point.setX(point.getX() * 0.3f);
                point.setY(point.getY() * 0.3f);
            }
    }

}
