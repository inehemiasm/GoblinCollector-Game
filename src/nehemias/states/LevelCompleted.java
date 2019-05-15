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

public class LevelCompleted extends State {

  private Background bg;
  private UIManager buttons;

  public LevelCompleted(Handler handler) {
    super(handler);
    backgroundImage();
    buttons = new UIManager(handler);
    buttons.addObject(new UIImageButton((Display.displaywidth / 2) - 100,
        Display.displayheight - 300, 128, 64, Assets.btn_exit, new ClickListener() {
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
    bg.render(g);
    buttons.render(g);

    Text.drawString(g, "Congratulations ", Display.displaywidth / 2 - 70, Display.displayheight / 4,
        true, Color.BLACK, Assets.font45);
    buttons.render(g);

  }


  private void backgroundImage() {

    try {
      bg = new Background("/textures/bgimage.png", 1);
      bg.setVector(-.5, 0);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
