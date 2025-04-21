package ui;

import frame.FrameApp;
import window.GameWindow;

import java.awt.*;

public class UI {

    private GameWindow gameWindow;
    private Graphics2D g2;
    private Font arial_40;
    private Font arial_80B;
    private int commandNum;

    public UI(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        arial_40 = new Font("エリア", Font.PLAIN, 40);
        arial_80B = new Font("エリア", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.YELLOW);

        gameWindow.setGameState(gameWindow.getTitleState());
        drawTitleScreen();
    }

    public void drawTitleScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        String text = "Pac Man";
        int x = getXforCenteredText(text);
        int y = FrameApp.createSize() * 3;

        // 影
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.YELLOW);
        g2.drawString(text, x, y);

        text = "GAME START";
        x = getXforCenteredText(text);
        y += FrameApp.createSize() * 12;
        g2.drawString(text, x - 20, y);
        if (commandNum == 0) {
            g2.drawString(">", x - FrameApp.createSize() * 2, y);
        }
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = FrameApp.getScreenWidth() / 3 - length / 6;
        return x;
    }

    public int getCommandNum() {
        return commandNum;
    }
}