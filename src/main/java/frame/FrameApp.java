package frame;

public class FrameApp {
    public static FrameSize baseDisplay() {

        int maxScreenRow = 20;
        int maxScreenCol = 16;
        int tileSize = createSize();
        int screenWidth = tileSize * maxScreenRow;
        int screenHeight = tileSize * maxScreenCol;

        return new FrameSize(screenWidth, screenHeight);
    }

    public static int createSize() {
        int originalTileSize = 16;
        int scale = 3;
        return originalTileSize * scale;
    }
}