package ru.litun.unitingtwist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Litun on 07.04.2016.
 */
public class GameField {

    public static final int DIM = 3;
    FieldGraph graph = new FieldGraph(DIM);

    private Hexagon mHexagon;
    private Hexagon mHexagon2;
    private Hexagon mHexagon3;
    private Hexagon mHexagon4;

    List<Hexagon> hexagons = new ArrayList<>((DIM * 2 - 1) ^ 2);

    public GameField() {
        for (int i = 0; i < DIM * 2 - 1; i++)
            for (int j = 0; j < DIM * 2 - 1; j++) {
                Hexagon hexagon = new Hexagon();
                hexagon.setPosition(graph.points[i][j].asArray());
                hexagons.add(hexagon);
            }
        //mHexagon = new Hexagon(mProgram);
//        mHexagon = new Hexagon();
//        mHexagon2 = new Hexagon();
//        mHexagon2.setPosition(new float[]{0f, 0f, 0f});
//
//        mHexagon3 = new Hexagon();
//        mHexagon3.setPosition(new float[]{0.2f, 0.2f, 0f});
//
//        mHexagon4 = new Hexagon();
//        mHexagon4.setPosition(new float[]{0.2f, -0.2f, 0f});
    }

    public void draw(float[] mMVPMatrix) {
        //mHexagon.draw(mMVPMatrix);
//        mHexagon2.draw(mMVPMatrix);
//        mHexagon3.draw(mMVPMatrix);
//        mHexagon4.draw(mMVPMatrix);
        for (Hexagon h : hexagons)
            h.draw(mMVPMatrix);
    }
}
