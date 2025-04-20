package window;

import asset.AssetSetter;
import collision.CollisionChecker;
import entity.Entity;
import entity.Player;
import factory.FrameFactory;
import frame.FrameApp;
import frame.GameFrame;
import game.GameOver;
import key.KeyHandler;
import sound.SoundManager;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

import static frame.FrameApp.baseDisplay;

public class GameWindow extends JPanel implements Window, Runnable {

    private GameFrame gameFrame = FrameFactory.createFrame(baseDisplay(), this);
    private KeyHandler keyHandler = new KeyHandler(this);
    private Player player = new Player(this, keyHandler);
    private Entity[] ghost = new Entity[4];
    private TileManager tileManager = new TileManager(this);
    private AssetSetter assetSetter = new AssetSetter(this);
    private GameOver gameOver = new GameOver(this);
    private static GameWindow instance;
    private Thread gameThread;
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private SoundManager soundManager = new SoundManager(this);
    private int gameState;
    private final int playState = 1;
    private final int gameOverState = 2;
    private int score = 0;

    private GameWindow() {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.startThread();
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(keyHandler);
        this.initGhosts();
        this.soundManager.playStartWAV("res/sound/game-start-sound.wav");
        this.gameState = playState;
    }

    private void initGhosts() {
        assetSetter.setGhost();
    }

    public static synchronized GameWindow getInstance() {
        if (instance == null) {
            instance = new GameWindow();
        }
        return instance;
    }

    @Override
    public void run() {
        int fps = 60;
        int nanosecond = 1000000000;
        double drawInterval = (double) nanosecond / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= nanosecond) {
                System.out.println("FPS :" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    @Override
    public void frame() {
        gameFrame.createFrame();
    }

    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            int playerCol = player.x / FrameApp.createSize();
            int playerRow = player.y / FrameApp.createSize();
            if (tileManager.mapTileNum[playerCol][playerRow] == 1) {
                tileManager.mapTileNum[playerCol][playerRow] = 0;
                score += 10;
                soundManager.playFoodWAV("res/sound/food-sound.wav");
            }
            for (int i = 0; i < ghost.length; i++) {
                if (ghost[i] != null) {
                    ghost[i].update();
                }
            }
        }
        if (gameState == gameOverState) {
            gameOver.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == playState) {
            tileManager.draw(g2);
            player.draw(g2);
            for (Entity ghostEntity : ghost) {
                if (ghostEntity != null) {
                    ghostEntity.draw(g2);
                }
            }
        }
        if (gameState == gameOverState) {
            gameOver.draw(g2);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("GAME OVER ", 150, 200);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.drawString("SCORE: " + score, 10, 20);

        g2.dispose();
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity[] getGhost() {
        return ghost;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public int getGameState() {
        return gameOverState;
    }

    public int getGameOverState() {
        return gameOverState;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public void setGameState(int state) {
        this.gameState = state;
    }
}