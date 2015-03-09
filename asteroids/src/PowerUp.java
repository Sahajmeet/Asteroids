import java.awt.Polygon;


public class PowerUp extends VectorSprite
{
	//Declare Variables
	private int size;
	private int type;
	public final static int SHRINK = 1;
	public final static int HEART = 2;
	
	public PowerUp(double xposition, double yposition, int type)
	{
		size = 5;
		this.xposition = xposition;
		this.yposition = yposition;
		this.type = type;
		
		if(type == SHRINK)
		{
			shape = new Polygon();
			shape.addPoint(size*0, size*2);
			shape.addPoint(size*2, size*1);
			shape.addPoint(size*2, size*-1);
			shape.addPoint(size*0, size*-2);
			shape.addPoint(size*-2, size*-1);
			shape.addPoint(size*-2, size*1);
			
			drawShape = new Polygon();
			drawShape.addPoint(size*0, size*2);
			drawShape.addPoint(size*2, size*1);
			drawShape.addPoint(size*2, size*-1);
			drawShape.addPoint(size*0, size*-2);
			drawShape.addPoint(size*-2, size*-1);
			drawShape.addPoint(size*-2, size*1);
		}
		
		else if(type == HEART)
		{
			shape = new Polygon();
			shape.addPoint(size*0, size*1);
			shape.addPoint(size*1, size*2);
			shape.addPoint(size*2, size*1);
			shape.addPoint(size*1, size*-1);
			shape.addPoint(size*0, size*-2);
			shape.addPoint(size*-1, size*-1);
			shape.addPoint(size*-2, size*1);
			shape.addPoint(size*-1, size*2);
			
			drawShape = new Polygon();
			drawShape.addPoint(size*0, size*1);
			drawShape.addPoint(size*1, size*2);
			drawShape.addPoint(size*2, size*1);
			drawShape.addPoint(size*1, size*-1);
			drawShape.addPoint(size*0, size*-2);
			drawShape.addPoint(size*-1, size*-1);
			drawShape.addPoint(size*-2, size*1);
			drawShape.addPoint(size*-1, size*2);
		}
		
		xspeed = THRUST*Math.cos(angle);
		yspeed = THRUST*Math.sin(angle);

		ROTATION = 0.1 *  (Math.random() - .5);
	}
	
	@Override 
	public void updatePosition()
	{
		super.updatePosition();
		counter++;
		if(counter >= 200)
			active = false;
	}
	
	public int getType()
	{
		return type;
	}
}
