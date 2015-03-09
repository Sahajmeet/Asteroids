import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.Timer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Main extends Applet implements KeyListener, ActionListener
{
	//Declare Variables & Objects
	Image offscreen;
	Graphics offg;
	SpaceCraft ship;
	ArrayList <Bullet>bullets;
	ArrayList <Asteroid> asteroids;
	ArrayList <Debris> debris;
	ArrayList <PowerUp> powerups;
	ArrayList <AmmoBox> ammobox;
	Timer timer;
	AdvancedPlayer playMP3, shot, explode;
	
	int score;
	boolean pause = false;
	long startTime; 
	int time, pastTime;
	
	//number of asteroids based on difficulty; have to get this from the menu
		//when selecting difficulty; for now initialize to 4
		int numAstr = 3;
		//initial size of asteroids based on difficulty; for now initialize to 3
		//have this size decrease every time an asteroid is hit
		int sizeAstr = 3;
		//number of smaller asteroids created for each time one is hit; initialize to 3
		int numSmAstr = 2;
		//change xspeed and yspeed of asteroids based on difficulty 
		//multiply this by the randomly given speed for the asteroid
		//initialize to 2.5
		double xspd = 1.5;
		double yspd = 1.5;
		
		//ENDLESS VERSION OF GAME
		//number of asteroids to respawn
		int respawnAstr = 5;
		//respawn asteroids after this many asteroids remain on screen
		int minRespawn = 3;
	
	boolean[] KEYS = new boolean[256];
	
	@Override
	public void init()
	{
		
		this.setSize(900, 600);
		this.addKeyListener(this);
		timer = new Timer(20, this);
		timer.start();
		startTime = System.currentTimeMillis();
		time = 0;
		pastTime = 0;
		ship = new SpaceCraft();
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();
		debris = new ArrayList<Debris>();
		ammobox = new ArrayList<AmmoBox>();
		powerups = new ArrayList<PowerUp>();
	
		powerups = new ArrayList<PowerUp>();
		for(int i = 0; i < numAstr; i++){
			asteroids.add(new Asteroid(Math.random()*900, Math.random()*600, sizeAstr));
		}
		
		offscreen = createImage(this.getWidth(), this.getHeight());
		offg = offscreen.getGraphics();
		
		try
		{
		    playMP3 = new AdvancedPlayer(new FileInputStream("song.mp3"));
		    shot = new AdvancedPlayer(new FileInputStream("laser.mp3"));
		    explode = new AdvancedPlayer(new FileInputStream("explosion.mp3"));
		    
		}
		catch(Exception exc){
		    exc.printStackTrace();
		    System.out.println("Failed to play the file.");
		}
		
		new Thread()
		{
			  public void run()
			  {
				  try 
				  {
					playMP3.play();
					playMP3.close();
				  } 
				  catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			}.start();
	}

	public void update(Graphics g)
	{
		paint(g);
	}
	
	@Override
	public void paint(Graphics g)
	{
		offg.setColor(Color.BLACK);
		offg.fillRect(0, 0, 900, 600);
		offg.setColor(Color.white);
		drawBackground();
		offg.setColor(Color.GREEN);
		if(ship.active)
		{
			ship.paint(offg);
		}
		offg.setColor(Color.ORANGE);
		for(int i = 0; i < ammobox.size(); i++)
		{
			ammobox.get(i).paint(offg);
		}
		offg.setColor(Color.GREEN);
		for(int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).paint(offg);
			
		}
		for(int i = 0; i < asteroids.size(); i++)
		{
			asteroids.get(i).paint(offg);
		}
		offg.setColor(Color.white);
		for(int i = 0; i < debris.size(); i++)
		{
			debris.get(i).paint(offg);
		}
		for(int i = 0; i < powerups.size(); i++)
		{
			if(powerups.get(i).getType() == PowerUp.SHRINK)
				offg.setColor(Color.CYAN);
			else if(powerups.get(i).getType() == PowerUp.HEART)
				offg.setColor(Color.RED);
			powerups.get(i).paint(offg);
		}
		offg.setColor(Color.white);
		offg.drawString("Score: " + Integer.toString(score), 50, 50);
		offg.drawString("Lives: " + Integer.toString(ship.lives), 800, 50);
		offg.drawString("Bullets (1): " + Integer.toString(ship.getAmmoIndex(0)), 50, 100);
		offg.drawString("Bombs (2): " + Integer.toString(ship.getAmmoIndex(1)), 50, 150);
		offg.drawString("Spread (F): " + ship.aoeUsed(), 50, 200);
		offg.drawString("Time: " +  Integer.toString(time), 450, 50);
		
		if(ship.lives == 0)
		{
			offg.drawString("GAME OVER", 450, 300);
		}
		
		if(asteroids.size()==0)
		{
			offg.drawString("YOU WIN", 450, 300);
			
		}
		
		if(pause){
			offg.drawString("PAUSED", 450, 300);
		}
		
		g.drawImage(offscreen, 0, 0, this);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{

		int key = arg0.getKeyCode();
		KEYS[key] = true;
	
		if(key == KeyEvent.VK_P){
			pause = !pause;
			if(!pause){
				pastTime = time;
				startTime = System.currentTimeMillis();
			}
		}
		if(key==KeyEvent.VK_C){
			init();	
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		int key = arg0.getKeyCode();
		KEYS[key] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(!pause){
			ship.updatePosition();
			bulletUpdate();
			asteroidUpdate();
			debrisUpdate();
			powerupUpdate();
			checkKey();
			if (!(asteroids.size()==0) && !(ship.lives == 0)){
			checkTime();
			}
			if (ship.getAmmoIndex(0)<90){
				createAmmoBoxes();
				updateAmmoBoxes();
			}
			if (asteroids.size() < minRespawn){
				createAsteroids();
			}
		 
			if(ship.active == false)
			{
				checkRespawn();
			}
			repaint();
		}
	}

	public void createAsteroids(){
		while (asteroids.size()< respawnAstr){
			asteroids.add(new Asteroid(Math.random()*900, Math.random()*600, sizeAstr));
		}
	}
	
	public void createAmmoBoxes(){
		if (ammobox.size()==0){  		
    		ammobox.add(new AmmoBox(Math.random()*900, Math.random()*600, Math.random()*Math.PI));
		}
	}
	
	public void updateAmmoBoxes()
	{
		for(int i = 0; i < ammobox.size(); i ++)
		{ 
			ammobox.get(i).updatePosition();
			if(collision(ship, ammobox.get(i)))
			{
				ship.ammo[0] = ship.getAmmoIndex(0) +5;
				ammobox.get(i).active = false;
				ammobox.remove(i);
			}
		}
	}

	public boolean collision(VectorSprite obj1, VectorSprite obj2)
	{
		
		for(int i = 0; i < obj1.drawShape.npoints; i++)
		{
			if(obj2.drawShape.contains(obj1.drawShape.xpoints[i], obj1.drawShape.ypoints[i]))
				return true;
		}
		return false;
	}
	
	public void checkCollision()
	{
		
		for(int i = 0; i < asteroids.size(); i ++)
		{
			for(int j = 0; j < bullets.size(); j++)
			{
				if(collision(bullets.get(j), asteroids.get(i)))
				{
					asteroids.remove(i);
					break;
				}
			}
		}
	}

	
	public void bulletUpdate()
	{
		for(int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).updatePosition();
			for(int j = 0; j < asteroids.size(); j++)
			{
				if(collision(bullets.get(i), asteroids.get(j)))
				{
					asteroids.get(j).active = false;
					bullets.get(i).active = false;
					if(bullets.get(i) instanceof Bomb)
					{
						for(int k = 0; k < 7; k++)
						{
							bullets.add(new Bullet(bullets.get(i).xposition, bullets.get(i).yposition, Math.random() * (Math.PI*2)));
						}
					}
				}
			}
			if(!bullets.get(i).active)
			{
				bullets.remove(i);
			}
		}
	}
	
	public void asteroidUpdate()
	{
		for(int i = 0; i < asteroids.size(); i++)
		{
			asteroids.get(i).updatePosition();
			if(collision(ship, asteroids.get(i)) && ship.active == true)
			{
				ship.active = false;
				ship.lives -=1;
			}
			if(!asteroids.get(i).active)
			{
				explosion(asteroids.get(i).xposition, asteroids.get(i).yposition);
				
				int sizeOfMini = asteroids.get(i).size-1;
				
				if (sizeOfMini > 0)
				for (int j = 0; j < numSmAstr; j++){
					asteroids.add(new Asteroid(asteroids.get(i).xposition, asteroids.get(i).yposition, sizeOfMini));
					asteroids.get(asteroids.size() - 1).xspeed = asteroids.get(asteroids.size() - 1).xspeed*xspd;
					asteroids.get(asteroids.size() - 1).yspeed = asteroids.get(asteroids.size() - 1).yspeed*yspd;
				}
				score = score + (sizeAstr - (sizeOfMini));
				asteroids.remove(i);
				
			}
		}
	}
	
	public void debrisUpdate()
	{
		for(int i = 0; i < debris.size(); i++)
		{
			debris.get(i).updatePosition();
			if(debris.get(i).active == false)
			{
				debris.remove(i);
			}
		}
	}
	
	public void powerupUpdate()
	{
		for(int i = 0; i < powerups.size(); i++)
		{
			if(powerups.get(i).active == false)
			{
				powerups.remove(i);
			}
			else
			{
				
				powerups.get(i).updatePosition();
				
				if(collision(ship, powerups.get(i)) && ship.active == true)
				{
					if(powerups.get(i).getType() == PowerUp.SHRINK)
					{
						ship.setSize(1);
					}
					else if(powerups.get(i).getType() == PowerUp.HEART)
					{
						ship.lives+=1;
					}
					powerups.remove(i);
				}
			
			}
		}
	}
	
	public void explosion(double x, double y)
	{
		if(asteroids.size() >=7 && powerups.size() <3 && Math.random() > 0.7)
		{
			powerups.add(new PowerUp(x, y, 1));
		}
		
		if(ship.lives <=4 && powerups.size() <4 && Math.random() > 0.6)
		{
			powerups.add(new PowerUp(x, y, 2));
		}
		
		
		
		playExplosionSound();
		for(int i = 0; i < 10; i++)
		{
			debris.add(new Debris(x, y));
		}
	}
	
	public boolean isSafe()
	{
		//respawn ship only if no asteroids in the centre
				for(int i = 0; i<asteroids.size();i++){
					//distance from ship to any asteroid
					double dist = Math.sqrt(Math.pow((Math.abs(asteroids.get(i).xposition - ship.xposition)),2)+Math.pow((Math.abs(asteroids.get(i).yposition - ship.yposition)),2));
					if (dist < 100){
						return false;
					}
				}
				return true;
	}
	
	public void checkRespawn()
	{
		if (isSafe()&&(!ship.active)){
			ship.respawn();
		}
	}
	
	public void checkKey(){
		//right
		if(KEYS[39]){
			ship.angle += 0.1;
		}
		//left
		if(KEYS[37]){
			ship.angle -= 0.1;
		}
		//up
		if(KEYS[38]){
			ship.accelerate();
		}
		//space
		if(KEYS[32] && ship.active)
		{
			
			if(ship.getArmedType() == 0 && ship.getArmedAmmo() > 0 && ship.isArmed())
			{
				bullets.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle));
				playShootSound();
				ship.removedArmedAmmo();
				ship.reloadArmed();
			}
			else if(ship.getArmedType() == 1 && ship.getArmedAmmo() > 0 && ship.isArmed())
			{
				bullets.add(new Bomb(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle));
				playShootSound();
				ship.removedArmedAmmo();
				ship.reloadArmed();
			}
		}
		//f
		if(KEYS[70] && ship.aoeUsed())
		{
			for(int k = 0; k < 10; k++)
			{
				bullets.add(new Bullet(ship.xposition, ship.yposition, Math.random() * (Math.PI*2)));
			}
			ship.aoeSpent();
		}
		//1
		if(KEYS[49])
		{
			ship.setBulletType(0);
		}
		//2
		if(KEYS[50])
		{
			ship.setBulletType(1);
		}
	}
	
	public void checkTime(){
		//if (!(asteroids.size()==0) || !(ship.lives == 0)){
		time = ((int)(Math.abs(startTime - (System.currentTimeMillis()))/1000)) + pastTime;
		//}
//		else if (ship.lives == 0){
//			time = pastTime;
//		}
	}
	
	public void playShootSound()
	{
		new Thread()
		{
			  public void run()
			  {
				  try {
					  	shot = new AdvancedPlayer(new FileInputStream("laser.mp3"));
						shot.play();
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  }
			}.start();
	}
	
	public void playExplosionSound()
	{
		new Thread()
		{
			  public void run()
			  {
				  try {
						
						explode = new AdvancedPlayer(new FileInputStream("explosion.mp3"));
						explode.play();
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  }
			}.start();
	}
	
	public void drawBackground()
	{

		offg.drawRect(5, 7, 1, 1);
		offg.drawRect(6, 600, 1, 1);
		offg.drawRect(345, 234, 1, 1);
		offg.drawRect(123, 789, 1, 1);
		offg.drawRect(500, 5677, 1, 1);
		offg.drawRect(123, 123, 1, 1);
		offg.drawRect(234, 345, 1, 1);
		offg.drawRect(278, 234, 1, 1);
		offg.drawRect(527, 678, 1, 1);
		offg.drawRect(567, 789, 1, 1);
		offg.drawRect(34, 789, 1, 1);
		offg.drawRect(67, 789, 1, 1);
		offg.drawRect(678, 134, 1, 1);
		offg.drawRect(234, 456, 1, 1);
		offg.drawRect(789, 790, 1, 1);
		offg.drawRect(876, 854, 1, 1);
		offg.drawRect(842, 425, 1, 1);
		offg.drawRect(700, 500, 1, 1);
		offg.drawRect(600, 630, 1, 1);
	}
}
