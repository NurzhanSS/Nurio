package Objects;

import java.awt.Graphics2D;

import MapFragments.MapFragments;

public abstract class projectionOfEnemy extends MapObject {

    protected boolean hit;
    protected boolean remove;
    protected int damage;

    public projectionOfEnemy(MapFragments tm) {
        super(tm);
    }

    public int damagedBy() {
        return damage;
    }

    public boolean timeToRemove() {
        return remove;
    }

    public abstract void setHit();

    public abstract void update();

    public void draw(Graphics2D g) {
        super.draw(g);
    }

}
