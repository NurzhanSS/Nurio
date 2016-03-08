package GameConditions;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Sounds.SoundsCollection;
import Objects.Enemy;
import Objects.projectionOfEnemy;
import Objects.Explosion;
import Objects.HUD;
import Objects.Player;
import Objects.PlayerSave;
import Objects.Teleport;
import Objects.Enemies.enemyNumberOne;
import Handlers.Keys;
import GameWindow.GPanel;
import MapFragments.Background;
import MapFragments.MapFragments;

public class Level1x1 extends GameConditions {

    private Background sky;
    private Background clouds;
    private Background mountains;

    private Player player;
    private MapFragments MapFragments;
    private ArrayList<Enemy> enemies;
    private ArrayList<projectionOfEnemy> eprojectiles;
    private ArrayList<Explosion> explosions;

    private HUD HUD;
    private BufferedImage hageonText;
    private Teleport teleport;

    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private ArrayList<Rectangle> tb;
    private boolean eventFinish;
    private boolean eventDead;

    public Level1x1(GameCondChange GCC) {
        super(GCC);
        init();
    }

    public void init() {

        sky = new Background("/bg/sky.gif", 0);
        clouds = new Background("/bg/clouds.gif", 0.1);
        mountains = new Background("/bg/mountains.gif", 0.2);

        MapFragments = new MapFragments(30);
        MapFragments.fragmentsLoad("/MapFragments/vulkan.gif");
        MapFragments.mapLoading("/Maps/levelup.map");
        MapFragments.positionSetting(140, 0);
        MapFragments.setBounds(
                MapFragments.getWidth() - 1 * MapFragments.getFragmentSize(),
                MapFragments.getHeight() - 2 * MapFragments.getFragmentSize(),
                0, 0
        );
        MapFragments.setTween(1);

        player = new Player(MapFragments);
        player.positionSetting(300, 161);
        player.healthPropertySetting(PlayerSave.getHealth());
        player.livesAmountSetting(PlayerSave.getAmountOfLoves());
        player.setTime(PlayerSave.getTime());

        enemies = new ArrayList<Enemy>();
        eprojectiles = new ArrayList<projectionOfEnemy>();
        populateEnemies();

        player.init(enemies);

        explosions = new ArrayList<Explosion>();

        HUD = new HUD(player);

        teleport = new Teleport(MapFragments);
        teleport.positionSetting(3700, 131);

        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

        SoundsCollection.load("/SoundEffects/teleport.mp3", "teleport");
        SoundsCollection.load("/SoundEffects/explode.mp3", "explode");
        SoundsCollection.load("/SoundEffects/enemyhit.mp3", "enemyhit");

        SoundsCollection.load("/AllSounds/level1.mp3", "level1");
        SoundsCollection.loop("level1", 600, SoundsCollection.getFrames("level1") - 2200);

    }

    private void populateEnemies() {
        enemies.clear();

        enemyNumberOne gp;

        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(1300, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(1320, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(1340, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(1660, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(1680, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(1700, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(2177, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(2960, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(2980, 100);
        enemies.add(gp);
        gp = new enemyNumberOne(MapFragments, player);
        gp.positionSetting(3000, 100);
        enemies.add(gp);

    }

    public void update() {

        inputHandling();
        if (teleport.contains(player)) {
            eventFinish = blockInput = true;
        }
        if (player.getHealth() == 0 || player.gety() > MapFragments.getHeight()) {
            eventDead = blockInput = true;
        }
        if (eventStart) {
            eventStart();
        }
        if (eventDead) {
            eventDead();
        }
        if (eventFinish) {
            eventFinish();
        }

        
        clouds.positionSetting(MapFragments.getx(), MapFragments.gety());
        mountains.positionSetting(MapFragments.getx(), MapFragments.gety());
        player.update();
        MapFragments.positionSetting(
                GPanel.WIDTH / 2 - player.getx(),
                GPanel.HEIGHT / 2 - player.gety()
        );
        MapFragments.update();
        MapFragments.fixBounds();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.alreadyDead()) {
                enemies.remove(i);
                i--;
                explosions.add(new Explosion(MapFragments, e.getx(), e.gety()));
            }
        }
        for (int i = 0; i < eprojectiles.size(); i++) {
            projectionOfEnemy ep = eprojectiles.get(i);
            ep.update();
            if (ep.timeToRemove()) {
                eprojectiles.remove(i);
                i--;
            }
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).timeToRemove()) {
                explosions.remove(i);
                i--;
            }
        }
        teleport.update();

    }

    public void draw(Graphics2D g) {
        sky.draw(g);
        clouds.draw(g);
        mountains.draw(g);
        MapFragments.draw(g);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        for (int i = 0; i < eprojectiles.size(); i++) {
            eprojectiles.get(i).draw(g);
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }

        player.draw(g);
        teleport.draw(g);
        HUD.draw(g);
      
        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < tb.size(); i++) {
            g.fill(tb.get(i));
        }

    }

    public void inputHandling() {
        if (Keys.pressed(Keys.ESCAPE)) {
            GCC.makePause(true);
        }
        if (blockInput || player.getHealth() == 0) {
            return;
        }
        player.setUp(Keys.keyCond[Keys.UP]);
        player.setLeft(Keys.keyCond[Keys.LEFT]);
        player.setDown(Keys.keyCond[Keys.DOWN]);
        player.setRight(Keys.keyCond[Keys.RIGHT]);
        player.jumpSetting(Keys.keyCond[Keys.but1]);
        player.dashSetting(Keys.keyCond[Keys.but2]);
        if (Keys.pressed(Keys.but3)) {
            player.attackSetting();
        }
        if (Keys.pressed(Keys.but4)) {
            player.chargeSetting();
        }
    }

    private void reset() {
        player.reset();
        player.positionSetting(300, 161);
        populateEnemies();
        blockInput = true;
        eventCount = 0;
        MapFragments.makeEarthquake(false, 0);
        eventStart = true;
        eventStart();
    }

    private void eventStart() {
        eventCount++;
        if (eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, GPanel.WIDTH, GPanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, GPanel.WIDTH / 2, GPanel.HEIGHT));
            tb.add(new Rectangle(0, GPanel.HEIGHT / 2, GPanel.WIDTH, GPanel.HEIGHT / 2));
            tb.add(new Rectangle(GPanel.WIDTH / 2, 0, GPanel.WIDTH / 2, GPanel.HEIGHT));
        }
        if (eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;
        }
       
        
        if (eventCount == 60) {
            eventStart = blockInput = false;
            eventCount = 0;
            tb.clear();
        }
    }

    private void eventDead() {
        eventCount++;
        if (eventCount == 1) {
            player.deadPlayer();
            player.stop();
        }
        if (eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(
                    GPanel.WIDTH / 2, GPanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if (eventCount >= 120) {
            if (player.getAmountOfLoves() == 0) {
                GCC.setView(GameCondChange.MenuC);
            } else {
                eventDead = blockInput = false;
                eventCount = 0;
                player.lifeAmountLose();
                reset();
            }
        }
    }

    private void eventFinish() {
        eventCount++;
        if (eventCount >= 1) {
            SoundsCollection.play("teleport");
            player.teleportSetting(true);
            player.stop();
            PlayerSave.healthPropertySetting(player.getHealth());
            PlayerSave.livesAmountSetting(player.getAmountOfLoves());
            PlayerSave.setTime(player.getTime());
            GCC.setView(GameCondChange.WIN);
        }
    }

}
