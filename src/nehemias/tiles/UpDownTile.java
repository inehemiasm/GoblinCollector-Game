package nehemias.tiles;

import nehemias.game.gfx.Assets;

public class UpDownTile extends Tile {

  public UpDownTile(int id) {
    super(Assets.fence_up, id);
  }

  @Override
  public boolean isSolid() {
    return true;
  }

}
