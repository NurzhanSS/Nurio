package Sounds;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundsCollection {

    private static HashMap<String, Clip> soundFX;
    private static int space;
    private static boolean noSound = false;

    public static void init() {
        soundFX = new HashMap<String, Clip>();
        space = 0;
    }

    public static void load(String s, String n) {
        if (soundFX.get(n) != null) {
            return;
        }
        Clip clip;
        try {
            AudioInputStream ais
                    = AudioSystem.getAudioInputStream(
                            SoundsCollection.class.getResourceAsStream(s)
                    );
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            soundFX.put(n, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void play(String s) {
        play(s, space);
    }

    public static void play(String s, int i) {
        if (noSound) {
            return;
        }
        Clip c = soundFX.get(s);
        if (c == null) {
            return;
        }
        if (c.isRunning()) {
            c.stop();
        }
        c.setFramePosition(i);
        while (!c.isRunning()) {
            c.start();
        }
    }

    public static void stop(String s) {
        if (soundFX.get(s) == null) {
            return;
        }
        if (soundFX.get(s).isRunning()) {
            soundFX.get(s).stop();
        }
    }

    public static void resume(String s) {
        if (noSound) {
            return;
        }
        if (soundFX.get(s).isRunning()) {
            return;
        }
        soundFX.get(s).start();
    }

    public static void loop(String s) {
        loop(s, space, space, soundFX.get(s).getFrameLength() - 1);
    }

    public static void loop(String s, int frame) {
        loop(s, frame, space, soundFX.get(s).getFrameLength() - 1);
    }

    public static void loop(String s, int start, int end) {
        loop(s, space, start, end);
    }

    public static void loop(String s, int frame, int start, int end) {
        stop(s);
        if (noSound) {
            return;
        }
        soundFX.get(s).setLoopPoints(start, end);
        soundFX.get(s).setFramePosition(frame);
        soundFX.get(s).loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void positionSetting(String s, int frame) {
        soundFX.get(s).setFramePosition(frame);
    }

    public static int getFrames(String s) {
        return soundFX.get(s).getFrameLength();
    }

    public static int getPosition(String s) {
        return soundFX.get(s).getFramePosition();
    }

    public static void close(String s) {
        stop(s);
        soundFX.get(s).close();
    }

}
