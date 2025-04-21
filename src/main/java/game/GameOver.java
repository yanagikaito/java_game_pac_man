package game;

import entity.Entity;
import frame.FrameApp;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameOver extends Entity {

    private GameWindow gameWindow;
    private int spriteCounter = 0;
    private int spriteDelay = 30;

    public GameOver(GameWindow gameWindow) {
        super(gameWindow);
        this.gameWindow = gameWindow;
        setDefaultValues();
        loadGameOverImages();
    }

    public void setDefaultValues() {
        x = FrameApp.createSize() * 9;
        y = FrameApp.createSize() * 10;
        direction = "up";
    }

    public void triggerGameOver() {
        gameWindow.getSoundManager().playGameOverWAV("res/sound/game-over-sound.wav");
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > spriteDelay) {
            spriteNum++;
            if (spriteNum < 5) {
                spriteNum++;
            }
            spriteCounter = 0;
        }
    }

    public void loadGameOverImages() {

        try {

            game_over_Image_1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gameover/pacman-game-over-1.gif"));
            game_over_Image_2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gameover/pacman-game-over-2.gif"));
            game_over_Image_3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gameover/pacman-game-over-3.gif"));
            game_over_Image_4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gameover/pacman-game-over-4.gif"));
            game_over_Image_5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gameover/pacman-game-over-5.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        String safeDirection = (direction == null) ? "up" : direction;
        BufferedImage image = null;

        switch (safeDirection) {
            case "up":
            case "down":
            case "left":
            case "right":
                if (spriteNum == 1) {
                    image = game_over_Image_1;
                } else if (spriteNum == 2) {
                    image = game_over_Image_2;
                } else if (spriteNum == 3) {
                    image = game_over_Image_3;
                } else if (spriteNum == 4) {
                    image = game_over_Image_4;
                } else if (spriteNum == 5) {
                    image = game_over_Image_5;
                }
                break;
        }

        if (image != null) {
            g2.drawImage(image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
        }
    }
}