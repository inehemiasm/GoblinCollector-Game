package nehemias.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import nehemias.entities.Entity;
import nehemias.game.Handler;
import nehemias.game.gfx.Animation;
import nehemias.game.gfx.Assets;
import nehemias.inventory.Inventory;

public class Skeleton extends Creature {



  // Animations
  private Animation animDown, animUp, animLeft, animRight, previousDirection;
  // Attack timer
  private long lastAttackTimer, attackCooldown = 1000, attackTimer = attackCooldown;
  // Inventory
  private Inventory inventory;
  private boolean attackup, attackdown, attackleft, attackright, attacking, active;
  private int points, pPrevX, pPrevY;

  private int currDist;
  private static int playerXpos, playerYpos, enemyXpos, enemyYpos, health;


  public Skeleton(Handler handler, float x, float y) {
    super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

    bounds.x = 22;
    bounds.y = 44;
    bounds.width = 19;
    bounds.height = 19;
    Skeleton.enemyXpos = (int) x;
    Skeleton.enemyYpos = (int) y;
    this.active = false;


    int anim_speed = 100;
    // Goblin Anim
    animDown = new Animation(anim_speed * 2, Assets.skeleton_down);
    animUp = new Animation(anim_speed * 2, Assets.skeleton_up);
    animLeft = new Animation(anim_speed * 2, Assets.skeleton_left);
    animRight = new Animation(anim_speed * 2, Assets.skeleton_right);

    this.pPrevX = playerXpos;
    this.pPrevY = playerYpos;
    this.currDist = 100;



    inventory = new Inventory(handler);



  }

  @Override
  public void update() {
    // Animations
    animDown.update();
    animUp.update();
    animRight.update();
    animLeft.update();
    if (previousDirection != null)
      previousDirection.update();

    // Movement
    getInput();

    if ((pPrevX != playerXpos) || (pPrevY != playerYpos)) {
      // calculate the distance between the player and enemy
      currDist = enemyDistance();
      // Update the position
      pPrevX = playerXpos;
      pPrevY = playerYpos;

    }
    if (currDist < 100 * 100) {
      chasePlayer();

    }



    move();

    // Attack
    if (active)
      checkAttacks();
    // Inventory
    // inventory.tick();

  }

  private void checkAttacks() {
    attackTimer += System.currentTimeMillis() - lastAttackTimer;
    lastAttackTimer = System.currentTimeMillis();
    if (attackTimer < attackCooldown)
      return;

    if (inventory.isActive())
      return;

    Rectangle cb = getCollisionBounds(0, 0);
    Rectangle ar = new Rectangle();
    int arSize = 22;
    ar.width = arSize;
    ar.height = arSize + 8;

    if (playerYpos < enemyYpos) {
      attackup = true;
      ar.x = cb.x + cb.width / 2 - arSize / 2;
      ar.y = cb.y - arSize;
    } else if (playerYpos > enemyYpos) {
      attackdown = true;
      ar.x = cb.x + cb.width / 2 - arSize / 2;
      ar.y = cb.y + cb.height;
    } else if (playerXpos < enemyXpos) {
      attackleft = true;
      ar.x = cb.x - arSize;
      ar.y = cb.y + cb.height / 2 - arSize / 2;

    } else if (playerXpos > enemyXpos) {
      attackright = true;
      ar.x = cb.x + cb.width;
      ar.y = cb.y + cb.height / 2 - arSize / 2;
    } else {
      return;
    }

    attackTimer = 0;

    for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
      if (e.equals(this))
        continue;
      if (e.getCollisionBounds(0, 0).intersects(ar)) {
        e.hurt(1);
        return;
      }
    }

  }

  @Override
  public void die() {
    points += 5;
    System.out.println("You got " + points);
  }

  private void getInput() {
    xMove = 0;
    yMove = 0;

    if (inventory.isActive())
      return;
    playerXpos = Creature.playerX + 10;
    playerYpos = Creature.playerY + 10;
    if (handler.getKeyManager().aUp) {
      attackup = true;
    }
    if (handler.getKeyManager().aDown)
      attackdown = true;
    if (handler.getKeyManager().aLeft) {
      attackleft = true;

    }
    if (handler.getKeyManager().aRight)
      attackright = true;



  }

  @Override
  public void render(Graphics g) {
    g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
        (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    if (active)
      drawHealthBar(g, this.getHealth(), (int) (x - handler.getGameCamera().getxOffset()),
          (int) (y - handler.getGameCamera().getyOffset()));

  }

  public void postRender(Graphics g) {
    inventory.render(g);

  }

  private BufferedImage getCurrentAnimationFrame() {
    if (xMove < 0) {
      previousDirection = animLeft;

      return animLeft.getCurrentFrame();
    } else if (xMove > 0) {

      previousDirection = animRight;
      return animRight.getCurrentFrame();
    } else if (yMove < 0) {

      previousDirection = animUp;
      return animUp.getCurrentFrame();
    }

    else {
      if (previousDirection != null) {
        active = true;
        return previousDirection.getCurrentFrame();
      } else
        // return Assets.tile_grass;
        return Assets.graveyard;

    }
  }

  public int enemyDistance() {
    int difference, dx, dy = 0;
    // distance formula
    dx = (int) Math.abs((int) playerXpos - (x));
    dy = (int) Math.abs((int) playerYpos - (y));
    difference = (dx * dx) + (dy * dy);

    System.out.println("Distance betwen player and enemy = " + difference);
    return difference;



  }

  private void chasePlayer() {

    speed = 1;
    if (playerXpos < x) {
      xMove = -speed;

    }
    if (playerXpos > x) {
      xMove = speed;

    }

    if (playerYpos < y) {
      yMove = -speed;

    }
    if (playerYpos > y) {
      yMove = speed;

    }
    if ((playerXpos == enemyXpos) || (playerYpos == enemyYpos)) {

      System.out.println("Player is dead ");
      System.out.println("Player location " + playerXpos + ", " + playerYpos + " Emnemy location "
          + enemyXpos + ", " + enemyYpos);


    }
  }


  public Inventory getInventory() {
    return inventory;
  }

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  public static int getEnemyXpos() {
    return enemyXpos;
  }

  public static void setEnemyXpos(int enemyXpos) {
    Skeleton.enemyXpos = enemyXpos;
  }

  public static int getEnemyYpos() {
    return enemyYpos;
  }

  public static void setEnemyYpos(int enemyYpos) {
    Skeleton.enemyYpos = enemyYpos;
  }

  public void drawHealthBar(Graphics g, int health, int x, int y) {
    g.setColor(Color.BLACK);
    g.fillRect(x, y, DEFAULT_HEALTH * 10, 11);
    g.setColor(Color.RED);
    g.fillRect(x, y, health * 10, 10);
    g.setColor(Color.BLACK);
    g.drawString("Health", x, y);

  }
}
