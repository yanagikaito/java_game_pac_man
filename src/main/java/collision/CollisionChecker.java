package collision;

import entity.Entity;
import frame.FrameApp;
import window.GameWindow;

public class CollisionChecker {

    private GameWindow gameWindow;

    public CollisionChecker(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void checkTile(Entity entity) {

        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX / FrameApp.createSize();
        int entityRightCol = entityRightX / FrameApp.createSize();
        int entityTopRow = entityTopY / FrameApp.createSize();
        int entityBottomRow = entityBottomY / FrameApp.createSize();

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopY - entity.speed) / FrameApp.createSize();
                tileNum1 = gameWindow.getTileManager().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gameWindow.getTileManager().mapTileNum[entityRightCol][entityTopRow];
                if (gameWindow.getTileManager().tile[tileNum1].collision || gameWindow.getTileManager().tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomY + entity.speed) / FrameApp.createSize();
                tileNum1 = gameWindow.getTileManager().mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gameWindow.getTileManager().mapTileNum[entityRightCol][entityBottomRow];
                if (gameWindow.getTileManager().tile[tileNum1].collision || gameWindow.getTileManager().tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftX - entity.speed) / FrameApp.createSize();
                tileNum1 = gameWindow.getTileManager().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gameWindow.getTileManager().mapTileNum[entityLeftCol][entityBottomRow];
                if (gameWindow.getTileManager().tile[tileNum1].collision || gameWindow.getTileManager().tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightX + entity.speed) / FrameApp.createSize();
                tileNum1 = gameWindow.getTileManager().mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gameWindow.getTileManager().mapTileNum[entityRightCol][entityBottomRow];
                if (gameWindow.getTileManager().tile[tileNum1].collision || gameWindow.getTileManager().tile[tileNum2].collision) {
                    entity.collision = true;
                }
            }
        }
    }
}