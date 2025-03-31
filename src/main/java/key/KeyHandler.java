package key;

import window.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GameWindow gameWindow;
    public boolean pacmanUp;
    public boolean pacmanDown;
    public boolean pacmanLeft;
    public boolean pacmanRight;

    public KeyHandler(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            pacmanUp = true;
        }
        if (code == KeyEvent.VK_S) {
            pacmanDown = true;
        }
        if (code == KeyEvent.VK_A) {
            pacmanLeft = true;
        }
        if (code == KeyEvent.VK_D) {
            pacmanRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            pacmanUp = false;
        }
        if (code == KeyEvent.VK_S) {
            pacmanDown = false;
        }
        if (code == KeyEvent.VK_A) {
            pacmanLeft = false;
        }
        if (code == KeyEvent.VK_D) {
            pacmanRight = false;
        }
    }
}