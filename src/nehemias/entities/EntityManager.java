package nehemias.entities;


import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import nehemias.entities.creatures.Player;
import nehemias.entities.creatures.Skeleton;
import nehemias.game.Handler;



public class EntityManager {

  private Handler handler;
  private Player player;
  private Skeleton enemy;
  private ArrayList<Entity> entities;
  private Comparator<Entity> renderSorter = new Comparator<Entity>() {
    @Override
    public int compare(Entity a, Entity b) {
      if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
        return -1;
      return 1;
    }
  };

  public EntityManager(Handler handler, Player player) {
    this.handler = handler;
    this.player = player;
    entities = new ArrayList<Entity>();
    addEntity(player);
  }


  public void tick() {
    Iterator<Entity> it = entities.iterator();
    while (it.hasNext()) {
      Entity e = it.next();
      e.update();
      if (!e.isActive())
        it.remove();
    }
    entities.sort(renderSorter);
  }

  public void render(Graphics g) {
    for (Entity e : entities) {
      e.render(g);
    }
    player.postRender(g);
  }

  public void addEntity(Entity e) {
    entities.add(e);
  }

  // GETTERS SETTERS

  public Handler getHandler() {
    return handler;
  }

  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  public Player getPlayer() {
    return player;
  }

  public Skeleton getSkelton() {
    return enemy;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public void setEnemy(Skeleton enemy) {
    this.enemy = enemy;
  }

  public ArrayList<Entity> getEntities() {
    return entities;
  }

  public void setEntities(ArrayList<Entity> entities) {
    this.entities = entities;
  }



}
