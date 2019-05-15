package nehemias.tiles;

import nehemias.game.gfx.Assets;

public class ExitDoor extends Tile {


  public ExitDoor(int id) {
    super(Assets.finishline, id);
  }

  @Override
  public boolean isSolid() {
    return false;
  }

}
