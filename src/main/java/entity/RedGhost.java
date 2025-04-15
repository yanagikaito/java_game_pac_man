package entity;

import window.GameWindow;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class RedGhost extends Entity {

    private GameWindow gameWindow;
    private Random random = new Random();
    private int actionLockCounter = 0;

    public RedGhost(GameWindow gameWindow) {
        super(gameWindow);
        direction = "right";
        speed = 2;
        loadGhostImages();
    }

    public void loadGhostImages() {

        try {

            red_ghost_Image_up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-up-1.gif"));
            red_ghost_Image_up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-up-2.gif"));
            red_ghost_Image_down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-down-1.gif"));
            red_ghost_Image_down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-down-2.gif"));
            red_ghost_Image_left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-left-1.gif"));
            red_ghost_Image_left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-left-2.gif"));
            red_ghost_Image_right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-right-1.gif"));
            red_ghost_Image_right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ghost/red/red-ghost-right-2.gif"));

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
}