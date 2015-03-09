import java.awt.*;


public class VectorSprite 
{
	double xposition;
	double yposition;
	double xspeed;
	double yspeed;
	double angle;
	Polygon shape, drawShape;
	double ROTATION;
	double THRUST;
	boolean active = true;
	int counter; 
	
	
	public void paint(Graphics g)
	{
		g.drawPolygon(drawShape);
	}
	
	public void updatePosition()
	{
		xposition += xspeed;
		yposition += yspeed;
		
		int x, y;
		angle += ROTATION;
		for(int i = 0; i < shape.npoints; i++)
		{
			x = (int)Math.round(shape.xpoints[i]*Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
			y = (int)Math.round(shape.xpoints[i]*Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));
			drawShape.xpoints[i] = x;
			drawShape.ypoints[i] = y;
		}
		drawShape.invalidate();
		drawShape.translate((int)Math.round(xposition), (int)Math.round(yposition));
		wrapAround();
	}
	
	public void wrapAround()
	{
		if(xposition > 900)
			xposition = 0;
		else if(xposition < 0)
			xposition = 900;
		if(yposition > 600)
			yposition = 0;
		else if(yposition < 0)
			yposition = 600;
	}
}
