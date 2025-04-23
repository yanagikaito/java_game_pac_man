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
        // 初期状態は右向き、速度は通常と同様（必要に応じて変更）
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // frightened モードが一定時間経過した場合、通常状態に戻すための処理を呼び出す
        long elapsed = System.currentTimeMillis() - frightenedStartTime;
        if (elapsed >= frightenedDuration) {
            // frightened モード終了：GameWindow 側に戻す処理を依頼
            gameWindow.revertGhostFromFrightened(this);
            return;
        }

        // ランダムな移動のため setAction() を実行
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
        // 約1秒ごとにランダムな方向へ変更
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
        // 警告状態：残り時間が1秒未満の場合
        long remaining = frightenedDuration - (System.currentTimeMillis() - frightenedStartTime);
        boolean warning = (remaining < 1000);

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = blue_frightened_up1;
                } else {
                    image = blue_frightened_up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = blue_frightened_down1;
                } else {
                    image = blue_frightened_down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = blue_frightened_left1;
                } else {
                    image = blue_frightened_left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = blue_frightened_right1;
                } else {
                    image = blue_frightened_right2;
                }
            }
        }

        if (image != null) {
            g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
        }
    }

    public Class<?> getOriginalGhostType() {
        return originalGhostType;
    }
}