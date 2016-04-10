package ru.litun.unitingtwist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Litun on 07.04.2016.
 */
public class GameField {

    public static final int DIM = 7;
    FieldGraph graph = new FieldGraph(DIM);

    List<GameHexagon> hexagons = new ArrayList<>((DIM * 2 - 1) ^ 2);

    public GameField() {
        GraphPoint center = graph.getCenter();
        GameHexagon hexagon = new GameHexagon(center.getPoint());
        center.setHasObject(true);
        graph.recountOpens();
        hexagons.add(hexagon);
    }

    public void draw(float[] mMVPMatrix) {
        for (GameHexagon h : hexagons)
            h.draw(mMVPMatrix);
    }

    public void newUp(float x, float y) {
        double atan = Math.atan2(y, x);
        float angle = (float) (atan / Math.PI * 180);
        graph.rotate(angle);
        for (GameHexagon h : hexagons)
            h.setAngle(angle);
    }

    public void addGameHex(GameHexagon hexagon, GraphPoint openPoint) {
        hexagons.add(hexagon);
        hexagon.newPoint(openPoint.getPoint());
        openPoint.setHasObject(true);
        graph.hexAdded(openPoint);
    }
}
