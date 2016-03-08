package MapFragments;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import GameWindow.GPanel;

public class MapFragments {

    private double x;
    private double y;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    private double tween;
    private int[][] map;
    private int fragmentSize;
    private int rowsAmount;
    private int colsAmount;
    private int width;
    private int height;
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    private int balancingRows;
    private int balancingCols;
    private int rowsDrawAmount;
    private int colsDrawAmount;

    private boolean earthquake;
    private int intensity;

    public MapFragments(int fragmentSize) {
        this.fragmentSize = fragmentSize;
        rowsDrawAmount = GPanel.HEIGHT / fragmentSize + 2;
        colsDrawAmount = GPanel.WIDTH / fragmentSize + 2;
        tween = 0.07;
    }

    public void fragmentsLoad(String s) {

        try {

            tileset = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            numTilesAcross = tileset.getWidth() / fragmentSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++) {
                subimage = tileset.getSubimage(
                        col * fragmentSize,
                        0,
                        fragmentSize,
                        fragmentSize
                );
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                subimage = tileset.getSubimage(
                        col * fragmentSize,
                        fragmentSize,
                        fragmentSize,
                        fragmentSize
                );
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void mapLoading(String s) {

        try {

            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in)
            );

            colsAmount = Integer.parseInt(br.readLine());
            rowsAmount = Integer.parseInt(br.readLine());
            map = new int[rowsAmount][colsAmount];
            width = colsAmount * fragmentSize;
            height = rowsAmount * fragmentSize;

            minX = GPanel.WIDTH - width;
            maxX = 0;
            minY = GPanel.HEIGHT - height;
            maxY = 0;

            String delims = "\\s+";
            for (int row = 0; row < rowsAmount; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < colsAmount; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getFragmentSize() {
        return fragmentSize;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRowsAmount() {
        return rowsAmount;
    }

    public int getColsAmount() {
        return colsAmount;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getType();
    }

    public boolean quake() {
        return earthquake;
    }

    public void setTween(double d) {
        tween = d;
    }

    public void makeEarthquake(boolean b, int i) {
        earthquake = b;
        intensity = i;
    }

    public void setBounds(int i1, int i2, int i3, int i4) {
        minX = GPanel.WIDTH - i1;
        minY = GPanel.WIDTH - i2;
        maxX = i3;
        maxY = i4;
    }

    public void positionSetting(double x, double y) {

        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        balancingCols = (int) -this.x / fragmentSize;
        balancingRows = (int) -this.y / fragmentSize;

    }

    public void fixBounds() {
        if (x < minX) {
            x = minX;
        }
        if (y < minY) {
            y = minY;
        }
        if (x > maxX) {
            x = maxX;
        }
        if (y > maxY) {
            y = maxY;
        }
    }

    public void update() {
        if (earthquake) {
            this.x += Math.random() * intensity - intensity / 2;
            this.y += Math.random() * intensity - intensity / 2;
        }
    }

    public void draw(Graphics2D g) {

        for (int row = balancingRows; row < balancingRows + rowsDrawAmount; row++) {

            if (row >= rowsAmount) {
                break;
            }

            for (int col = balancingCols; col < balancingCols + colsDrawAmount; col++) {

                if (col >= colsAmount) {
                    break;
                }
                if (map[row][col] == 0) {
                    continue;
                }

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(
                        tiles[r][c].getImage(),
                        (int) x + col * fragmentSize,
                        (int) y + row * fragmentSize,
                        null
                );

            }

        }

    }

}
