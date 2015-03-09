import java.awt.Polygon;

public class SpaceCraft extends VectorSprite
{
	public static final int BASIC_BULLET = 0;
	public static final int BOMB = 1;
	int armed;
	int size;
	int [] ammo;
	int reloadTimes [] = new int []{0, 0};
	boolean [] loaded;
	int lives = 5;
	boolean aoeShot = true;
	public SpaceCraft()
	{
		xposition = 450;
		yposition = 300;
		
		size = 2;
		
		shape = new Polygon();
		shape.addPoint(size*7, size*0);
		shape.addPoint(size*-5, size*5);
		shape.addPoint(size*-5, size*-5);
		
		drawShape = new Polygon();
		drawShape.addPoint(size*7, size*0);
		drawShape.addPoint(size*-5, size*5);
		drawShape.addPoint(size*-5, size*-5);
		
		THRUST = 0.5;
		
		
		//Build Ammunition Array for Different Bullets
		armed = BASIC_BULLET;
		ammo = new int []{100, 3};
		loaded = new boolean [] {true, true};
	}
	
	@Override
	public void updatePosition()
	{
		//Update Reloading
		
		for(int i = 0; i < loaded.length; i++)
		{
			if (loaded[i] == false)
			{
				reloadTimes[i] -=1;
				if(reloadTimes[i] <= 0)
				{
					loaded[i] = true;
				}
			}
		}
		
		if(active == false)
		{
			shape = new Polygon();
			shape.addPoint(size*7, size*0);
			shape.addPoint(size*-5, size*5);
			shape.addPoint(size*-5, size*-5);
			
			drawShape = new Polygon();
			drawShape.addPoint(size*7, size*0);
			drawShape.addPoint(size*-5, size*5);
			drawShape.addPoint(size*-5, size*-5);
		}
		
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
	
	public void accelerate()
	{
		xspeed += THRUST*Math.cos(angle);
		yspeed += THRUST*Math.sin(angle);
	}
	
	public void respawn()
	{
		if(lives > 0)
		{
		active = true;
		xposition = 450;
		yposition = 300;
		xspeed = 0;
		yspeed = 0;
		counter ++;
		}
	}
	
	public void setBulletType(int b)
	{
		//Change Bullet Type ONLY if There Is Ammo
		if(b == 0 && ammo[0] > 0)
		{
			armed = BASIC_BULLET;
		}
		if(b == 1 && ammo[1] > 0)
		{
			armed = BOMB;
		}
	}
	
	public int getArmedType()
	{
		return armed;
	}
	
	public int getAmmoIndex(int i)
	{
		return ammo[i];
	}
	
	public int getArmedAmmo()
	{
		return ammo[armed];
	}
	
	public void removedArmedAmmo()
	{
		ammo[armed] -= 1;
		if(ammo[armed] < 0)
			ammo[armed] = 0;
	}
	
	public boolean isArmed()
	{
		return loaded[getArmedType()];
	}
	
	public void reloadArmed()
	{
		loaded[getArmedType()] = false;
		if(getArmedType() == BASIC_BULLET)
			reloadTimes[getArmedType()] = Bullet.RELOAD_TIME;
		if(getArmedType() == BOMB)
			reloadTimes[getArmedType()] = Bomb.RELOAD_TIME;
	}
	
	public void aoeSpent()
	{
		aoeShot = false;
	}
	
	public boolean aoeUsed()
	{
		return aoeShot;
	}
	
	public void setSize(int size)
	{
		shape = new Polygon();
		shape.addPoint(size*7,size*0);
		shape.addPoint(size*-5, size*5);
		shape.addPoint(size*-5, size*-5);
		
		drawShape = new Polygon();
		drawShape.addPoint(size*7, size*0);
		drawShape.addPoint(size*-5, size*5);
		drawShape.addPoint(size*-5, size*-5);
	}
}
