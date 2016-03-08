package Objects;

public class PlayerSave {

    private static int lives = 3;
    private static int health = 5;
    private static long time = 0;

    public static void init() {
        lives = 3;
        health = 5;
        time = 0;
    }

    public static int getAmountOfLoves() {
        return lives;
    }

    public static void livesAmountSetting(int i) {
        lives = i;
    }

    public static int getHealth() {
        return health;
    }

    public static void healthPropertySetting(int i) {
        health = i;
    }

    public static long getTime() {
        return time;
    }

    public static void setTime(long t) {
        time = t;
    }

}
