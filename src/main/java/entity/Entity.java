package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x;
    public int y;
    public int speed;

    // 方向とスプライトの配列
    // 4方向 × 3スプライト
    public BufferedImage[][] sprites = new BufferedImage[4][3];
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collision = false;

    public static final String[] DIRECTIONS = {"up", "down", "left", "right"};
}