package treeset.extensions.utils;

import android.os.CountDownTimer;
import android.os.Handler;


/**
 * Created by daemontus on 11/04/14.
 */
public abstract class PauseTimer {

    private CountDownTimer timer;
    private long started;
    private long remaining;
    private long future;
    private long interval;

    private Handler threadHandler;

    public PauseTimer(final long millisInFuture, final long countDownInterval) {
        future = millisInFuture;
        interval = countDownInterval;
    }

    public abstract void onTick(long millisUntilFinished);
    public abstract void onFinish();

    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public PauseTimer start() {
        timer = new CountDownTimer(future, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (PauseTimer.this.timer == this) PauseTimer.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if (PauseTimer.this.timer == this) PauseTimer.this.onFinish();
            }
        };
        threadHandler = new Handler();
        timer.start();
        started = System.currentTimeMillis();
        remaining = future;
        return this;
    }

    public void pause() {
        timer.cancel();
        timer = null;
        remaining -= System.currentTimeMillis() - started;
    }

    public void resume() {
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                timer = new CountDownTimer(remaining, interval) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (PauseTimer.this.timer == this) PauseTimer.this.onTick(millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        if (PauseTimer.this.timer == this) PauseTimer.this.onFinish();
                    }
                };
                timer.start();
                started = System.currentTimeMillis();
            }
        });
    }
}
