package Objects.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Objects.Enemy;
import Objects.Player;
import Handlers.SpriteCollection;
import GameWindow.GPanel;
import MapFragments.MapFragments;

public class enemyNumberOne extends Enemy {

    private BufferedImage[] sprites;
    private Player player;
    private boolean active;

    public enemyNumberOne(MapFragments tm, Player p) {

        super(tm);
        player = p;

        health = healthLimit = 1;

        width = 25;
        height = 25;
        widthC = 20;
        heightC = 18;

        damage = 1;
        speedOfMove = 0.8;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -5;

        sprites = SpriteCollection.enemyNumberOne[0];

        animation.setFrames(sprites);
        animation.setDelay(4);

        left = true;
        lookingRight = false;

    }

    private void getNextPosition() {
        if (left) {
            dx = -speedOfMove;
        } else if (right) {
            dx = speedOfMove;
        } else {
            dx = 0;
        }
        if (falling) {
            dy += fallSpeed;
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }
        if (onJump && !falling) {
            dy = jumpStart;
        }
    }

    public void update() {

        if (!active) {
            if (Math.abs(player.getx() - x) < GPanel.WIDTH) {
                active = true;
            }
            return;
        }
        if (makeInvisible) {
            invisibilityCount++;
            if (invisibilityCount == 6) {
                makeInvisible = false;
            }
        }

        getNextPosition();
        checkMapFragmentsCollision();
        cornersCalc(x, ydest + 1);
        if (!bottomLeft) {
            left = false;
            right = lookingRight = true;
        }
        if (!bottomRight) {
            left = true;
            right = lookingRight = false;
        }
        positionSetting(tempX, tempY);

        if (dx == 0) {
            left = !left;
            right = !right;
            lookingRight = !lookingRight;
        }
        animation.update();

    }

    public void draw(Graphics2D g) {

        if (makeInvisible) {
            if (invisibilityCount == 0 || invisibilityCount == 2) {
                return;
            }
        }

        super.draw(g);

    }

}
