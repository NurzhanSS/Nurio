package Objects;

import java.awt.Rectangle;

import GameWindow.GPanel;
import MapFragments.Tile;
import MapFragments.MapFragments;

public abstract class MapObject {

    protected MapFragments MapFragments;
    protected int fragmentSize;
    protected double mapX;
    protected double mapY;

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    protected int width;
    protected int height;

    protected int widthC;
    protected int heightC;

    protected int currRow;
    protected int currCol;
    protected double destX;
    protected double ydest;
    protected double tempX;
    protected double tempY;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean topLeft;
    protected boolean bottomRight;

    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean lookingRight;

    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean onJump;
    protected boolean falling;

    protected double speedOfMove;
    protected double maxSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double stopSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    public MapObject(MapFragments tm) {
        MapFragments = tm;
        fragmentSize = tm.getFragmentSize();
        animation = new Animation();
        lookingRight = true;
    }

    public boolean intersectionOfObjects(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    public boolean intersectionOfObjects(Rectangle r) {
        return getRectangle().intersects(r);
    }

    public boolean contains(Rectangle r) {
        return getRectangle().contains(r);
    }

    public boolean contains(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.contains(r2);
    }

    public Rectangle getRectangle() {
        return new Rectangle(
                (int) x - widthC / 2,
                (int) y - heightC / 2,
                widthC,
                heightC
        );
    }

    public void cornersCalc(double x, double y) {
        int leftTile = (int) (x - widthC / 2) / fragmentSize;
        int rightTile = (int) (x + widthC / 2 - 1) / fragmentSize;
        int topTile = (int) (y - heightC / 2) / fragmentSize;
        int bottomTile = (int) (y + heightC / 2 - 1) / fragmentSize;
        if (topTile < 0 || bottomTile >= MapFragments.getRowsAmount()
                || leftTile < 0 || rightTile >= MapFragments.getColsAmount()) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }
        int tl = MapFragments.getType(topTile, leftTile);
        int tr = MapFragments.getType(topTile, rightTile);
        int bl = MapFragments.getType(bottomTile, leftTile);
        int br = MapFragments.getType(bottomTile, rightTile);
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }

    public void checkMapFragmentsCollision() {

        currCol = (int) x / fragmentSize;
        currRow = (int) y / fragmentSize;

        destX = x + dx;
        ydest = y + dy;

        tempX = x;
        tempY = y;

        cornersCalc(x, ydest);
        if (dy < 0) {
            if (topLeft || topRight) {
                dy = 0;
                tempY = currRow * fragmentSize + heightC / 2;
            } else {
                tempY += dy;
            }
        }
        if (dy > 0) {
            if (bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
                tempY = (currRow + 1) * fragmentSize - heightC / 2;
            } else {
                tempY += dy;
            }
        }

        cornersCalc(destX, y);
        if (dx < 0) {
            if (topLeft || bottomLeft) {
                dx = 0;
                tempX = currCol * fragmentSize + widthC / 2;
            } else {
                tempX += dx;
            }
        }
        if (dx > 0) {
            if (topRight || bottomRight) {
                dx = 0;
                tempX = (currCol + 1) * fragmentSize - widthC / 2;
            } else {
                tempX += dx;
            }
        }

        if (!falling) {
            cornersCalc(x, ydest + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }

    }

    public int getx() {
        return (int) x;
    }

    public int gety() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getwidthC() {
        return widthC;
    }

    public int getheightC() {
        return heightC;
    }

    public boolean islookingRight() {
        return lookingRight;
    }

    public void positionSetting(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void positionOnMap() {
        mapX = MapFragments.getx();
        mapY = MapFragments.gety();
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void jumpSetting(boolean b) {
        onJump = b;
    }

    public boolean ifNotOnScreen() {
        return x + mapX + width < 0
                || x + mapX - width > GPanel.WIDTH
                || y + mapY + height < 0
                || y + mapY - height > GPanel.HEIGHT;
    }

    public void draw(java.awt.Graphics2D g) {
        positionOnMap();
        if (lookingRight) {
            g.drawImage(
                    animation.getImage(),
                    (int) (x + mapX - width / 2),
                    (int) (y + mapY - height / 2),
                    null
            );
        } else {
            g.drawImage(
                    animation.getImage(),
                    (int) (x + mapX - width / 2 + width),
                    (int) (y + mapY - height / 2),
                    -width,
                    height,
                    null
            );
        }
    }

}
