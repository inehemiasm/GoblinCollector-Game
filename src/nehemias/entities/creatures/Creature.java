package nehemias.entities.creatures;

import nehemias.entities.Entity;
import nehemias.game.Handler;
import nehemias.tiles.Tile;

public abstract class Creature extends Entity {

  public static final float DEFAULT_SPEED = 3.0f;
  public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;

  protected float speed;
  protected float xMove, yMove;

  protected static int playerX = 0;
  protected static int playerY = 0;

  public Creature(Handler handler, float x, float y, int width, int height) {
    super(handler, x, y, width, height);
    speed = DEFAULT_SPEED;
    xMove = 0;
    yMove = 0;
    playerX = (int) x;
    playerY = (int) y;
  }

  public void move() {
    if (!checkEntityCollisions(xMove, 0f))
      moveLeftRight();
    if (!checkEntityCollisions(0f, yMove))
      moveUpDown();

  }

  public void moveLeftRight() {
    if (xMove > 0) {// Moving right
      int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;

      if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
          && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
        x += xMove;

      } else {
        x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
      }



    } else if (xMove < 0) {// Moving left
      int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;

      if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
          && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
        x += xMove;
      } else {
        x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
      }


    }
    playerX = (int) x;

  }

  public void moveUpDown() {
    if (yMove < 0) {// Up
      int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

      if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
          && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
        y += yMove;
      } else {
        y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
      }


    } else if (yMove > 0) {// Down
      int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

      if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
          && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
        y += yMove;
      } else {
        y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
      }
    }
    playerY = (int) y;

  }

  protected boolean collisionWithTile(int x, int y) {
    return handler.getWorld().getTile(x, y).isSolid();
  }

  protected boolean collisionWithDoor(int x, int y) {
    return (!handler.getWorld().getTile(x, y).isSolid()
        && (handler.getWorld().getTile(x, y).id == 5));
  }

  // GETTERS SETTERS

  public float getxMove() {
    return xMove;
  }

  public void setxMove(float xMove) {
    this.xMove = xMove;
  }

  public float getyMove() {
    return yMove;
  }

  public void setyMove(float yMove) {
    this.yMove = yMove;
  }

  public int getHealth() {
    return health;
  }


  public void setHealth(int health) {
    this.health = health;
  }

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public static int[] getPlayerLocation() {
    int points[] = new int[2];
    points[0] = playerX;
    points[1] = playerY;

    return points;

  }



}