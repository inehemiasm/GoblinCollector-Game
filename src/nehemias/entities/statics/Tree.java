package nehemias.entities.statics;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import nehemias.game.Handler;
import nehemias.game.gfx.Animation;
import nehemias.game.gfx.Assets;
import nehemias.items.Item;
import nehemias.tiles.Tile;


public class Tree extends StaticEntity {

  private boolean destroyed;
  private Animation tree;
  private Random rand;

  public Tree(Handler handler, float x, float y) {
    super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2);

    bounds.x = 10;
    bounds.y = (int) (height / 1.5f);
    bounds.width = width - 20;
    bounds.height = (int) (height - height / 1.5f);
    destroyed = false;
    tree = new Animation(500, Assets.treeanim);
    rand = new Random();
  }

  @Override
  public void update() {
    tree.update();

  }

  @Override
  public void die() {
    handler.getWorld().getItemManager().addItem(Item.appleItem.createNew((int) x, (int) y));
  }

  @Override
  public void render(Graphics g) {
    int index = rand.nextInt(2);
    g.drawImage(Assets.trees[0], (int) (x - handler.getGameCamera().getxOffset()),
        (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
  }

  private BufferedImage getCurrentAnimationFrame() {
    int index = rand.nextInt(2);
    return Assets.trees[index];

  }

}
