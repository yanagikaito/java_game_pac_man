package entity;

import frame.FrameApp;
import key.KeyHandler;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    private static final String[] DIRECTIONS = {"up", "down", "left", "right"};
    private static final int SPRITE_COUNT = 3;
    private BufferedImage[][] sprites = new BufferedImage[DIRECTIONS.length][SPRITE_COUNT];

    GameWindow gameWindow;
    KeyHandler keyHandler;

    public Player(GameWindow gameWindow, KeyHandler keyHandler) {
        this.gameWindow = gameWindow;
        this.keyHandler = keyHandler;

        solidArea = new Rectangle();
        solidArea.x = FrameApp.createSize() / 6;
        solidArea.y = FrameApp.createSize() / 3;
        solidArea.width = FrameApp.createSize() - 16;
        solidArea.height = FrameApp.createSize() - 16;

        setDefaultValues();
        loadPlayerImages();
    }

    public void setDefaultValues() {
        x = FrameApp.createSize() * 9;
        y = FrameApp.createSize() * 15;
        speed = 2;
        direction = "left";
    }

    public void loadPlayerImages() {
        try {
            for (int dir = 0; dir < DIRECTIONS.length; dir++) {
                for (int i = 0; i < SPRITE_COUNT; i++) {
                    sprites[dir][i] = ImageIO.read(getClass().getClassLoader()
                            .getResourceAsStream("player/pacman-" + DIRECTIONS[dir] + "-" + (i + 1) + ".gif"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.pacmanUp || keyHandler.pacmanDown || keyHandler.pacmanLeft || keyHandler.pacmanRight) {

            if (keyHandler.pacmanUp) {
                direction = "up";
            } else if (keyHandler.pacmanDown) {
                direction = "down";
            } else if (keyHandler.pacmanRight) {
                direction = "right";
            } else if (keyHandler.pacmanLeft) {
                direction = "left";
            }

            collision = false;
            gameWindow.getCollisionChecker().checkTile(this);

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
                spriteNum = (spriteNum % SPRITE_COUNT) + 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int dirIndex = java.util.Arrays.asList(DIRECTIONS).indexOf(direction);
        if (dirIndex != -1) {
            image = sprites[dirIndex][spriteNum - 1];
        }
        g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
    }
}