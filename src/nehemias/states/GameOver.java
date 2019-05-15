package nehemias.states;

import java.awt.Color;
import java.awt.Graphics;
import nehemias.display.Display;
import nehemias.game.Handler;
import nehemias.game.gfx.Assets;
import nehemias.game.gfx.Background;
import nehemias.game.gfx.Text;
import nehemias.ui.ClickListener;
import nehemias.ui.UIImageButton;
import nehemias.ui.UIManager;

public class GameOver extends State {

  private Background bg;
  private UIManager buttons;

  public GameOver(Handler handler) {
    super(handler);
    backgroundImage();
    buttons = new UIManager(handler);
    buttons.addObject(new UIImageButton((Display.displaywidth / 2) - 120,
        Display.displayheight - 370, 200, 100, "Exit", new ClickListener() {
          @Override
          public void onClick() {
            System.exit(0);
          }
        }));


  }

  @Override
  public void tick() {
    bg.update();
    buttons.tick();
    handler.getMouseManager().setUIManager(buttons);
  }

  @Override
  public void render(Graphics g) {
    // bg.render(g);
    g.fillRect(0, 0, Display.displaywidth, Display.displayheight);
    buttons.render(g);

    Text.drawString(g, "GAME OVER", Display.displaywidth / 2, Display.displayheight - 500, true,
        Color.RED, Assets.font45);
    buttons.render(g);

  }


  private void backgroundImage() {

    try {
      bg = new Background("/textures/gameoverbg.jpg", 1);
      bg.setVector(0, 0);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}


