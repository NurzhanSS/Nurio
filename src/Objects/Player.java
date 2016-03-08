package Objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Sounds.SoundsCollection;
import MapFragments.MapFragments;

public class Player extends MapObject {

    private ArrayList<Enemy> enemies;
    private int damage;
    private int chargeDamage;
    private int lives;
    private boolean moveBackward;
    private boolean makeInvisible;
    private long invisibilityCount;
    private int score;
    private int health;
    private int healthLimit;
    private boolean jump2;
    private boolean jump2Done;
    private double jump2Start;

    private long time;
    private boolean onDash;
    private boolean attacking;
    private boolean attackUp;
    private boolean superCharge;
    private int superChargeTick;
    private boolean teleportPlayer;

    private ArrayList<BufferedImage[]> sprites;
    private final int[] spriteDivisions = {
        1, 8, 5, 3, 3, 5, 3, 8, 2, 1, 3
    };
    private final int[] divisionWidth = {
        40, 40, 80, 40, 40, 40, 80, 40, 40, 40, 40
    };
    private final int[] divisionHeight = {
        40, 40, 40, 40, 40, 80, 40, 40, 40, 40, 40
    };
    private final int[] makeDelay = {
        -1, 3, 2, 6, 5, 2, 2, 2, 1, -1, 1
    };
    private Rectangle aur;
    private Rectangle ar;
    private Rectangle cr;
    private static final int IDLE = 0;
    private static final int pWalk = 1;
    private static final int ATTACKING = 2;
    private static final int JUMPING = 3;
    private static final int FALLING = 4;
    private static final int UPATTACKING = 5;
    private static final int CHARGING = 6;
    private static final int DASHING = 7;
    private static final int moveBACKWARD = 8;
    private static final int DEAD = 9;
    private static final int TELEPORTING = 10;

