package Handlers;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteCollection {

    public static BufferedImage[][] Explosion = load("/Sprites/Enemies/Explosion.gif", 30, 30);
    public static BufferedImage[][] enemyNumberOne = load("/Sprites/Enemies/enemyNumberOne.gif", 25, 25);

    public static BufferedImage[][] load(String s, int w, int h) {
        BufferedImage[][] ret;
        try {
            BufferedImage spritesheet = ImageIO.read(SpriteCollection.class.getResourceAsStream(s));
            int width = spritesheet.getWidth() / w;
            int height = spritesheet.getHeight() / h;
            ret = new BufferedImage[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading graphics.");
            System.exit(0);
        }
        return null;
    }

}
