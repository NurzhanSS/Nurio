package Objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Handlers.SpriteCollection;
import MapFragments.MapFragments;

public class Explosion extends MapObject {

    private BufferedImage[] sprites;

    private boolean remove;

    private Point[] xyPoints;
    private int speed;
    private double speedDiag;

    public Explosion(MapFragments tm, int x, int y) {

        super(tm);

        this.x = x;
        this.y = y;

        width = 30;
        height = 30;

        speed = 2;
        speedDiag = 1.41;

        sprites = SpriteCollection.Explosion[0];

        animation.setFrames(sprites);
        animation.setDelay(6);

        xyPoints = new Point[8];
        for (int i = 0; i < xyPoints.length; i++) {
            xyPoints[i] = new Point(x, y);
        }

    }

    public void update() {
        animation.update();
        if (animation.justPlayOnce()) {
            remove = true;
        }
        xyPoints[0].x += speed;
        xyPoints[1].x += speedDiag;
        xyPoints[1].y += speedDiag;
        xyPoints[2].y += speed;
        xyPoints[3].x -= speedDiag;
        xyPoints[3].y += speedDiag;
        xyPoints[4].x -= speed;
        xyPoints[5].x -= speedDiag;
        xyPoints[5].y -= speedDiag;
        xyPoints[6].y -= speed;
        xyPoints[7].x += speedDiag;
        xyPoints[7].y -= speedDiag;

    }

    public boolean timeToRemove() {
        return remove;
    }

    public void draw(Graphics2D g) {
        positionOnMap();
        for (int i = 0; i < xyPoints.length; i++) {
            g.drawImage(
                    animation.getImage(),
                    (int) (xyPoints[i].x + mapX - width / 2),
                    (int) (xyPoints[i].y + mapY - height / 2),
                    null
            );
        }
    }

}
