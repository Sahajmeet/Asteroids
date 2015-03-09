import java.awt.Polygon;


public class Bullet extends VectorSprite
{
	
	protected static int RELOAD_TIME = 10;
	
	public Bullet(double shipx, double shipy, double shipAngle)
	{
		xposition = shipx;
		yposition = shipy;
		angle = shipAngle;
		
		shape = new Polygon();
		shape.addPoint(0, 0);
		shape.addPoint(1, 0);
		shape.addPoint(0, 1);
		shape.addPoint(1, 1);
		
		drawShape = new Polygon();
		drawShape.addPoint(0, 0);
		drawShape.addPoint(1, 0);
		drawShape.addPoint(0, 1);
		drawShape.addPoint(1, 1);

		THRUST = 4.5;
		
		xspeed = THRUST*Math.cos(angle);
		yspeed = THRUST*Math.sin(angle);
		
	}
	
	@Override
	public void updatePosition()
	{
		super.updatePosition();
		counter++;
		if(counter >= 200)
			active = false;
	}
	
	public int getLoadTime()
	{
		return RELOAD_TIME;
	}
}
