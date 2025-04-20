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

    // red
    public BufferedImage red_ghost_Image_up1;
    public BufferedImage red_ghost_Image_up2;
    public BufferedImage red_ghost_Image_down1;
    public BufferedImage red_ghost_Image_down2;
    public BufferedImage red_ghost_Image_left1;
    public BufferedImage red_ghost_Image_left2;
    public BufferedImage red_ghost_Image_right1;
    public BufferedImage red_ghost_Image_right2;

    // blue
    public BufferedImage blue_ghost_Image_up1;
    public BufferedImage blue_ghost_Image_up2;
    public BufferedImage blue_ghost_Image_down1;
    public BufferedImage blue_ghost_Image_down2;
    public BufferedImage blue_ghost_Image_left1;
    public BufferedImage blue_ghost_Image_left2;
    public BufferedImage blue_ghost_Image_right1;
    public BufferedImage blue_ghost_Image_right2;

    // orange
    public BufferedImage orange_ghost_Image_up1;
    public BufferedImage orange_ghost_Image_up2;
    public BufferedImage orange_ghost_Image_down1;
    public BufferedImage orange_ghost_Image_down2;
    public BufferedImage orange_ghost_Image_left1;
    public BufferedImage orange_ghost_Image_left2;
    public BufferedImage orange_ghost_Image_right1;
    public BufferedImage orange_ghost_Image_right2;

    // pink
    public BufferedImage pink_ghost_Image_up1;
    public BufferedImage pink_ghost_Image_up2;
    public BufferedImage pink_ghost_Image_down1;
    public BufferedImage pink_ghost_Image_down2;
    public BufferedImage pink_ghost_Image_left1;
    public BufferedImage pink_ghost_Image_left2;
    public BufferedImage pink_ghost_Image_right1;
    public BufferedImage pink_ghost_Image_right2;

    // game over
    public BufferedImage game_over_Image_1;
    public BufferedImage game_over_Image_2;
    public BufferedImage game_over_Image_3;
    public BufferedImage game_over_Image_4;
    public BufferedImage game_over_Image_5;

    // 方向とスプライトの配列
    // 4方向 × 3スプライト
    public BufferedImage[][] sprites = new BufferedImage[4][3];
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int width = 16;
    public int height = 16;
    public int entityX = 0;
    public int entityY = 0;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(entityX, entityY, width, height);
    public boolean collision = false;

    public static final String[] DIRECTIONS = {"up", "down", "left", "right"};

    public Entity(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void setAction() {

    }

    public void update() {

        setAction();

        collision = false;
        gameWindow.getCollisionChecker().checkTile(this);
        gameWindow.getCollisionChecker().checkPlayer(this);

        if (collision == false) {

            switch (direction) {
                case "up" -> y -= speed;
                case "down" -> y += speed;
                case "right" -> x += speed;
                case "left" -> x -= speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_up1;
                } else if (spriteNum == 2) {
                    image = red_ghost_Image_up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_down1;
                } else if (spriteNum == 2) {
                    image = red_ghost_Image_down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_left1;
                } else if (spriteNum == 2) {
                    image = red_ghost_Image_left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_right1;
                } else if (spriteNum == 2) {
                    image = red_ghost_Image_right2;
                }
            }
        }
        if (image != null) {
            g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
        }
    }
}