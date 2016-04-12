package ru.litun.unitingtwist;

import android.support.annotation.NonNull;

/**
 * Created by Litun on 12.04.2016.
 */
public class GraphGameHexagon extends GameHexagonContainer implements Comparable<GraphGameHexagon> {
    private int i;
    private int j;
    boolean visited = false;
    Point graphPoint;

    public GraphGameHexagon(int i, int j, Point p) {

        this.i = i;
        this.j = j;
        graphPoint = p;
    }

    @Override
    public void setHexagon(GameHexagon hexagon) {
        super.setHexagon(hexagon);
        hexagon.newPoint(graphPoint);
    }

    public float distance(Point other) {
        return (float) Math.sqrt((other.getX() - graphPoint.getX()) * (other.getX() - graphPoint.getX())
                + (other.getY() - graphPoint.getY()) * (other.getY() - graphPoint.getY()));
    }

    public Point getPoint() {
        return graphPoint;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean hasHexagon() {
        return getHexagon() != null;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public int compareTo(@NonNull GraphGameHexagon other) {
        if (i > other.i) return 1;
        else if (i < other.i) return -1;
        else if (j > other.j) return 1;
        else if (j < other.j) return -1;
        else return 0;
    }
}
