package ui;

import window.GameWindow;

import java.awt.*;

public class ScorePopup {

    private GameWindow gameWindow;
    public int x;
    public int y;
    private int scoreValue;
    private long displayStartTime;
    private final long displayDuration = 1000;

    public ScorePopup(GameWindow gameWindow, int x, int y, int scoreValue) {
        this.gameWindow = gameWindow;
        this.x = x;
        this.y = y;
        this.scoreValue = scoreValue;
        this.displayStartTime = System.currentTimeMillis();
    }

    public void draw(Graphics2D g2) {
        long elapsed = System.currentTimeMillis() - displayStartTime;
        if (elapsed < displayDuration) {
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.drawString(String.valueOf(scoreValue), x, y);
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - displayStartTime > displayDuration;
    }
}