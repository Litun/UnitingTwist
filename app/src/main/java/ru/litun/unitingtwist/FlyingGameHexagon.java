package ru.litun.unitingtwist;

/**
 * Created by Litun on 12.04.2016.
 */
public class FlyingGameHexagon extends GameHexagonContainer {

    private float vectorX = 0f,
            vectorY = 0f;

    public FlyingGameHexagon(GameHexagon h) {
        hexagon = h;
    }

    @Override
    public void update(long deltaTime) {
        float k = deltaTime / 1000f;

        hexagon.move(vectorX * k, vectorY * k);
    }

    public void setVector(float x, float y) {
        vectorX = x;
        vectorY = y;
    }
}
