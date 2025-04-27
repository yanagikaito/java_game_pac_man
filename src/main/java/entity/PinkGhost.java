package entity;

import frame.FrameApp;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class PinkGhost extends Entity {

    private GameWindow gameWindow;
    private Random random = new Random();
    private int actionLockCounter = 0;

    public PinkGhost(GameWindow gameWindow) {
        super(gameWindow);
        this.originalGhostType = PinkGhost.class;
        direction = "right";
        speed = 2;
        loadGhostImages();
    }

    public void loadGhostImages() {

        try {

            pink_ghost_Image_up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-up-1.gif"));
            pink_ghost_Image_up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-up-2.gif"));
            pink_ghost_Image_down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-down-1.gif"));
            pink_ghost_Image_down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-down-2.gif"));
            pink_ghost_Image_left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-left-1.gif"));
            pink_ghost_Image_left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-left-2.gif"));
            pink_ghost_Image_right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-right-1.gif"));
            pink_ghost_Image_right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/pink/pink-ghost-right-2.gif"));

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
                    image = pink_ghost_Image_up1;
                } else if (spriteNum == 2) {
                    image = pink_ghost_Image_up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = pink_ghost_Image_down1;
                } else if (spriteNum == 2) {
                    image = pink_ghost_Image_down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = pink_ghost_Image_left1;
                } else if (spriteNum == 2) {
                    image = pink_ghost_Image_left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = pink_ghost_Image_right1;
                } else if (spriteNum == 2) {
                    image = pink_ghost_Image_right2;
                }
            }
        }
        if (image != null) {
            g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);

            // デバッグ
//            g2.setColor(Color.GREEN);
//            g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}