package nehemias.entities.statics;

import nehemias.entities.Entity;
import nehemias.game.Handler;



public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int width, int height){
		super(handler, x, y, width, height);
	}

}