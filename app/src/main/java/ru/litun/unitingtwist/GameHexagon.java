package ru.litun.unitingtwist;

/**
 * Created by Litun on 09.04.2016.
 */
public class GameHexagon implements Drawable {
    private Point point;
    private float angle = 0f;

    public GameHexagon(Point p) {
        point = p;
    }

    @Override
    public void draw(float[] mvpMatrix) {
        Hexagon hexagon = Hexagon.getInstance();
        hexagon.rotate(angle);
        hexagon.translate(point.getX(), point.getY());
        //hexagon.setColor();
        hexagon.draw(mvpMatrix);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
