package GameConditions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Sounds.SoundsCollection;
import Objects.PlayerSave;
import Handlers.Keys;
import GameWindow.GPanel;
import MapFragments.Background;

public class MenuLook extends GameConditions {

    private BufferedImage willPick;

    private int selectedNow = 0;
    private String[] pick = {
        "Start",
        "Quit"
    };

    private Color tColor;
    private Font tFont;

    private Font font;
    private Font font2;

    public MenuLook(GameCondChange GCC) {

        super(GCC);

        try {

            willPick = ImageIO.read(
                    getClass().getResourceAsStream("/Elements/LifeBar.gif")
            ).getSubimage(0, 12, 12, 11);
            tColor = Color.WHITE;
            tFont = new Font("Times New Roman", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 14);
            font2 = new Font("Arial", Font.PLAIN, 10);
            SoundsCollection.load("/SoundEffects/menuoption.mp3", "menuoption");
            SoundsCollection.load("/SoundEffects/menuselect.mp3", "menuselect");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
    }

    public void update() {
        inputHandling();

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GPanel.WIDTH, GPanel.HEIGHT);
        g.setColor(tColor);
        g.setFont(tFont);
        g.drawString("Nurio", 120, 90);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Start", 145, 165);
        g.drawString("Quit", 145, 185);
        if (selectedNow == 0) {
            g.drawImage(willPick, 125, 154, null);
        } else if (selectedNow == 1) {
            g.drawImage(willPick, 125, 174, null);
        }
        g.setFont(font2);
        g.drawString("2015 Nurzhan S.", 10, 232);

    }

    private void select() {
        if (selectedNow == 0) {
            SoundsCollection.play("menuselect");
            PlayerSave.init();
            GCC.setView(GameCondChange.Level1_1);
        } else if (selectedNow == 1) {
            System.exit(0);
        }
    }

    public void inputHandling() {
        if (Keys.pressed(Keys.ENTER)) {
            select();
        }
        if (Keys.pressed(Keys.UP)) {
            if (selectedNow > 0) {
                SoundsCollection.play("menuoption", 0);
                selectedNow--;
            }
        }
        if (Keys.pressed(Keys.DOWN)) {
            if (selectedNow < pick.length - 1) {
                SoundsCollection.play("menuoption", 0);
                selectedNow++;
            }
        }
    }

}
