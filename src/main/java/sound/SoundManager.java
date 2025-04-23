package sound;

import window.GameWindow;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private GameWindow gameWindow;
    private Clip backgroundClip;
    private Clip startClip;
    private Clip clip;

    public SoundManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void playStartWAV(String filePath, Runnable callback) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            startClip = AudioSystem.getClip();
            startClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    startClip.close();
                    if (callback != null) {
                        callback.run();
                    }
                }
            });
            startClip.open(audioStream);
            startClip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playEatGhostWAV(String filePath) {
        playWAV(filePath);
    }

    public void playBackgroundWAV(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playFoodWAV(String filePath) {
        playWAV(filePath);
    }

    public void playGameOverWAV(String filePath) {
        playWAV(filePath);
    }

    private void playWAV(String filePath) {

        try {

            if (clip != null && clip.isRunning()) {
                clip.stop();
            }

            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}