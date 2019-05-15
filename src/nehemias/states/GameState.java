package nehemias.states;


import java.awt.Graphics;
import nehemias.game.Handler;
import nehemias.worlds.World;



public class GameState extends State {

  private World world;

  public GameState(Handler handler) {
    super(handler);
    world = new World(handler, "res/worlds/world3.txt");
    handler.setWorld(world);
  }

  @Override
  public void tick() {
    world.tick();
  }

  @Override
  public void render(Graphics g) {
    world.render(g);
  }

}
