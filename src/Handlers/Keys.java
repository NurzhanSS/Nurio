package Handlers;

import java.awt.event.KeyEvent;

public class Keys {

    public static final int keysAmount = 16;

    public static boolean keyCond[] = new boolean[keysAmount];
    public static boolean prevKeyCond[] = new boolean[keysAmount];

    public static int UP = 0;
    public static int LEFT = 1;
    public static int DOWN = 2;
    public static int RIGHT = 3;
    public static int but1 = 4;
    public static int but2 = 5;
    public static int but3 = 6;
    public static int but4 = 7;
    public static int ENTER = 8;
    public static int ESCAPE = 9;

    public static void keyCollection(int i, boolean b) {
        if (i == KeyEvent.VK_UP) {
            keyCond[UP] = b;
        } else if (i == KeyEvent.VK_LEFT) {
            keyCond[LEFT] = b;
        } else if (i == KeyEvent.VK_DOWN) {
            keyCond[DOWN] = b;
        } else if (i == KeyEvent.VK_RIGHT) {
            keyCond[RIGHT] = b;
        } else if (i == KeyEvent.VK_W) {
            keyCond[but1] = b;
        } else if (i == KeyEvent.VK_E) {
            keyCond[but2] = b;
        } else if (i == KeyEvent.VK_R) {
            keyCond[but3] = b;
        } else if (i == KeyEvent.VK_F) {
            keyCond[but4] = b;
        } else if (i == KeyEvent.VK_ENTER) {
            keyCond[ENTER] = b;
        } else if (i == KeyEvent.VK_ESCAPE) {
            keyCond[ESCAPE] = b;
        }
    }

    public static void update() {
        for (int i = 0; i < keysAmount; i++) {
            prevKeyCond[i] = keyCond[i];
        }
    }

    public static boolean pressed(int i) {
        return keyCond[i] && !prevKeyCond[i];
    }

    public static boolean anyPressed() {
        for (int i = 0; i < keysAmount; i++) {
            if (keyCond[i]) {
                return true;
            }
        }
        return false;
    }

}
