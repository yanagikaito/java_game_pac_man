package entity;

import frame.FrameApp;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class OrangeGhost extends Entity {

    private GameWindow gameWindow;
    private Random random = new Random();
    private int actionLockCounter = 0;

    public OrangeGhost(GameWindow gameWindow) {
        super(gameWindow);
        direction = "right";
        speed = 2;
        loadGhostImages();
    }

    public void loadGhostImages() {
        try {

            orange_ghost_Image_up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-up-1.gif"));
            orange_ghost_Image_up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-up-2.gif"));
            orange_ghost_Image_down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-down-1.gif"));
            orange_ghost_Image_down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-down-2.gif"));
            orange_ghost_Image_left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-left-1.gif"));
            orange_ghost_Image_left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-left-2.gif"));
            orange_ghost_Image_right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-right-1.gif"));
            orange_ghost_Image_right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/orange/orange-ghost-right-2.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAction() {
        actionLockCounter++;
        // 60フレームごとに方向変更（1秒ごと）
        if (actionLockCounter >= 60) {
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = orange_ghost_Image_up1;
                } else if (spriteNum == 2) {
                    image = orange_ghost_Image_up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = orange_ghost_Image_down1;
                } else if (spriteNum == 2) {
                    image = orange_ghost_Image_down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = orange_ghost_Image_left1;
                } else if (spriteNum == 2) {
                    image = orange_ghost_Image_left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = orange_ghost_Image_right1;
                } else if (spriteNum == 2) {
                    image = orange_ghost_Image_right2;
                }
            }
        }
        if (image != null) {
            g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
        }
    }
}