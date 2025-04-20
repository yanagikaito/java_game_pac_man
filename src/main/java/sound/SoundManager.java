package sound;

import window.GameWindow;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {

    private GameWindow gameWindow;
    private Clip clip;

    public SoundManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void playStartWAV(String filePath) {
        playWAV(filePath);
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