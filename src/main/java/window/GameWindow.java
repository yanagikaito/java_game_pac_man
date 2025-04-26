package window;

import ai.PathFinder;
import asset.AssetSetter;
import collision.CollisionChecker;
import entity.*;
import factory.FrameFactory;
import frame.FrameApp;
import frame.GameFrame;
import game.GameOver;
import key.KeyHandler;
import sound.SoundManager;
import tile.TileManager;
import ui.ScorePopup;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import static frame.FrameApp.baseDisplay;

public class GameWindow extends JPanel implements Window, Runnable {

    private GameFrame gameFrame = FrameFactory.createFrame(baseDisplay(), this);
    private KeyHandler keyHandler = new KeyHandler(this);
    private Player player = new Player(this, keyHandler);
    private Entity[] ghost = new Entity[4];
    private Entity[] frightenedGhost = new Entity[4];
    private TileManager tileManager = new TileManager(this);
    private AssetSetter assetSetter = new AssetSetter(this);
    private GameOver gameOver = new GameOver(this);
    private static GameWindow instance;
    private Thread gameThread;
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private SoundManager soundManager = new SoundManager(this);
    private UI ui = new UI(this);
    private PathFinder pathFinder = new PathFinder(this);
    private List<ScorePopup> scorePopups = new ArrayList<>();
    private boolean isPlayBackgroundMusic = false;
    private boolean isStartBGMPlaying = false;
    private boolean isPowerModeActive = false;
    private boolean isPlayFrightenedMusic = false;
    private long powerModeStartTime = 0;
    private final long powerModeDuration = 5000;
    private int gameState;
    private final int titleState = 0;
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
        this.setupGame();
    }

    private void setupGame() {
        initGhosts();
        gameState = titleState;
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

    public void startGame() {
        isStartBGMPlaying = true;
        soundManager.playStartWAV("res/sound/game-start-sound.wav", this::onStartBGMEnd);
    }

    private void onStartBGMEnd() {
        isStartBGMPlaying = false;
        gameState = playState;
        System.out.println("パックマンの動きを開始！");
    }

    public void update() {
        if (isStartBGMPlaying) {
            return;
        }
        if (gameState == playState) {
            player.update();

            scorePopups.removeIf(ScorePopup::isExpired);

            if (!isPlayBackgroundMusic) {
                soundManager.playBackgroundWAV("res/sound/game-background-sound.wav");
                isPlayBackgroundMusic = true;
            }
            int playerCol = player.x / FrameApp.createSize();
            int playerRow = player.y / FrameApp.createSize();
            if (tileManager.mapTileNum[playerCol][playerRow] == 1) {
                tileManager.mapTileNum[playerCol][playerRow] = 0;
                score += 10;
                soundManager.playFoodWAV("res/sound/food-sound.wav");
            }
            if (tileManager.mapTileNum[playerCol][playerRow] == 3) {
                tileManager.mapTileNum[playerCol][playerRow] = 0;
                score += 50;
                soundManager.playFoodWAV("res/sound/food-sound.wav");

                activatePowerMode();
            }

            if (isPowerModeActive) {
                long elapsed = System.currentTimeMillis() - powerModeStartTime;
                if (elapsed >= powerModeDuration) {
                    deactivatePowerMode();
                } else if (powerModeDuration - elapsed < 1000) {
                    for (int i = 0; i < ghost.length; i++) {
                        if (ghost[i] != null) {
                            ghost[i].setWarningMode(true);
                        }
                    }
                } else {

                    for (int i = 0; i < ghost.length; i++) {
                        if (ghost[i] != null) {
                            ghost[i].setWarningMode(false);
                        }
                    }
                }
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

    private void activatePowerMode() {
        isPowerModeActive = true;
        powerModeStartTime = System.currentTimeMillis();

        if (!isPlayFrightenedMusic) {
            soundManager.playFrightenedWAV("res/sound/frightened-sound.wav");
            isPlayFrightenedMusic = true;
        }

        for (int i = 0; i < ghost.length; i++) {
            if (ghost[i] != null) {
                int ghostX = ghost[i].x;
                int ghostY = ghost[i].y;
                Class<?> originalType = ghost[i].getClass();

                frightenedGhost[i] = new BlueFrightened(this, originalType);
                frightenedGhost[i].setFrightened(true);
                frightenedGhost[i].x = ghostX;
                frightenedGhost[i].y = ghostY;

                ghost[i] = frightenedGhost[i];
            }
        }
    }

    private void deactivatePowerMode() {
        isPowerModeActive = false;
        for (int i = 0; i < ghost.length; i++) {
            if (ghost[i] != null) {
                ghost[i].setFrightened(false);
                ghost[i].setWarningMode(false);
            }
        }
    }

    public void revertGhostFromFrightened(Entity frightenedGhost) {
        for (int i = 0; i < ghost.length; i++) {
            if (ghost[i] == frightenedGhost) {
                Entity normalGhost = null;
                Class<?> originalType = ((BlueFrightened) frightenedGhost).getOriginalGhostType();

                if (originalType == RedGhost.class) {
                    normalGhost = new RedGhost(this);
                } else if (originalType == BlueGhost.class) {
                    normalGhost = new BlueGhost(this);
                } else if (originalType == OrangeGhost.class) {
                    normalGhost = new OrangeGhost(this);
                } else if (originalType == PinkGhost.class) {
                    normalGhost = new PinkGhost(this);
                }

                if (normalGhost != null) {
                    normalGhost.x = frightenedGhost.x;
                    normalGhost.y = frightenedGhost.y;
                    normalGhost.speed = frightenedGhost.speed;

                    ghost[i] = normalGhost;
                }
            }
        }
    }

    public void eatGhost(Entity ghost) {
        if (ghost != null) {
            score += 200;
            soundManager.playEatGhostWAV("res/sound/eat-ghost-sound.wav");

            addScorePopup(ghost.x, ghost.y, 200);
            ghost.setInvisible(1000);
            ghost.setFrightened(false);

            ghost.x = FrameApp.createSize() * 9;
            ghost.y = FrameApp.createSize() * 8;

            ghost.isReturning = true;

            for (int i = 0; i < this.ghost.length; i++) {
                if (this.ghost[i] == ghost) {
                    GhostEye ghostEye = new GhostEye(this);
                    ghostEye.x = ghost.x;
                    ghostEye.y = ghost.y;
                    ghostEye.speed = ghost.speed;
                    ghostEye.searchPath(9, 8);
                    ghostEye.isReturning = true;
                    this.ghost[i] = ghostEye;
                    break;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {
            ui.draw(g2);
        } else if (gameState == playState) {
            tileManager.draw(g2);
            player.draw(g2);
            for (Entity ghostEntity : ghost) {
                if (ghostEntity != null) {
                    ghostEntity.draw(g2);
                }
            }

            for (ScorePopup popup : scorePopups) {
                popup.draw(g2);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 15));
            g2.drawString("SCORE: " + score, 10, 20);
        }
        if (gameState == gameOverState) {
            gameOver.draw(g2);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("GAME OVER ", 150, 200);
        }

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

    public int getGameOverState() {
        return gameOverState;
    }

    public int getPlayState() {
        return playState;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public int getTitleState() {
        return titleState;
    }

    public void setGameState(int state) {
        this.gameState = state;
    }

    public UI getUi() {
        return ui;
    }

    public void addScorePopup(int x, int y, int scoreValue) {
        scorePopups.add(new ScorePopup(this, x, y, scoreValue));
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }
}