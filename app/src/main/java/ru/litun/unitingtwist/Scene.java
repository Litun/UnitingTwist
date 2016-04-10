package ru.litun.unitingtwist;

import android.os.Handler;
import android.os.Looper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Litun on 09.04.2016.
 */
public class Scene implements Drawable {
    final Random r = new Random();
    GameField field;

    List<FlyingHexagon> flyingHexagons = new LinkedList<>();

    public Scene() {
        this(new GameField());
    }

    public Scene(final GameField field) {
        this.field = field;
//        final Handler handler = new Handler(Looper.getMainLooper());
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Point point = new Point(r.nextFloat() * 2 - 1, r.nextFloat() * 2 - 1, 0f);
//                GameHexagon hexagon = new GameHexagon(point);
//                hexagon.setColor(r.nextInt(6) + 1);
//                field.addGameHex(hexagon);
//                //hexagonList.add(hexagon);
//            }
//        };
//        handler.postDelayed(runnable, 2000);
//        handler.postDelayed(runnable, 3000);
//        handler.postDelayed(runnable, 5000);
//        handler.postDelayed(runnable, 8000);
//        handler.postDelayed(runnable, 12000);
//        handler.postDelayed(runnable, 13000);
//        handler.postDelayed(runnable, 15000);
//        handler.postDelayed(runnable, 18000);

        scheduleTask();
    }

    @Override
    public void draw(float[] mvpMatrix) {
        update();

        field.draw(mvpMatrix);

        for (FlyingHexagon hex : flyingHexagons) {
            GameHexagon h = hex.getHexagon();
            h.draw(mvpMatrix);
        }
    }

    private Date date;

    private void update() {
        if (date == null) {
            date = new Date();
        }
        Date currentDate = new Date();
        long delta = currentDate.getTime() - date.getTime();
        float k = delta / 1000f;
        date = currentDate;

        for (FlyingHexagon hexagon : flyingHexagons)
            hexagon.update(delta);
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    public void scheduleTask() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int x = r.nextInt(20);
                int y = r.nextInt(20);
                double k = 2 / Math.sqrt(x * x + y * y);
                Point point = new Point((float) (x * k), (float) (y * k), 0f);
                GameHexagon gameHexagon = new GameHexagon(point);
                final FlyingHexagon flyingHexagon = new FlyingHexagon(gameHexagon);
                flyingHexagon.setVector((float) -(x * k * 0.1), (float) -(y * k * 0.1));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        flyingHexagons.add(flyingHexagon);
                    }
                });
            }
        };
        timer.schedule(task, 5000, 10000);
    }
}
