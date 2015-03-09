import java.awt.*;

public class AmmoBox extends VectorSprite
{
	public AmmoBox(double x, double y, double a)
	{
		this.xposition = x;
		this.yposition = y;
		this.angle = a;
		
		int size = 5;
		
		shape = new Polygon();
		shape.addPoint(1*size,size* 1);
		shape.addPoint(-1*size,size* 1);
		shape.addPoint(-1*size,size* -1);
		shape.addPoint(1*size,size* -1);
		
		drawShape = new Polygon();
		drawShape.addPoint(1*size,size* 1);
		drawShape.addPoint(-1*size,size* 1);
		drawShape.addPoint(-1*size,size* -1);
		drawShape.addPoint(1*size,size* -1);
		
		THRUST = 0.5;
		
		xspeed = 5 * (Math.random() - .5);
		yspeed = 5 * (Math.random() - .5);
		
		ROTATION = 0.1 *  (Math.random()*Math.PI - .5);
		
		
		//more probablility of ammoboxes containing normal bullets (4/5 chance)		
		int ammoProb = (int) (Math.random()*5)+5;
		
		if (ammoProb>=4){
			//ammo box contains normal bullets
			
		}
		else if (ammoProb ==5){
			//ammo box contains bombs
			
		}
		
	}
	
	@Override
	public void updatePosition()
	{
		super.updatePosition();
		counter++;
		if(counter >= 300)
			active = false;
	}
}