    public Player(MapFragments tm) {

        super(tm);

        ar = new Rectangle(0, 0, 0, 0);
        ar.width = 30;
        ar.height = 20;
        aur = new Rectangle((int) x - 15, (int) y - 45, 30, 30);
        cr = new Rectangle(0, 0, 0, 0);
        cr.width = 50;
        cr.height = 40;

        width = 30;
        height = 30;
        widthC = 15;
        heightC = 38;

        speedOfMove = 1.6;
        maxSpeed = 1.6;
        stopSpeed = 1.6;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;
        jump2Start = -3;

        damage = 2;
        chargeDamage = 1;

        lookingRight = true;

        lives = 3;
        health = healthLimit = 5;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/PlayerSprites.gif"
                    )
            );

            int count = 0;
            sprites = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < spriteDivisions.length; i++) {
                BufferedImage[] bi = new BufferedImage[spriteDivisions[i]];
                for (int j = 0; j < spriteDivisions[i]; j++) {
                    bi[j] = spritesheet.getSubimage(
                            j * divisionWidth[i],
                            count,
                            divisionWidth[i],
                            divisionHeight[i]
                    );
                }
                sprites.add(bi);
                count += divisionHeight[i];
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animate(IDLE);

        SoundsCollection.load("/SoundEffects/playerjump.mp3", "playerjump");
        SoundsCollection.load("/SoundEffects/playerlands.mp3", "playerlands");
        SoundsCollection.load("/SoundEffects/playerattack.mp3", "playerattack");
        SoundsCollection.load("/SoundEffects/playerhit.mp3", "playerhit");
        SoundsCollection.load("/SoundEffects/playercharge.mp3", "playercharge");

    }

    public void init(
            ArrayList<Enemy> enemies
    ) {
        this.enemies = enemies;

    }

    public int getHealth() {
        return health;
    }

    public int getMaxOfHealth() {
        return healthLimit;
    }

    public void teleportSetting(boolean b) {
        teleportPlayer = b;
    }

    public void jumpSetting(boolean b) {
        if (moveBackward) {
            return;
        }
        if (b && !onJump && falling && !jump2Done) {
            jump2 = true;
        }
        onJump = b;
    }

    public void attackSetting() {
        if (moveBackward) {
            return;
        }
        if (superCharge) {
            return;
        }
        if (up && !attacking) {
            attackUp = true;
        } else {
            attacking = true;
        }
    }

    public void chargeSetting() {
        if (moveBackward) {
            return;
        }
        if (!attacking && !attackUp && !superCharge) {
            superCharge = true;
            SoundsCollection.play("playercharge");
            superChargeTick = 0;
        }
    }

    public void dashSetting(boolean b) {
        if (!b) {
            onDash = false;
        } else if (b && !falling) {
            onDash = true;
        }
    }

    public boolean dashNow() {
        return onDash;
    }

    public void deadPlayer() {
        health = 0;
        stop();
    }

    public String getTimeToString() {
        int minutes = (int) (time / 3600);
        int seconds = (int) ((time % 3600) / 60);
        return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long t) {
        time = t;
    }

    public void healthPropertySetting(int i) {
        health = i;
    }

    public void livesAmountSetting(int i) {
        lives = i;
    }

    public void obtainAddLife() {
        lives++;
    }

    public void lifeAmountLose() {
        lives--;
    }

    public int getAmountOfLoves() {
        return lives;
    }

    public void upScore(int score) {
        this.score += score;
    }

    public int getScores() {
        return score;
    }

    public void hit(int damage) {
        if (makeInvisible) {
            return;
        }
        SoundsCollection.play("playerhit");
        stop();
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        makeInvisible = true;
        invisibilityCount = 0;
        if (lookingRight) {
            dx = -1;
        } else {
            dx = 1;
        }
        dy = -3;
        moveBackward = true;
        falling = true;
        onJump = false;
    }

    public void reset() {
        health = healthLimit;
        lookingRight = true;
        currentAction = -1;
        stop();
    }

    public void stop() {
        left = right = up = down = makeInvisible
                = onDash = onJump = attacking = attackUp = superCharge = false;
    }

    private void getNextPosition() {

        if (moveBackward) {
            dy += fallSpeed * 2;
            if (!falling) {
                moveBackward = false;
            }
            return;
        }

        double maxSpeed = this.maxSpeed;
        if (onDash) {
            maxSpeed *= 1.75;
        }

        if (left) {
            dx -= speedOfMove;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += speedOfMove;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        if ((attacking || attackUp || superCharge)
                && !(onJump || falling)) {
            dx = 0;
        }

        if (superCharge) {
            superChargeTick++;
            if (lookingRight) {
                dx = speedOfMove * (3 - superChargeTick * 0.07);
            } else {
                dx = -speedOfMove * (3 - superChargeTick * 0.07);
            }
        }

        if (onJump && !falling) {
            dy = jumpStart;
            falling = true;
            SoundsCollection.play("playerjump");
        }

        if (jump2) {
            dy = jump2Start;
            jump2Done = true;
            jump2 = false;
            SoundsCollection.play("playerjump");

        }

        if (!falling) {
            jump2Done = false;
        }

        // falling
        if (falling) {
            dy += fallSpeed;
            if (dy < 0 && !onJump) {
                dy += stopJumpSpeed;
            }
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }

    }

    private void animate(int i) {
        currentAction = i;
        animation.setFrames(sprites.get(currentAction));
        animation.setDelay(makeDelay[currentAction]);
        width = divisionWidth[currentAction];
        height = divisionHeight[currentAction];
    }

    public void update() {

        time++;
        boolean isFalling = falling;
        getNextPosition();
        checkMapFragmentsCollision();
        positionSetting(tempX, tempY);
        if (isFalling && !falling) {
            SoundsCollection.play("playerlands");
        }
        if (dx == 0) {
            x = (int) x;
        }
        if (makeInvisible) {
            invisibilityCount++;
            if (invisibilityCount > 120) {
                makeInvisible = false;
            }
        }

        if (currentAction == ATTACKING
                || currentAction == UPATTACKING) {
            if (animation.justPlayOnce()) {
                attacking = false;
                attackUp = false;
            }
        }
        if (currentAction == CHARGING) {
            if (animation.countPlay(5)) {
                superCharge = false;
            }
            cr.y = (int) y - 20;
            if (lookingRight) {
                cr.x = (int) x - 15;
            } else {
                cr.x = (int) x - 35;
            }

        }
        for (int i = 0; i < enemies.size(); i++) {

            Enemy e = enemies.get(i);

            if (currentAction == ATTACKING
                    && animation.getFrame() == 3 && animation.getCount() == 0) {
                if (e.intersectionOfObjects(ar)) {
                    e.hit(damage);
                }
            }

            if (currentAction == ATTACKING
                    && animation.getFrame() == 3 && animation.getCount() == 0) {
                if (e.intersectionOfObjects(aur)) {
                    e.hit(damage);
                }
            }

            if (currentAction == CHARGING) {
                if (animation.getCount() == 0) {
                    if (e.intersectionOfObjects(cr)) {
                        e.hit(chargeDamage);
                    }
  
                }
            }

            if (!e.alreadyDead() && intersectionOfObjects(e) && !superCharge) {
                hit(e.damagedBy());
            }

            if (e.alreadyDead()) {
                SoundsCollection.play("explode", 2000);
            }

        }
        if (teleportPlayer) {
            if (currentAction != TELEPORTING) {
                animate(TELEPORTING);
            }
        } else if (moveBackward) {
            if (currentAction != moveBACKWARD) {
                animate(moveBACKWARD);
            }
        } else if (health == 0) {
            if (currentAction != DEAD) {
                animate(DEAD);
            }
        } else if (attackUp) {
            if (currentAction != ATTACKING) {
                SoundsCollection.play("playerattack");
                animate(ATTACKING);
                aur.x = (int) x - 15;
                aur.y = (int) y - 50;
            }

        } else if (attacking) {
            if (currentAction != ATTACKING) {
                SoundsCollection.play("playerattack");
                animate(ATTACKING);
                ar.y = (int) y - 6;
                if (lookingRight) {
                    ar.x = (int) x + 10;
                } else {
                    ar.x = (int) x - 40;
                }
            }

        } else if (superCharge) {
            if (currentAction != CHARGING) {
                animate(CHARGING);
            }
        } else if (dy < 0) {
            if (currentAction != JUMPING) {
                animate(JUMPING);
            }
        } else if (dy > 0) {
            if (currentAction != FALLING) {
                animate(FALLING);
            }
        } else if (onDash && (left || right)) {
            if (currentAction != DASHING) {
                animate(DASHING);
            }
        } else if (left || right) {
            if (currentAction != pWalk) {
                animate(pWalk);
            }
        } else if (currentAction != IDLE) {
            animate(IDLE);
        }

        animation.update();

        if (!attacking && !attackUp && !superCharge && !moveBackward) {
            if (right) {
                lookingRight = true;
            }
            if (left) {
                lookingRight = false;
            }
        }

    }

    public void draw(Graphics2D g) {

        if (makeInvisible && !moveBackward) {
            if (invisibilityCount % 10 < 5) {
                return;
            }
        }

        super.draw(g);

    }

}
