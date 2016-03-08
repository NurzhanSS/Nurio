package Objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import MapFragments.MapFragments;

public class Teleport extends MapObject {

    private BufferedImage[] sprites;

    public Teleport(MapFragments tm) {
        super(tm);
        lookingRight = true;
        width = height = 40;
        widthC = 20;
        heightC = 40;
        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/Teleport.gif")
            );
            sprites = new BufferedImage[9];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width, 0, width, height
                );
            }
            animation.setFrames(sprites);
            animation.setDelay(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        animation.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
