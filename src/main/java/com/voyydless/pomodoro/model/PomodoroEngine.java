package com.voyydless.pomodoro.model;

import java.util.Timer;
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

