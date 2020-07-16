package com.example.minitiktok.video;

public class PauseVideoEvent {
    private boolean playOrPause;

    public PauseVideoEvent(boolean playOrPause) {
        this.playOrPause = playOrPause;
    }

    public boolean isPlayOrPause() {
        return playOrPause;
    }
}
