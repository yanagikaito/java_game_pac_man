package tile;

import frame.FrameApp;
import window.GameWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private GameWindow gameWindow;
    private Tile[] tile;
    private int mapTileNum[][];

    public TileManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        tile = new Tile[3];
        mapTileNum = new int[FrameApp.getScreenWidth()][FrameApp.getScreenHeight()];
        getTileImage();
        loadMap("map/map.text.txt");
    }

    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(""));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("food/food.gif"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("wall/wall.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try {

            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < FrameApp.getMaxScreenCol() && row < FrameApp.getMaxScreenRow()) {
                String line = br.readLine();

                while (col < FrameApp.getMaxScreenCol()) {

                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == FrameApp.getMaxScreenCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;
        int x = 0;
        int y = 0;

        while (worldCol < FrameApp.getMaxScreenCol() && worldRow < FrameApp.getMaxScreenRow()) {
            int tileNum = mapTileNum[worldCol][worldRow];

            g2.drawImage(tile[tileNum].image, x, y, FrameApp.createSize(), FrameApp.createSize(), null);
            worldCol++;
            x += FrameApp.createSize();

            if (worldCol == FrameApp.getMaxScreenCol()) {
                worldCol = 0;
                x = 0;
                worldRow++;
                y += FrameApp.createSize();
            }
        }
    }
}