package nehemias.game.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage sheet;
	public SpriteSheet(BufferedImage bi) {
		this.sheet = bi;
	}
	
	public BufferedImage crop(int x, int y, int width, int height) {
		
		return sheet.getSubimage(x, y, width, height);
	}

}
