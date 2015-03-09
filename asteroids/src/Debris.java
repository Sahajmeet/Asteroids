import java.awt.Polygon;


public class Debris extends VectorSprite
{
	public Debris(double x, double y)
	{
		xposition = x;
		yposition = y;
		angle = Math.random() * (Math.PI*2);
		
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

		THRUST = 2.5;
		
		xspeed = THRUST*Math.cos(angle);
		yspeed = THRUST*Math.sin(angle);
	}
	
	@Override
	public void updatePosition()
	{
		super.updatePosition();
		counter++;
		if(counter >= 100)
			active = false;
	}
}
