package ru.litun.unitingtwist;

/**
 * Created by Litun on 12.04.2016.
 */
public class GameHexagonContainer implements Drawable {
    protected GameHexagon hexagon;

    public GameHexagon getHexagon() {
        return hexagon;
    }

    public void setHexagon(GameHexagon hexagon) {
        this.hexagon = hexagon;
    }

    public Point getPoint() {
        return hexagon.getPoint();
    }

    @Override
    public void update(long deltaTime) {
        //do nothing
    }

    @Override
    public void draw(float[] mvpMatrix) {
        if (hexagon != null)
            hexagon.draw(mvpMatrix);
    }
}
