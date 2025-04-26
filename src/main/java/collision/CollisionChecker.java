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

    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;

                target[i].solidArea.x = target[i].x + target[i].solidArea.x;
                target[i].solidArea.y = target[i].y + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> {
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collision = true;
                            index = i;
                        }
                    }
                    case "down" -> {
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collision = true;
                            index = i;
                        }
                    }
                    case "left" -> {
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collision = true;
                            index = i;
                        }
                    }
                    case "right" -> {
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collision = true;
                            index = i;
                        }
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity) {

        entity.solidArea.x = entity.x + entity.solidArea.x;
        entity.solidArea.y = entity.y + entity.solidArea.y;

        gameWindow.getPlayer().solidArea.x = gameWindow.getPlayer().x + gameWindow.getPlayer().solidArea.x;
        gameWindow.getPlayer().solidArea.y = gameWindow.getPlayer().y + gameWindow.getPlayer().solidArea.y;

        switch (entity.direction) {
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.x += entity.speed;
        }

        // 衝突判定
        if (entity.solidArea.intersects(gameWindow.getPlayer().solidArea)) {
            entity.collision = true;
            if (entity.isFrightened == true || entity.isWarning == true) {
                gameWindow.eatGhost(entity);
            } else {
                gameWindow.setGameState(gameWindow.getGameOverState());
                gameWindow.getGameOver().triggerGameOver();
                gameWindow.getGameOver().update();
            }
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gameWindow.getPlayer().solidArea.x = gameWindow.getPlayer().solidAreaDefaultX;
        gameWindow.getPlayer().solidArea.y = gameWindow.getPlayer().solidAreaDefaultY;
    }
}