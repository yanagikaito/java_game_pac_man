package entity;

import frame.FrameApp;
import key.KeyHandler;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GameWindow gameWindow;
    KeyHandler keyHandler;

    public Player(GameWindow gameWindow, KeyHandler keyHandler) {
        this.gameWindow = gameWindow;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 2;
        direction = "left";
    }

    public void getPlayerImage() {
        try {

            pacmanUp1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-up-1.gif"));
            pacmanUp2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-up-2.gif"));
            pacmanUp3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-up-3.gif"));
            pacmanDown1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-down-1.gif"));
            pacmanDown2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-down-2.gif"));
            pacmanDown3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-down-3.gif"));
            pacmanLeft1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-left-1.gif"));
            pacmanLeft2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-left-2.gif"));
            pacmanLeft3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-left-3.gif"));
            pacmanRight1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-right-1.gif"));
            pacmanRight2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-right-2.gif"));
            pacmanRight3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/pacman-right-3.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyHandler.pacmanUp == true) {
            direction = "up";
            y -= speed;
        } else if (keyHandler.pacmanDown == true) {
            direction = "down";
            y += speed;
        } else if (keyHandler.pacmanRight == true) {
            direction = "right";
            x += speed;
        } else if (keyHandler.pacmanLeft == true) {
            direction = "left";
            x -= speed;
        }
        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
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
                    image = pacmanUp1;
                }
                if (spriteNum == 2) {
                    image = pacmanUp2;
                }
                if (spriteNum == 3) {
                    image = pacmanUp3;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = pacmanDown1;
                }
                if (spriteNum == 2) {
                    image = pacmanDown2;
                }
                if (spriteNum == 3) {
                    image = pacmanDown3;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = pacmanRight1;
                }
                if (spriteNum == 2) {
                    image = pacmanRight2;
                }
                if (spriteNum == 3) {
                    image = pacmanRight3;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = pacmanLeft1;
                }
                if (spriteNum == 2) {
                    image = pacmanLeft2;
                }
                if (spriteNum == 3) {
                    image = pacmanLeft3;
                }
            }
        }
        g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
    }
}