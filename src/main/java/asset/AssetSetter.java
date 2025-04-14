package asset;

import entity.Ghost;
import frame.FrameApp;
import window.GameWindow;

public class AssetSetter {

    private GameWindow gameWindow;

    public AssetSetter(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void setGhost() {

        gameWindow.getGhost()[0] = new Ghost(gameWindow, 0);
        gameWindow.getGhost()[0].x = FrameApp.createSize() * 9;
        gameWindow.getGhost()[0].y = FrameApp.createSize() * 8;

//        gameWindow.getGhost()[1] = new Ghost(gameWindow, 1);
//        gameWindow.getGhost()[1].x = FrameApp.createSize() * 8;
//        gameWindow.getGhost()[1].y = FrameApp.createSize() * 9;
//
//        gameWindow.getGhost()[2] = new Ghost(gameWindow, 2);
//        gameWindow.getGhost()[2].x = FrameApp.createSize() * 9;
//        gameWindow.getGhost()[2].y = FrameApp.createSize() * 9;
//
//        gameWindow.getGhost()[3] = new Ghost(gameWindow, 3);
//        gameWindow.getGhost()[3].x = FrameApp.createSize() * 10;
//        gameWindow.getGhost()[3].y = FrameApp.createSize() * 9;
    }
}