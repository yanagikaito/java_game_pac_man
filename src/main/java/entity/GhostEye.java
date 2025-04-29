package entity;

import ai.PathFinder;
import frame.FrameApp;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GhostEye extends Entity {

    private GameWindow gameWindow;
    private Class<? extends Entity> originalGhostEyeType;
    private Class<? extends Entity>[] ghostTypes;
    private ArrayList<Point> pathPoints;
    private int currentPathIndex = 0;

    public GhostEye(GameWindow gameWindow, Class<?> originalGhostEyeType,
                    Class<? extends Entity>... ghostTypes) {
        super(gameWindow);
        this.gameWindow = gameWindow;
        this.originalGhostType = originalGhostType;
        this.ghostTypes = ghostTypes;
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

    // GhostEye.java の中に追加する例:

    @Override
    public void setAction() {

        if (pathPoints == null || pathPoints.isEmpty() || currentPathIndex >= pathPoints.size()) {

            int startCol = (x + solidArea.x) / FrameApp.createSize();
            int startRow = (y + solidArea.y) / FrameApp.createSize();

            int houseCol = GOAL_COL;
            int houseRow = GOAL_ROW;

            PathFinder pathFinder = gameWindow.getPathFinder();
            pathFinder.setNodes(startCol, startRow, houseCol, houseRow,
                    gameWindow.getTileManager().mapTileNum,
                    gameWindow.getTileManager().tile);
            pathFinder.findPath();

            // 経路が見つかった場合、ピクセル座標に変換された経路リストをセットする
            if (pathFinder.isPathFound()) {
                setPathPoints(pathFinder.getPathPoints());
                currentPathIndex = 0;
            } else {
                // 経路が見つからなかった場合は移動速度を 0 にするなどの処理
                speed = 0;
                return;
            }
        }

        // 以下は既存の経路に沿った移動処理
        Point target = pathPoints.get(currentPathIndex);
        moveTowards(target);

        if (isAtTarget(target)) {
            currentPathIndex++;
            if (currentPathIndex >= pathPoints.size()) {
                isReturning = false;
                gameWindow.revertGhostFromGhostEye(this);
            }
        }
    }


    private void moveTowards(Point target) {
        if (x < target.x) {
            x += speed;
        }
    }

    private boolean isAtTarget(Point target) {
        int threshold = 5;
        return Math.abs(x - target.x) < threshold && Math.abs(y - target.y) < threshold;
    }

    public void setPathPoints(ArrayList<Point> points) {
        this.pathPoints = points;
        currentPathIndex = 0;
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