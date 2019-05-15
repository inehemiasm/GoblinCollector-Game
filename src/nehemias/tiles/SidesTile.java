package nehemias.tiles;

import nehemias.game.gfx.Assets;

public class SidesTile extends Tile {

  public SidesTile(int id) {
    super(Assets.fence_sides, id);
  }

  @Override
  public boolean isSolid() {
    return true;
  }

}
