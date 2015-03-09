import java.awt.Polygon;


public class Asteroid extends VectorSprite
{

	int size;
	double damping = 0.5;
	
	public Asteroid(double xposition, double yposition, int size)
	{
		//account for difficulty and then the number of breaks
		//asteroidUpdate is where the size of the asteroids happens
		
		//size will vary, so if this size is bigger.. account for an changes to that
		this.size = size;
		
		//always have 6 or more vertices
		int vertices = (int) (Math.random()*12) + 12 ;
		//distance from origin of each point .. probably not necessary
		double radius;
		//the degree of rotation to get the next point
		double rot =(2*Math.PI)/vertices;
		
		shape = new Polygon();
		drawShape = new Polygon();

		//centre of asteroid
		double cx = 0;
		double cy = 0;
		
		int xnew;
		int ynew;
		
		for (int i = 0; i < vertices; i++){		
			
			radius = (Math.random()*10)+12;
			xnew =(int) (cx + radius * Math.cos(i * rot));
			ynew =(int) (cx + radius * Math.sin(i * rot));
			
			shape.addPoint(size*xnew, size*ynew);
			drawShape.addPoint(size*xnew, size*ynew);
		}
				
		this.xposition = xposition;
		this.yposition = yposition;
		
		xspeed = 5 * (Math.random() - .5);
		yspeed = 5 * (Math.random() - .5);
		
		ROTATION = 0.1 *  (Math.random() - .5);
	}
	
}