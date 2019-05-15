package nehemias.tiles;

import nehemias.game.gfx.Assets;

public class Obstacles extends Tile {

  public Obstacles(int id) {
    super(Assets.stone, id);
  }

  @Override
  public boolean isSolid() {
    return true;
  }

}
