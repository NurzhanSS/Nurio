package Objects;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currPart;
    private int framesAmount;

    private int count;
    private int delay;

    private int countPlayedTimes;

    public Animation() {
        countPlayedTimes = 0;
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currPart = 0;
        count = 0;
        countPlayedTimes = 0;
        delay = 2;
        framesAmount = frames.length;
    }

    public void setDelay(int i) {
        delay = i;
    }

    public void setFrame(int i) {
        currPart = i;
    }

    public void setFramesAmount(int i) {
        framesAmount = i;
    }

    public void update() {

        if (delay == -1) {
            return;
        }

        count++;

        if (count == delay) {
            currPart++;
            count = 0;
        }
        if (currPart == framesAmount) {
            currPart = 0;
            countPlayedTimes++;
        }

    }

    public int getFrame() {
        return currPart;
    }

    public int getCount() {
        return count;
    }

    public BufferedImage getImage() {
        return frames[currPart];
    }

    public boolean justPlayOnce() {
        return countPlayedTimes > 0;
    }

    public boolean countPlay(int i) {
        return countPlayedTimes == i;
    }

}
