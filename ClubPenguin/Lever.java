import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.BorderLayout;
import java.net.URL;
import java.util.*;

public class Lever
{
	private int x;
	private int y;
	
	private Image pic1;
	private Image pic2;
	private Image pic3;

	
	private int width;
	private int height;
	
	
	private boolean hit;
	private boolean visible; 
	
	private boolean left;
	
	private ArrayList<Projectiles> projectileList;
	
	private ArrayList<Thread> threadList;

	public Lever(int x, int y)
	{
		
		this.x = x;
		this.y = y;
		
		this.width = 313;
		this.height = 300;
		
		projectileList = new ArrayList<Projectiles>();
		threadList = new ArrayList<Thread>();
		
		try {
			pic1 = ImageIO.read(new File("itemFiles/LeverImageRight.png"));
			pic2 = ImageIO.read(new File("itemFiles/LeverImageLeft.png"));
		} catch (IOException e) {
			System.out.println("Could not load images. ");
			System.exit(-1);
		}
		left = true;
		
	}
	public void hit(){ //release 1 projectile
		if(left)
			left = false;
		else
			left = true;
		projectileList.add(new Projectiles(560,0));
		threadList.add(new Thread(projectileList.get(projectileList.size()-1)));
		threadList.get(threadList.size()-1).start();
	}
	public void drawMe(Graphics g){
		if(left){
			g.drawImage(pic1,x,y,null);
		}
		else{
			g.drawImage(pic2,x,y,null);
		}
		
		//do more dialogues
	}
	public boolean checkCollision(Graphics g, IceBoss player){
		
		for(int z = 0; z<projectileList.size();z++){
			System.out.println(projectileList.get(z).getX() + " vs. " + player.getX());
			if((player.getX())<projectileList.get(z).getX() && projectileList.get(z).getX()<(player.getX()+250)){
				if((projectileList.get(z).getY()+282)>=(player.getY())){
					//playSound3();
					//player.loseHealth();
					projectileList.get(z).disappear();
					//playSound1();
					z--;
					return true;
				}
			}
		}
		for(int i = 0; i<projectileList.size();i++){
			if(projectileList.get(i).visible()){
				projectileList.get(i).drawMe(g);
			}
			else{
				projectileList.remove(i);
				i--;
			}
		}
		
		return false;
	}
	
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	

}