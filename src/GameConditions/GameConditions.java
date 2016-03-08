package GameConditions;

import java.awt.Graphics2D;

public abstract class GameConditions {

    protected GameCondChange GCC;

    public GameConditions(GameCondChange GCC) {
        this.GCC = GCC;
    }

    public abstract void init();

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public abstract void inputHandling();

}
