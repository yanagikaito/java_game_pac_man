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

    // frightened
    public BufferedImage blue_frightened_up1;
    public BufferedImage blue_frightened_up2;
    public BufferedImage blue_frightened_down1;
    public BufferedImage blue_frightened_down2;
    public BufferedImage blue_frightened_left1;
    public BufferedImage blue_frightened_left2;
    public BufferedImage blue_frightened_right1;
    public BufferedImage blue_frightened_right2;

    public BufferedImage white_frightened_up1;
    public BufferedImage white_frightened_up2;
    public BufferedImage white_frightened_down1;
    public BufferedImage white_frightened_down2;
    public BufferedImage white_frightened_left1;
    public BufferedImage white_frightened_left2;
    public BufferedImage white_frightened_right1;
    public BufferedImage white_frightened_right2;

    // 方向とスプライトの配列
    // 4方向 × 3スプライト
    public BufferedImage[][] sprites = new BufferedImage[4][3];
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int width = 30;
    public int height = 30;
    public int entityX = 1;
    public int entityY = 1;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(entityX, entityY, width, height);
    public boolean collision = false;

    public boolean isFrightened = false;
    public boolean isWarning = false;
    public long frightenedStartTime = 0;
    public long frightenedDuration = 5000;
    public boolean isInvisible = false;
    public long invisibleStartTime = 0;
    public long invisibleDuration = 0;

    public static final String[] DIRECTIONS = {"up", "down", "left", "right"};

    public Entity(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void setAction() {

    }

    // イジケモードの経過時間をチェックして、時間切れ・警告状態を更新
    public void updateFrightenedMode() {
        if (isFrightened) {
            long elapsed = System.currentTimeMillis() - frightenedStartTime;
            if (elapsed >= frightenedDuration) {
                isFrightened = false;
                isWarning = false;
            } else if (frightenedDuration - elapsed <= 1000) { // 残り1秒なら警告状態
                isWarning = true;
            } else {
                isWarning = false;
            }
        }
    }

    public void update() {

        updateFrightenedMode();

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

    // 外部から呼び出してフライト状態に切り替える
    public void setFrightened(boolean frightened) {
        isFrightened = frightened;
        if (frightened) {

            frightenedStartTime = System.currentTimeMillis();
        }
    }

    // 残り1秒の警告状態の設定（GameWindow などから呼び出す）
    public void setWarningMode(boolean warning) {
        isWarning = warning;
    }

    public void setInvisible(long duration) {
        isInvisible = true;
        invisibleStartTime = System.currentTimeMillis();
        invisibleDuration = duration;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // 通常状態の場合は例えば赤いゴースト画像を使用（元々の実装）
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_up1;
                } else {
                    image = red_ghost_Image_up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_down1;
                } else {
                    image = red_ghost_Image_down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_left1;
                } else {
                    image = red_ghost_Image_left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = red_ghost_Image_right1;
                } else {
                    image = red_ghost_Image_right2;
                }
            }
        }

        if (image != null) {
            g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);

            // デバッグ
            g2.setColor(Color.GREEN);
            g2.drawRect(x + solidArea.x, y + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}