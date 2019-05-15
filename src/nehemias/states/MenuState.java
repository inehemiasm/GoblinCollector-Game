package nehemias.states;


import java.awt.Graphics;
import nehemias.display.Display;
import nehemias.game.Handler;
import nehemias.game.gfx.Assets;
import nehemias.game.gfx.Background;
import nehemias.ui.ClickListener;
import nehemias.ui.UIImageButton;
import nehemias.ui.UIManager;



public class MenuState extends State {

  private UIManager buttons;
  private Background bg;

  public MenuState(Handler handler) {
    super(handler);
    buttons = new UIManager(handler);
    handler.getMouseManager().setUIManager(buttons);

    setBackground();
    createButtons();

  }

  private void setBackground() {
    try {
      bg = new Background("/textures/loadingGame1.jpg", 1);
      // bg.setVector(-.5, 0);
      bg.setVector(-0.0, -5);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void tick() {
    bg.update();
    buttons.tick();
    // State.setState(handler.getGame().menuState);

  }

  @Override
  public void render(Graphics g) {
    bg.render(g);
    buttons.render(g);
  }

  public void createButtons() {

    buttons.addObject(new UIImageButton((Display.displaywidth / 2) - 70, Display.displayheight / 3,
        128, 64, Assets.btn_start, new ClickListener() {
          @Override
          public void onClick() {

            State.setState(handler.getGame().gameState);

          }
        }));
    buttons.addObject(new UIImageButton((Display.displaywidth / 2) - 70, Display.displayheight / 2,
        128, 64, Assets.btn_exit, new ClickListener() {
          @Override
          public void onClick() {
            System.exit(0);
          }
        }));

  }


}
