package nehemias.game.gfx;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import nehemias.display.Display;


public class Background {


  private BufferedImage image;
  private double x;
  private double y;
  private double dx;
  private double dy;
  private double moveScale;

  /**
   * 
   * @param s Image path from resources.
   * @param ms Move Scale amount (double).
   */
  public Background(String s, double ms) {
    try {
      image = ImageIO.read(getClass().getResourceAsStream(s));
      moveScale = ms;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @param x
   * @param y
   */
  public void setPosition(double x, double y) {
    this.x = (x * moveScale) % Display.displaywidth;
    this.y = (y * moveScale) % Display.displayheight;
  }

  /**
   * 
   * @param dx Move the x-axis by pixel amount.
   * @param dy Move the y-axis by pixel amount.
   */
  public void setVector(double dx, double dy) {
    this.dx = dx;
    this.dy = dy;
  }

  public void update() {
    this.x = (x + dx) % Display.displaywidth;
    this.y = (y + dy) % Display.displayheight;
  }


  public void render(Graphics g) {
    g.drawImage(image, (int) x, (int) y, null);
    if (x < 0) {
      g.drawImage(image, (int) x + Display.displaywidth - 100, (int) y, null);
    }
    if (x > 0) {
      g.drawImage(image, (int) x - Display.displaywidth - 100, (int) y, null);
    }
    if (y < 0) {
      g.drawImage(image, (int) x, (int) y + Display.displayheight - 100, null);
    }
    if (y > 0) {
      g.drawImage(image, (int) x, (int) y - Display.displayheight - 100, null);
    }


  }

}

