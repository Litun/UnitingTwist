package ru.litun.unitingtwist;

/**
 * Created by Litun on 09.04.2016.
 */
public class Scene implements Drawable {

    GameField field;

    Point point = new Point(0.2f, 0.2f, 0f);
    GameHexagon hexagon = new GameHexagon(point);

    public Scene() {
        this(new GameField());
    }

    public Scene(GameField field) {
        this.field = field;
    }

    @Override
    public void draw(float[] mvpMatrix) {

        hexagon.draw(mvpMatrix);

        field.draw(mvpMatrix);
    }
}
