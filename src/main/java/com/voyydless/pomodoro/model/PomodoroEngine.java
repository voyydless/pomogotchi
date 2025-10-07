package com.voyydless.pomodoro.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class PomodoroEngine {
    private static final int WORK_MINUTES = 25;
    private static final int SHORT_BREAK_MINUTES = 5;
    private static final int LONG_BREAK_MINUTES = 15;
    private static final int SESSIONS_BEFORE_LONG_BREAK = 4;

    private TimerState currentState;
    private int remainingSeconds;
    private int completedSessions;
    private Timer timer;
    private Consumer<String> onTimeUpdate;

    public PomodoroEngine(Consumer<String> onTimeUpdate) {
        this.onTimeUpdate = onTimeUpdate;
        this.currentState = TimerState.STOPPED;
        this.remainingSeconds = WORK_MINUTES * 60;
        this.completedSessions = 0;
        updateDisplay();
    }

    public void start() {
        if (currentState == TimerState.STOPPED) {
            currentState = TimerState.WORK;
            remainingSeconds = WORK_MINUTES * 60;
        }
        if (timer != null)
            timer.cancel();

        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingSeconds--;
                updateDisplay();
                if (remainingSeconds <=0)
                    transitionToNextState();
            }
        }, 1000, 1000);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void reset() {
        stop();
        currentState = TimerState.STOPPED;
        completedSessions = 0;
        remainingSeconds = WORK_MINUTES * 60;
        updateDisplay();
    }

    private void transitionToNextState() {
        stop();
        if (currentState == TimerState.WORK) {
            completedSessions++;
            if (completedSessions % SESSIONS_BEFORE_LONG_BREAK == 0) {
                currentState = TimerState.LONG_BREAK;
                remainingSeconds = LONG_BREAK_MINUTES * 60;
            } else {
                currentState = TimerState.SHORT_BREAK;
                remainingSeconds = SHORT_BREAK_MINUTES * 60;
            }
        } else {
            currentState = TimerState.WORK;
            remainingSeconds = WORK_MINUTES * 60;
        }
        start();
    }

    private void updateDisplay() {
        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        String message = "State: " + currentState + " | Time: " + formattedTime + " | Sessions: " + completedSessions;

        if (onTimeUpdate != null) {
            onTimeUpdate.accept(message);
        }
    }
}

