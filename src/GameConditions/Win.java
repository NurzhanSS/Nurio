package GameConditions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Handlers.Keys;
import GameWindow.GPanel;

public class Win extends GameConditions {

    private float shade;
    private Color color;

    private double changeOfAngle;
    private BufferedImage image;

    public Win(GameCondChange GCC) {
        super(GCC);
        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/PlayerSprites.gif"
                    )).getSubimage(0, 0, 40, 40);
        } catch (Exception e) {
        }
    }

    public void init() {
    }

    public void update() {
        inputHandling();
        color = Color.getHSBColor(shade, 1f, 1f);
        shade += 0.01;
        if (shade > 1) {
            shade = 0;
        }
        changeOfAngle += 0.1;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, GPanel.WIDTH, GPanel.HEIGHT);
        AffineTransform at = new AffineTransform();
        at.translate(GPanel.WIDTH / 2, GPanel.HEIGHT / 2);
        at.rotate(changeOfAngle);
        g.drawImage(image, at, null);
    }

    public void inputHandling() {
        if (Keys.pressed(Keys.ESCAPE)) {
            GCC.setView(GameCondChange.MenuC);
        }
    }

}
