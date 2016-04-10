package ru.litun.unitingtwist;

import java.util.Date;

/**
 * Created by Litun on 10.04.2016.
 */
public class FlyingHexagon {
    GameHexagon hexagon;
    private float vectorX = 0f,
            vectorY = 0f;

    public FlyingHexagon(GameHexagon h) {
        hexagon = h;
    }

    public GameHexagon getHexagon() {
        return hexagon;
    }

    public void update(long deltaTime) {
        float k = deltaTime / 1000f;

        hexagon.move(vectorX * k, vectorY * k);
    }

    public void setVector(float x, float y) {
        vectorX = x;
        vectorY = y;
    }
}
