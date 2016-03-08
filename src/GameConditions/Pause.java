package GameConditions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Handlers.Keys;
import GameWindow.GPanel;

public class Pause extends GameConditions {

    private Font font;

    public Pause(GameCondChange GCC) {

        super(GCC);
        font = new Font("Century Gothic", Font.PLAIN, 14);

    }

    public void init() {
    }

    public void update() {
        inputHandling();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GPanel.WIDTH, GPanel.HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Game Paused", 90, 90);
    }

    public void inputHandling() {
        if (Keys.pressed(Keys.ESCAPE)) {
            GCC.makePause(false);
        }
        if (Keys.pressed(Keys.but1)) {
            GCC.makePause(false);
            GCC.setView(GameCondChange.MenuC);
        }
    }

}
