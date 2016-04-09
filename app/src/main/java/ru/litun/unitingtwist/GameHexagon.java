package ru.litun.unitingtwist;

/**
 * Created by Litun on 09.04.2016.
 */
public class GameHexagon implements Drawable {
    private Point point;

    public GameHexagon(Point p) {
        point = p;
    }

    @Override
    public void draw(float[] mvpMatrix) {
        Hexagon hexagon = Hexagon.getInstance();
        hexagon.translate(point.getX(), point.getY());
        hexagon.rotate(0f);
        hexagon.draw(mvpMatrix);
    }
}
