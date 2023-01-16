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
public class Dummy
{
	private int x;
	private int y;
	
	
	//will have commands that have dialogue pop ups
	private Image pic1;
	private Image pic2;
	private Image pic3;
	private Image pic4;
	private Image pic5;
	private Image pic6;
	private Image pic7;
	
	private int width;
	private int height;
	private int dialogue;
	
	private boolean hit;
	private boolean visible; 
	private boolean finished;
	

	public Dummy(int x, int y, int type)
	{
		
		this.x = x;
		this.y = y;
		
		this.width = 200;
		this.height = 150;
		dialogue = 0;
		try {
			pic1 = ImageIO.read(new File("enemyFiles/Dummy.png"));
			if(type==1){
				pic2 = new ImageIcon("enemyFiles/SmallExplosion.gif").getImage();
			}
				
			if(type==2){
				pic2 = new ImageIcon("enemyFiles/SmallExplosion2.gif").getImage();
			}

			pic3 = ImageIO.read(new File("enemyFiles/DummyDialogue1.png"));
			pic4 = ImageIO.read(new File("enemyFiles/DummyDialogue2.png"));
			pic5 = ImageIO.read(new File("enemyFiles/DummyDialogue3.png"));
			pic6 = ImageIO.read(new File("enemyFiles/DummyDialogue4.png"));
			pic7 = ImageIO.read(new File("enemyFiles/DummyDialogue5.png"));
		} catch (IOException e) {
			System.out.println("Could not load images. ");
			System.exit(-1);
		}
		hit = false;
		visible = false;
		finished = false;
	}
	public void setDialogue(int x)
	{
		dialogue = x;
	}
	

	public void drawMe(Graphics g)
	{
		//g.setColor(orange);
		//g.fillRect(x,y,width,height);
		//g.setColor(gray);
		//g.fillRect(x,y+15,75,20);
		g.drawImage(pic1, x, y, null);
		if(hit)
			g.drawImage(pic2, x-100, y, null);
		if(dialogue==1)
			g.drawImage(pic4, x, y-100, null);
		if(dialogue==2)
			g.drawImage(pic3, x, y-100, null);
		if(dialogue==3)
			g.drawImage(pic5, x, y-100, null);
		if(dialogue==4)
			g.drawImage(pic6, x, y-100, null);
		if(dialogue==5)
			g.drawImage(pic7, x, y-100, null);
		//do more dialogues
	}
	public void visible(){
		visible = true;
	}
	public boolean checkCollision(Player player){
		if(visible){
			//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
			if(player.getX()>x-150 && player.getX()<x+width && player.getY()>y-100 && player.getY()<y+height)
			{
				//System.out.println("Item received");
				//System.out.println("hit");
				dialogue = 0;
				hit = true;
				finished = true;
				return true; // no purpose at the moment
			}
			return false;
		}
		else
			return false;
	}
	public void stopHit()
	{
		hit = false;
		
	}
	public boolean ifHit() // bit misleading
	{
		return finished;
	}
	/*
	public void moveUp()
	{
		y= y - 5;
	}
	
	public void moveDown()
	{
		y= y + 5;
	}
	public  int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void moveLeft()
	{
		x = x -5;
	}
	public void moveRight()
	{
		x = x +5;
	}
	*/

}