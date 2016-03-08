package Objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import MapFragments.MapFragments;

public class FireEffect extends MapObject {

    private boolean remove;
    private BufferedImage[] sprites;
    private boolean hit;
    private BufferedImage[] sprites_HIT;

    public FireEffect(MapFragments tm, boolean right) {

        super(tm);

        lookingRight = right;

        speedOfMove = 3.8;
        if (right) {
            dx = speedOfMove;
        } else {
            dx = -speedOfMove;
        }

        width = 30;
        height = 30;
        widthC = 14;
        heightC = 14;
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/fireball.gif"
                    )
            );

            sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

            sprites_HIT = new BufferedImage[3];
            for (int i = 0; i < sprites_HIT.length; i++) {
                sprites_HIT[i] = spritesheet.getSubimage(
                        i * width,
                        height,
                        width,
                        height
                );
            }

            animation.setFrames(sprites);
            animation.setDelay(4);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setHit() {
        if (hit) {
            return;
        }
        hit = true;
        animation.setFrames(sprites_HIT);
        animation.setDelay(4);
        dx = 0;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean timeToRemove() {
        return remove;
    }

    public void update() {

        checkMapFragmentsCollision();
        positionSetting(tempX, tempY);

        if (dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if (hit && animation.justPlayOnce()) {
            remove = true;
        }

    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
