package entity;

import frame.FrameApp;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GhostEye extends Entity {

    private GameWindow gameWindow;

    public GhostEye(GameWindow gameWindow) {
        super(gameWindow);
        direction = "right";
        speed = 2;
        loadGhostImages();
    }

    public void loadGhostImages() {

        try {

            ghost_eye_up = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/eye/ghost-eye-up.gif"));
            ghost_eye_down = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/eye/ghost-eye-down.gif"));
            ghost_eye_left = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/eye/ghost-eye-left.gif"));
            ghost_eye_right = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/eye/ghost-eye-right.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAction() {
        if (isReturning == true) {

            int currentCol = (x + solidArea.x) / FrameApp.createSize();
            int currentRow = (y + solidArea.y) / FrameApp.createSize();

            if (currentCol == baseCol && currentRow == baseRow) {
                isReturning = false;
                revertToNormalState();
            }
        }
    }

    private BufferedImage getGhostEyeImage() {

        switch (direction) {
            case "up":
                return ghost_eye_up;
            case "down":
                return ghost_eye_down;
            case "left":
                return ghost_eye_left;
            case "right":
                return ghost_eye_right;
            default:
                return ghost_eye_right;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        if (getIsReturning()) {
            BufferedImage eyeImage = getGhostEyeImage();
            if (eyeImage != null) {
                g2.drawImage(eyeImage, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
            }
        }
    }
}