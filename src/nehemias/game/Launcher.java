package nehemias.game;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Launcher {

  public static int screenW, screenH;

  public static void main(String[] args) {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    screenW = (int) screenSize.getWidth();
    screenH = (int) screenSize.getHeight();
    System.out.println("Screen Width " + screenW + " Screen Height " + screenH);

    Game game = new Game("Goblin Collector", (int) screenSize.getWidth(),
        (int) screenSize.getHeight() - 80);
    game.start();
  }

}
