package ru.litun.unitingtwist;

import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Timer;

/**
 * Created by Litun on 13.04.2016.
 */
public class Engine {
    private static int FPS = 60;
    private static int MIN_DELTA_TIME = 1000 / FPS;
    private final Scene scene;
    private final GLSurfaceView surface;
    private final Handler handler = new Handler(Looper.getMainLooper());

    class LoopThread extends Thread {
        private boolean running = false;
        private boolean interrupted = false;

        Date date;

        @Override
        public void run() {
            date = new Date();
            while (!interrupted)
                if (running) {
                    Date newDate = new Date();
                    if (MIN_DELTA_TIME > newDate.getTime() - date.getTime())
                        try {
                            Thread.sleep(MIN_DELTA_TIME - newDate.getTime() + date.getTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    date = newDate;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            update();
                        }
                    });
                } else try {
                    Thread.sleep(MIN_DELTA_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        public void updated() {
            date = new Date();
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public void preInterrupt() {
            interrupted = true;
        }
    }

    LoopThread thread;

    public Engine(@NonNull Scene scene, @NonNull GLSurfaceView surface) {

        this.scene = scene;
        this.surface = surface;
    }

    public void create() {
        thread = new LoopThread();
        thread.start();
    }

    public void resume() {
        if (thread != null)
            thread.setRunning(true);
        scene.resume();
    }

    public void pause() {
        if (thread != null)
            thread.setRunning(false);
        scene.pause();
    }

    public void destroy() {
        thread.preInterrupt();
        thread.interrupt();
        thread = null;
    }

    public void forceUpdate() {
        update();
        thread.updated();
    }

    private void update() {
        scene.update();
        surface.requestRender();
    }
}
