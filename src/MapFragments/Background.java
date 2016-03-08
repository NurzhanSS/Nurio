package MapFragments;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameWindow.GPanel;

public class Background {

    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private int width;
    private int height;

    private double multByX;
    private double multByY;

    public Background(String s) {
        this(s, 0.1);
    }

    public Background(String s, double d) {
        this(s, d, d);
    }

    public Background(String s, double d1, double d2) {
        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            width = image.getWidth();
            height = image.getHeight();
            multByX = d1;
            multByY = d2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Background(String s, double ms, int x, int y, int w, int h) {
        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            image = image.getSubimage(x, y, w, h);
            width = image.getWidth();
            height = image.getHeight();
            multByX = ms;
            multByY = ms;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void positionSetting(double x, double y) {
        this.x = (x * multByX) % width;
        this.y = (y * multByY) % height;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setScale(double multByX, double multByY) {
        this.multByX = multByX;
        this.multByY = multByY;
    }

    public void setDimensions(int i1, int i2) {
        width = i1;
        height = i2;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public void update() {
        x += dx;
        while (x <= -width) {
            x += width;
        }
        while (x >= width) {
            x -= width;
        }
        y += dy;
        while (y <= -height) {
            y += height;
        }
        while (y >= height) {
            y -= height;
        }
    }

    public void draw(Graphics2D g) {

        g.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            g.drawImage(image, (int) x + GPanel.WIDTH, (int) y, null);
        }
        if (x > 0) {
            g.drawImage(image, (int) x - GPanel.WIDTH, (int) y, null);
        }
        if (y < 0) {
            g.drawImage(image, (int) x, (int) y + GPanel.HEIGHT, null);
        }
        if (y > 0) {
            g.drawImage(image, (int) x, (int) y - GPanel.HEIGHT, null);
        }
    }

}
