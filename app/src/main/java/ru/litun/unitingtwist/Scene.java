package ru.litun.unitingtwist;

import java.util.Date;

/**
 * Created by Litun on 09.04.2016.
 */
public class Scene {
    private GameField field;

    public Scene(final GameField field) {
        this.field = field;
    }

    public void draw(float[] mvpMatrix) {
        field.draw(mvpMatrix);
    }

    private Date date;

    void update() {
        if (date != null) {
            Date currentDate = new Date();
            long delta = currentDate.getTime() - date.getTime();
            date = currentDate;
            field.update(delta);
        }
    }

    void resume() {
        date = new Date();
        field.startGenerating();
    }

    void pause() {
        date = null;
        field.stopGenerating();
    }
}
