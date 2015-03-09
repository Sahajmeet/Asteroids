import java.awt.Polygon;


public class Bomb extends Bullet
{
	protected static int RELOAD_TIME = 14;
	
	public Bomb(double shipx, double shipy, double shipAngle)
	{
		super(shipx, shipy,  shipAngle);
		
		xposition = shipx;
		yposition = shipy;
		angle = shipAngle;
		
		shape = new Polygon();
		shape.addPoint(0, 0);
		shape.addPoint(5, 0);
		shape.addPoint(5, 5);
		shape.addPoint(0, 5);
		
		drawShape = new Polygon();
		drawShape.addPoint(0, 0);
		drawShape.addPoint(5, 0);
		drawShape.addPoint(5, 5);
		drawShape.addPoint(0, 5);

		THRUST = 2.5;
		
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
}
