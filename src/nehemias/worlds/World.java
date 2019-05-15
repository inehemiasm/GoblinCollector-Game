package nehemias.worlds;


import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import nehemias.entities.Entity;
import nehemias.entities.EntityManager;
import nehemias.entities.creatures.Player;
import nehemias.entities.creatures.Skeleton;
import nehemias.entities.statics.Rock;
import nehemias.entities.statics.Tree;
import nehemias.game.Handler;
import nehemias.items.ItemManager;
import nehemias.tiles.Tile;
import nehemias.utils.Utils;

public class World {

  private Handler handler;
  private int width, height;
  private int spawnX, spawnY;
  private int[][] tiles;
  private Random rnd;
  private int playerHealth;

  // Entities
  private EntityManager entityManager;
  // Item
  private ItemManager itemManager;

  public World(Handler handler, String path) {
    this.handler = handler;
    this.setPlayerHealth(Entity.DEFAULT_HEALTH);
    entityManager = new EntityManager(handler, new Player(handler, 100, 100));


    itemManager = new ItemManager(handler);
    rnd = new Random();

    for (int i = 0; i < 30; i++) {
      int xpoint = rnd.nextInt(1600) + 30;
      int ypoint = rnd.nextInt(980) + 30;
      entityManager.addEntity(new Tree(handler, xpoint, ypoint));
    }
    for (int i = 0; i < 60; i++) {
      int xpoint = rnd.nextInt(1600) + 30;
      int ypoint = rnd.nextInt(980) + 30;
      entityManager.addEntity(new Skeleton(handler, xpoint, ypoint));

    }
    for (int i = 0; i < 8; i++) {
      int xpoint = rnd.nextInt(1600) + 30;
      int ypoint = rnd.nextInt(980) + 30;
      entityManager.addEntity(new Rock(handler, xpoint, ypoint));

    }
    loadWorld(path);
    entityManager.getPlayer().setX(spawnX);
    entityManager.getPlayer().setY(spawnY);
  }

  public void drawHealthBar(Graphics g, int health, int x, int y) {
    g.setColor(Color.GREEN);
    g.drawRect(x, y, Entity.DEFAULT_HEALTH * 10, 40);
    g.setColor(Color.GREEN);
    g.fillRect(x, y, health * 10, 40);

  }

  public void tick() {
    itemManager.tick();
    entityManager.tick();
    // setPlayerHealth(entityManager.getPlayer().getHealth());
  }

  public void render(Graphics g) {
    int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
    int xEnd = (int) Math.min(width,
        (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
    int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
    int yEnd = (int) Math.min(height,
        (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);

    for (int y = yStart; y < yEnd; y++) {
      for (int x = xStart; x < xEnd; x++) {
        getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
            (int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
      }
    }
    // Items
    itemManager.render(g);
    // Entities
    entityManager.render(g);


  }

  public Tile getTile(int x, int y) {
    if (x < 0 || y < 0 || x >= width || y >= height)
      return Tile.grassTile;

    Tile t = Tile.tiles[tiles[x][y]];
    if (t == null)
      return Tile.dirtTile;
    return t;
  }

  private void loadWorld(String path) {
    String file = Utils.loadFileAsString(path);
    String[] tokens = file.split("\\s+");
    width = Utils.parseInt(tokens[0]);
    height = Utils.parseInt(tokens[1]);
    spawnX = Utils.parseInt(tokens[2]);
    spawnY = Utils.parseInt(tokens[3]);

    tiles = new int[width][height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
      }
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public Handler getHandler() {
    return handler;
  }

  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  public ItemManager getItemManager() {
    return itemManager;
  }

  public void setItemManager(ItemManager itemManager) {
    this.itemManager = itemManager;
  }

  public int getPlayerHealth() {
    return playerHealth;
  }

  public void setPlayerHealth(int playerHealth) {
    this.playerHealth = playerHealth;
  }

}
