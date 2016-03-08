package GameConditions;

import Sounds.SoundsCollection;
import GameWindow.GPanel;

public class GameCondChange {

    private GameConditions[] gameConds;

    private int currentCond;

    private Pause pause;
    private boolean onPause;

    public static final int gameCondsAmount = 12;
    public static final int MenuC = 0;
    public static final int Level1_1 = 2;
    public static final int Level1_2 = 3;
    public static final int WIN = 11;

    public GameCondChange() {

        SoundsCollection.init();

        gameConds = new GameConditions[gameCondsAmount];

        pause = new Pause(this);
        onPause = false;

        currentCond = MenuC;
        condLoading(currentCond);

    }

    private void condLoading(int state) {
        if (state == MenuC) {
            gameConds[state] = new MenuLook(this);
        } else if (state == Level1_1) {
            gameConds[state] = new Level1x1(this);
        } else if (state == WIN) {
            gameConds[state] = new Win(this);
        }
    }

    private void uncondLoading(int state) {
        gameConds[state] = null;
    }

    public void setView(int state) {
        uncondLoading(currentCond);
        currentCond = state;
        condLoading(currentCond);
    }

    public void makePause(boolean b) {
        onPause = b;
    }

    public void update() {
        if (onPause) {
            pause.update();
            return;
        }
        if (gameConds[currentCond] != null) {
            gameConds[currentCond].update();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        if (onPause) {
            pause.draw(g);
            return;
        }
        if (gameConds[currentCond] != null) {
            gameConds[currentCond].draw(g);
        } else {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, GPanel.WIDTH, GPanel.HEIGHT);
        }
    }

}
