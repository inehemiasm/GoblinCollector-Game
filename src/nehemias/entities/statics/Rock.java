package nehemias.entities.statics;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import nehemias.game.Handler;
import nehemias.game.gfx.Animation;
import nehemias.game.gfx.Assets;
import nehemias.items.Item;
import nehemias.tiles.Tile;



public class Rock extends StaticEntity {
  private Handler handler;
  // Animations
  private Animation coin;
  private boolean destroyed;

  public Rock(Handler handler, float x, float y) {
    super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

    this.handler = super.handler;
    bounds.x = 3;
    bounds.y = (int) (height / 2f);
    bounds.width = width - 6;
    bounds.height = (int) (height - height / 2f);

    coin = new Animation(500, Assets.coin);
    destroyed = true;
  }

  @Override
  public void update() {
    coin.update();

  }

  @Override
  public void die() {
    handler.getWorld().getItemManager().addItem(Item.rockItem.createNew((int) x, (int) y));
    destroyed = true;

  }

  @Override
  public void render(Graphics g) {
    g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
        (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
  }

  private BufferedImage getCurrentAnimationFrame() {


    if (!destroyed) {
      destroyed = false;
      return coin.getCurrentFrame();
    }

    return Assets.rock;

  }

}
