package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x;
    public int y;
    public int speed;

    public BufferedImage pacmanUp1;
    public BufferedImage pacmanUp2;
    public BufferedImage pacmanUp3;
    public BufferedImage pacmanDown1;
    public BufferedImage pacmanDown2;
    public BufferedImage pacmanDown3;
    public BufferedImage pacmanRight1;
    public BufferedImage pacmanRight2;
    public BufferedImage pacmanRight3;
    public BufferedImage pacmanLeft1;
    public BufferedImage pacmanLeft2;
    public BufferedImage pacmanLeft3;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
