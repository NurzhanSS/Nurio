package Objects;

import Sounds.SoundsCollection;
import MapFragments.MapFragments;

public class Enemy extends MapObject {

    protected boolean remove;
    protected int health;
    protected int healthLimit;
    protected boolean dead;
    protected int damage;

    protected boolean makeInvisible;
    protected long invisibilityCount;

    public Enemy(MapFragments tm) {
        super(tm);
        remove = false;
    }

    public boolean alreadyDead() {
        return dead;
    }

    public boolean timeToRemove() {
        return remove;
    }

    public int damagedBy() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || makeInvisible) {
            return;
        }
        SoundsCollection.play("enemyhit");
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            dead = true;
        }
        if (dead) {
            remove = true;
        }
        makeInvisible = true;
        invisibilityCount = 0;
    }

    public void update() {
    }

}
