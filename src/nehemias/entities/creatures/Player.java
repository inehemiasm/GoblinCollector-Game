package nehemias.entities.creatures;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import nehemias.display.Display;
import nehemias.entities.Entity;
import nehemias.game.Handler;
import nehemias.game.gfx.Animation;
import nehemias.game.gfx.Assets;
import nehemias.inventory.Inventory;
import nehemias.states.State;



public class Player extends Creature {

  // Animations
  private Animation animDown, animUp, animLeft, animRight, animPushup, animAttackUp, animAttackDown,
      previousAnim, animAttackLeft, animAttackRight;
  // Attack timer
  private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
  // Inventory
  private Inventory inventory;
  private boolean push, attackup, attackdown, attackleft, attackright = false, attacking;
  private String valx = "", valy = "";
  private int currentHealth;



  public Player(Handler handler, float x, float y) {
    super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

    bounds.x = 22;
    bounds.y = 44;
    bounds.width = 19;
    bounds.height = 19;
    this.attacking = false;
    this.currentHealth = 10;


    int anim_speed = 100;
    // Goblin Anim
    animDown = new Animation(anim_speed * 2, Assets.goblin_down);
    animUp = new Animation(anim_speed * 2, Assets.goblin_up);
    animLeft = new Animation(anim_speed * 2, Assets.goblin_left);
    animRight = new Animation(anim_speed * 2, Assets.goblin_right);
    animPushup = new Animation(anim_speed * 2, Assets.goblin_pushup);
    previousAnim = null;


    // Goblin Attack Anim
    animAttackDown = new Animation(anim_speed, Assets.goblin_attack_down);
    animAttackUp = new Animation(anim_speed, Assets.goblin_attack_up);
    animAttackLeft = new Animation(anim_speed, Assets.goblin_attack_left);
    animAttackRight = new Animation(anim_speed, Assets.goblin_attack_right);
    inventory = new Inventory(handler);
  }



  @Override
  public void update() {
    // Animations
    animDown.update();
    animUp.update();
    animRight.update();
    animLeft.update();
    animPushup.update();
    animAttackUp.update();
    animAttackDown.update();
    animAttackLeft.update();
    animAttackRight.update();
    if (previousAnim != null)
      previousAnim.update();



    // Movement
    getInput();
    move();
    handler.getGameCamera().centerOnEntity(this);
    // Attack
    checkAttacks();
    // Inventory
    inventory.tick();
    if (won())
      State.setState(handler.getGame().levelCompleted);


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
    ar.height = arSize + 10;

    if (handler.getKeyManager().aUp) {
      ar.x = cb.x + cb.width / 2 - arSize / 2;
      ar.y = cb.y - arSize;
    } else if (handler.getKeyManager().aDown) {
      ar.x = cb.x + cb.width / 2 - arSize / 2;
      ar.y = cb.y + cb.height;
    } else if (handler.getKeyManager().aLeft) {
      ar.x = cb.x - arSize;
      ar.y = cb.y + cb.height / 2 - arSize / 2;

    } else if (handler.getKeyManager().aRight) {
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
        currentHealth = this.getHealth();
        return;
      }
    }

  }

  @Override
  public void die() {

    State.setState(handler.getGame().gameOver);

  }

  public boolean won() {
    if ((x > +1800) && (y >= 1142))
      return true;
    return false;

  }

  private void getInput() {
    xMove = 0;
    yMove = 0;

    if (inventory.isActive())
      return;

    if (handler.getKeyManager().up)
      yMove = -speed;
    if (handler.getKeyManager().down)
      yMove = speed;
    if (handler.getKeyManager().left)
      xMove = -speed;
    if (handler.getKeyManager().right)
      xMove = speed;
    if (handler.getKeyManager().pushup)
      push = true;
    if (handler.getKeyManager().aUp) {
      attackup = true;
      attacking = true;
    }
    if (handler.getKeyManager().aDown) {
      attackdown = true;
      attacking = true;
    }
    if (handler.getKeyManager().aLeft) {
      attackleft = true;
      attacking = true;
    }
    if (handler.getKeyManager().aRight) {
      attackright = true;
      attacking = true;
    }


  }

  @Override
  public void render(Graphics g) {
    g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
        (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    drawHealthBar(g, this.health, Display.displaywidth - 150, 25);



  }

  public void postRender(Graphics g) {
    inventory.render(g);
  }


  private BufferedImage getCurrentAnimationFrame() {
    if (xMove < 0) {
      previousAnim = animLeft;
      return animLeft.getCurrentFrame();

    } else if (xMove > 0) {
      previousAnim = animRight;
      return animRight.getCurrentFrame();
    } else if (yMove < 0) {
      previousAnim = animUp;
      return animUp.getCurrentFrame();
    } else if (push) {
      push = false;
      return animPushup.getCurrentFrame();
    } else if (attackup) {
      attackup = false;
      return animAttackUp.getCurrentFrame();
    } else if (attackdown) {
      attackdown = false;
      return animAttackDown.getCurrentFrame();
    } else if (attackright) {
      attackright = false;
      return animAttackRight.getCurrentFrame();
    } else if (attackleft) {
      attackleft = false;
      return animAttackLeft.getCurrentFrame();

    } else if (yMove > 0) {
      previousAnim = animDown;
      return animDown.getCurrentFrame();
    } else if (previousAnim != null) {
      return previousAnim.getCurrentFrame();
    } else
      return animPushup.getCurrentFrame();
  }

  public Inventory getInventory() {
    return this.inventory;
  }

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  public boolean isAttacking() {
    return attacking;
  }

  public void setAttacking(boolean attacking) {
    this.attacking = attacking;
  }

  public void drawHealthBar(Graphics g, int health, int x, int y) {
    g.setColor(Color.RED.brighter());
    g.fillRect(x - 4, y - 4, DEFAULT_HEALTH * 28, 36);
    g.setColor(Color.BLACK.darker());
    g.fillRect(x, y - 2, DEFAULT_HEALTH * 27, 32);
    g.setColor(Color.RED.darker());
    g.fillRect(x + 2, y, health * 26, 28);
    g.setColor(Color.WHITE);
    g.drawString("Health", x, y);

  }


}
