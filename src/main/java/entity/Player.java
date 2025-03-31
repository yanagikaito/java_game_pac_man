package entity;

import frame.FrameApp;
import key.KeyHandler;
import window.GameWindow;

import java.awt.*;

public class Player extends Entity {
    GameWindow gameWindow;
    KeyHandler keyHandler;

    public Player(GameWindow gameWindow, KeyHandler keyHandler) {
        this.gameWindow = gameWindow;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {

        if (keyHandler.pacmanUp == true) {
            y -= speed;
        } else if (keyHandler.pacmanDown == true) {
            y += speed;
        } else if (keyHandler.pacmanRight == true) {
            x += speed;
        } else if (keyHandler.pacmanLeft == true) {
            x -= speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillRoundRect(x, y, FrameApp.createSize(), FrameApp.createSize(), FrameApp.createSize(), FrameApp.createSize());
    }
}
