package entity;

import frame.FrameApp;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class BlueFrightened extends Entity {

    private GameWindow gameWindow;
    private Random random = new Random();
    private int actionLockCounter = 0;

    // フライトモード用のタイマー
    private long frightenedStartTime;
    private final long frightenedDuration = 5000;
    private Class<?> originalGhostType;

    public BlueFrightened(GameWindow gameWindow, Class<?> originalType) {
        super(gameWindow);
        this.gameWindow = gameWindow;
        this.originalGhostType = originalType;
        direction = "right";
        speed = 2;
        loadGhostImages();
        frightenedStartTime = System.currentTimeMillis();
    }

    public void loadGhostImages() {
        try {
            // 青い frightened 状態用画像
            blue_frightened_up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-up-1.gif"));
            blue_frightened_up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-up-2.gif"));
            blue_frightened_down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-down-1.gif"));
            blue_frightened_down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-down-2.gif"));
            blue_frightened_left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-left-1.gif"));
            blue_frightened_left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-left-2.gif"));
            blue_frightened_right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-right-1.gif"));
            blue_frightened_right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/blue/blue-frightened-right-2.gif"));

            white_frightened_up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-up-1.gif"));
            white_frightened_up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-up-2.gif"));
            white_frightened_down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-down-1.gif"));
            white_frightened_down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-down-2.gif"));
            white_frightened_left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-left-1.gif"));
            white_frightened_left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-left-2.gif"));
            white_frightened_right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-right-1.gif"));
            white_frightened_right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("frightened/white/white-frightened-right-2.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        long elapsed = System.currentTimeMillis() - frightenedStartTime;
        if (elapsed >= frightenedDuration) {
            gameWindow.revertGhostFromFrightened(this);
            return;
        }

        setAction();
        collision = false;
        gameWindow.getCollisionChecker().checkTile(this);
        gameWindow.getCollisionChecker().checkPlayer(this);
        if (!collision) {
            switch (direction) {
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
                case "right":
                    x += speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1 ? 2 : 1);
            spriteCounter = 0;
        }
    }

    @Override
    public void setAction() {
        actionLockCounter++;
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
        long elapsed = System.currentTimeMillis() - frightenedStartTime;
        long remaining = frightenedDuration - elapsed;
        boolean flash = false;
        if (remaining < 4000) {
            flash = ((System.currentTimeMillis() / 250) % 2) == 0;
        }

        switch (direction) {
            case "up" -> image = flash ? white_frightened_up1 : blue_frightened_up1;
            case "down" -> image = flash ? white_frightened_down1 : blue_frightened_down1;
            case "left" -> image = flash ? white_frightened_left1 : blue_frightened_left1;
            case "right" -> image = flash ? white_frightened_right1 : blue_frightened_right1;
        }

        if (image != null) {
            g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
        }
    }
}