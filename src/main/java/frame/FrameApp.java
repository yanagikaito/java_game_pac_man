package frame;

public class FrameApp {

    private static final int maxScreenRow = 22;
    private static final int maxScreenCol = 19;
    private static final int tileSize = createSize();
    private static final int screenWidth = tileSize * maxScreenCol;
    private static final int screenHeight = tileSize * maxScreenRow;

    public static FrameSize baseDisplay() {

        return new FrameSize(screenWidth, screenHeight);
    }

    public static int createSize() {
        int originalTileSize = 16;
        int scale = 2;
        return originalTileSize * scale;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getMaxScreenCol() {
        return maxScreenCol;
    }

    public static int getMaxScreenRow() {
        return maxScreenRow;
    }
}