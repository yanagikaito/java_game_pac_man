package entity;

import frame.FrameApp;
import window.GameWindow;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    private GameWindow gameWindow;
    public int x;
    public int y;
    public int speed;

    // 方向とスプライトの配列
    // 4方向 × 3スプライト
    public BufferedImage[][] sprites = new BufferedImage[4][3];
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public boolean collision = false;

    public static final String[] DIRECTIONS = {"up", "down", "left", "right"};

    public Entity(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (spriteNum != -1) {
            image = sprites[spriteNum][spriteNum - 1];
        }
        g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
    }
}