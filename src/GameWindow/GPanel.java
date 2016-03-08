package GameWindow;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameConditions.GameCondChange;
import Handlers.Keys;

@SuppressWarnings("serial")
public class GPanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    private BufferedImage image;
    private Graphics2D g;
    private GameCondChange GCC;
    private boolean recording = false;
    private int recordingCount = 0;
    private boolean screenshot;

    public GPanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;

        GCC = new GameCondChange();

    }

    public void run() {
        init();

        long start;
        long elapsed;
        long wait;
        while (running) {

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void update() {
        GCC.update();
        Keys.update();
    }

    private void draw() {
        GCC.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
        if (screenshot) {
            screenshot = false;
            try {
                java.io.File out = new java.io.File("screenshot " + System.nanoTime() + ".gif");
                javax.imageio.ImageIO.write(image, "gif", out);
            } catch (Exception e) {
            }
        }
        if (!recording) {
            return;
        }
        try {
            java.io.File out = new java.io.File("C:\\Filess\\frame" + recordingCount + ".gif");
            javax.imageio.ImageIO.write(image, "gif", out);
            recordingCount++;
        } catch (Exception e) {
        }
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        if (key.isControlDown()) {
            if (key.getKeyCode() == KeyEvent.VK_R) {
                recording = !recording;
                return;
            }
            if (key.getKeyCode() == KeyEvent.VK_S) {
                screenshot = true;
                return;
            }
        }
        Keys.keyCollection(key.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent key) {
        Keys.keyCollection(key.getKeyCode(), false);
    }

}
