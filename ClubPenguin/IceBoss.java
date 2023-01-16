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
public class IceBoss
{
	private int x;
	private int y;
	
	
	//will have commands that have dialogue pop ups
	private Image pic1;
	private Image pic2;
	private Image pic3; //explosion
	private Image pic4;
	
	private Image pic1Mirror;
	private Image pic2Mirror;
	private Image pic4Mirror;
	
	private Image healthBar100;
	private Image healthBar75;
	private Image healthBar50;
	private Image healthBar25;
	
	private int width;
	private int height;
	private int dialogue;
	
	private int healthBar;
	
	private boolean hit;
	private boolean visible; 
	private boolean finished;
	
	private boolean attack;
	private boolean faceRight;
	private boolean faceLeft;
	private boolean prone;

	public IceBoss(int x, int y)
	{
		
		this.x = x;
		this.y = y;
		
		dialogue = 0;
		
		this.width = 313;
		this.height = 300;
		
		Font font1 = new Font("Times New Roman", Font.BOLD, 15); 
		Font font2 = new Font("Times New Roman", Font.BOLD, 35);
		Font font3 = new Font("Times New Roman", Font.BOLD, 50); 
		
		healthBar = 4;
		attack = false;

		faceRight = false;
		prone = true;
		try {
			pic1 = ImageIO.read(new File("enemyFiles/IceBossProne.png"));
			pic2 = ImageIO.read(new File("enemyFiles/IceBossMoveLeft.png"));
			pic3 = ImageIO.read(new File("enemyFiles/IceBossMoveRight.png"));
			healthBar100 = ImageIO.read(new File("enemyFiles/HealthBar100%.png"));
			healthBar75 = ImageIO.read(new File("enemyFiles/HealthBar75%.png"));
			healthBar50 = ImageIO.read(new File("enemyFiles/HealthBar50%.png"));
			healthBar25 = ImageIO.read(new File("enemyFiles/HealthBar25%.png"));
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
	public void faceRight(){
		faceRight = true;
	}
	public void faceLeft(){
		faceRight = false;
	}
	public boolean ifFacingRight(){
		return faceRight;
	}
	public void destroy(){
		healthBar = 0;
		finished = true;
	}
	public void prone(){
		faceLeft = false;
		faceRight = false;
		prone = true;
	}
	public void drawMe(Graphics g){
		if(!finished){
			if(faceLeft){
				g.drawImage(pic2,x,y,null);
			}
			else if(faceRight){
				g.drawImage(pic3,x,y,null);
			}
			else if(prone){
				g.drawImage(pic1,x,y,null);
			}
			
			if(healthBar == 4)
				g.drawImage(healthBar100, x-20, y-40, null);
			else if(healthBar == 3)
				g.drawImage(healthBar75, x-20, y-40, null);
			else if(healthBar == 2)
				g.drawImage(healthBar50, x-20, y-40, null);
			else if(healthBar == 1)
				g.drawImage(healthBar25, x-20, y-40, null);
			if(healthBar<=0){
				finished = true;
				visible = false;
			}
		}
		
		//do more dialogues
	}
	public void visible(){
		visible = true;
	}
	public void loseHealth(){
		healthBar--;
	}
	public void heal(){
		healthBar = 4;
		finished = false;
	}
	public boolean checkCollision(Player player){
		if(visible)
		{
			//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
			if(player.getX()>x-100 && player.getX()<x+width && player.getY()>y-200 && player.getY()<y+height)
			{
				//System.out.println("Item received");
				//System.out.println("Boss hit");
				healthBar--;
				hit = true;
				//soon he'll have lives
				return true; // no purpose at the moment
			}
			return false;
		}
		else
			return false;
	}
	public boolean checkCollision(int playerX, int playerY){
		if(visible)
		{
			//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
			if(playerX>x-100 && playerX<x+width)
			{
				//System.out.println("Item received");
				//System.out.println("Boss hit");
				//soon he'll have lives
				return true; // no purpose at the moment
			}
			return false;
		}
		else
			return false;
	}
	public void stopHit(){
		hit = false;
	}
	public boolean ifHit(){
		return finished;
	}
	
	public void moveUp(){
		if(!finished){
			y= y - 2;
		}
	}
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void moveDown(){
		if(!finished){
			y= y + 2;
		}
			
	}
	public  int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void moveLeft(){
		if(!finished){
			faceLeft = true;
			faceRight = false;
			x = x -2;
		}
			
	}
	public void moveRight(){
		if(!finished){
			faceRight = true;
			faceLeft = false;
			x = x + 2;
		}
			
	}
	

}