package ru.litun.unitingtwist;

/**
 * Created by Litun on 09.04.2016.
 */
public interface Drawable {
    void update(long deltaTime);

    void draw(float[] mvpMatrix);
}
