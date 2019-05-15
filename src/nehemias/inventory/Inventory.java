package nehemias.inventory;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import nehemias.game.Handler;
import nehemias.game.gfx.Assets;
import nehemias.game.gfx.Text;
import nehemias.items.Item;

public class Inventory {

  private Handler handler;
  private boolean active = false;
  private ArrayList<Item> inventoryItems;
  private static String itencount;

  private int invX = 64, invY = 48, invWidth = 350, invHeight = 250, invListCenterX = invX + 171,
      invListCenterY = invY + invHeight / 2 + 5, invListSpacing = 30;

  private int invImageX = 452, invImageY = 82, invImageWidth = 64, invImageHeight = 64;

  private int invCountX = 484, invCountY = 172;

  private int selectedItem = 0;

  public Inventory(Handler handler) {
    this.handler = handler;
    inventoryItems = new ArrayList<Item>();
    this.itencount = " ";
  }

  public void tick() {
    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_I))
      active = !active;
    if (!active)
      return;

    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W))
      selectedItem--;
    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S))
      selectedItem++;

    if (selectedItem < 0)
      selectedItem = inventoryItems.size() - 1;
    else if (selectedItem >= inventoryItems.size())
      selectedItem = 0;
  }

  public void render(Graphics g) {

    if (!active)
      return;



    g.drawImage(Assets.background, invX + 50, invY, invWidth, invHeight, null);

    Text.drawString(g, "INVENTORY", invListCenterX + 50, invListCenterY - 100, true, Color.RED,
        Assets.font28);

    int len = inventoryItems.size();
    if (len == 0)
      return;

    for (int i = -5; i < 6; i++) {
      if (selectedItem + i < 0 || selectedItem + i >= len)
        continue;
      if (i == 0) {
        Text.drawString(g, "* " + inventoryItems.get(selectedItem + i).getName() + " *",
            invListCenterX, invListCenterY + i * invListSpacing, true, Color.BLUE, Assets.font28);
      } else {
        Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX,
            invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
      }
    }

    Item item = inventoryItems.get(selectedItem);
    g.drawImage(item.getTexture(), invListCenterX + 150, invListCenterY + 20, invImageWidth,
        invImageHeight, null);
    Text.drawString(g, Integer.toString(item.getCount()), invListCenterX + 170, invListCenterY,
        true, Color.BLACK, Assets.font28);
  }

  // Inventory methods

  public void addItem(Item item) {
    for (Item i : inventoryItems) {
      if (i.getId() == item.getId()) {
        i.setCount(i.getCount() + item.getCount());
        itencount = String.valueOf(i.getName() + " " + i.getCount());
        return;
      }
    }
    inventoryItems.add(item);
  }

  // GETTERS SETTERS

  public Handler getHandler() {
    return handler;
  }

  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  public static String getItemCount() {
    return itencount;
  }

  public boolean isActive() {
    return active;
  }

}
