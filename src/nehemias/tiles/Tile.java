package nehemias.tiles;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

  // STATIC STUFF HERE

  public static Tile[] tiles = new Tile[256];
  public static Tile grassTile = new GrassTile(0);
  public static Tile dirtTile = new DirtTile(1);
  public static Tile rockTile = new SidesTile(2);
  public static Tile water = new UpDownTile(3);
  public static Tile obstacles = new Obstacles(4);
  public static Tile door = new ExitDoor(5);

  // CLASS

  public static int TILEWIDTH = 64, TILEHEIGHT = 64;

  protected BufferedImage texture;
  public final int id;

  public Tile(BufferedImage texture, int id) {
    this.texture = texture;
    this.id = id;

    tiles[id] = this;

  }

  public void tick() {

  }

  public void render(Graphics g, int x, int y) {

    g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
  }

  public boolean isSolid() {
    return false;
  }

  public int getId() {
    return id;
  }

}
