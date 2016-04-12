package ru.litun.unitingtwist;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Litun on 09.04.2016.
 */
public class Scene {
    GameField field;

    public Scene() {
        this(new GameField());
    }

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
