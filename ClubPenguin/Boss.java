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
public class Boss
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
	/*
	private Image healthBar100;
	private Image healthBar75;
	private Image healthBar50;
	private Image healthBar25;
	*/
	private int width;
	private int height;
	private int dialogue;
	
	private int healthBar;
	
	private boolean hit;
	private boolean visible; 
	private boolean finished;
	
	private boolean attack;
	private boolean faceRight;
	
	private Image healthBar100;
	private Image healthBar75;
	private Image healthBar50;
	private Image healthBar25;

	public Boss(int x, int y)
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
	
			
		pic1 = new ImageIcon("enemyFiles/BossProne.gif").getImage();
		
		pic2 = new ImageIcon("enemyFiles/BossAttack.gif").getImage();
		pic3 = new ImageIcon("enemyFiles/SmallExplosion.gif").getImage();
		pic4 = new ImageIcon("enemyFiles/BossDeath.gif").getImage();
		
		pic1Mirror = new ImageIcon("enemyFiles/BossProneMirror.gif").getImage();
		pic2Mirror = new ImageIcon("enemyFiles/BossAttackMirror.gif").getImage();
		pic4Mirror = new ImageIcon("enemyFiles/BossDeathMirror.gif").getImage();
		
		try {
			healthBar100 = ImageIO.read(new File("enemyFiles/HealthBar100%.png"));
			healthBar75 = ImageIO.read(new File("enemyFiles/HealthBar75%.png"));
			healthBar50 = ImageIO.read(new File("enemyFiles/HealthBar50%.png"));
			healthBar25 = ImageIO.read(new File("enemyFiles/HealthBar25%.png"));
		} catch (IOException e) {
			System.out.println("Could not load images. ");
			System.exit(-1);
		}
		
		
		
		faceRight = false;
		
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
	public void followMode(Player player){
		if(!ifHit()){
			neutralMode();
			//b2.neutralMode();
			
			if(this.getY()<=player.getY()+50 && this.getY()>=player.getY()-50){
				//do not move
			}
			else if(player.getY()>this.getY()){
				this.moveDown();
			}else if(player.getY()<=this.getY()){
				this.moveUp();
			}else{
				//do nothing for now
			}
			
			if(this.getX()<=player.getX()+50 && this.getX()>=player.getX()-50){
				//do not move
			}
			else if(player.getX()>this.getX()){
				this.moveRight();
				this.faceRight();
			}else if(player.getX()<=this.getX()){
				this.moveLeft();
				this.faceLeft();
			}else{
				//do nothing for now
			}
			/* PLAYER TWO
			if(b2.getY()<=player2.getY()+50 && b2.getY()>=player2.getY()-50){
				//do not move
			}
			else if(player2.getY()>b2.getY()){
				b2.moveDown();
			}else if(player2.getY()<=b2.getY()){
				b2.moveUp();
			}else{
				//do nothing for now
			}
			
			if(b2.getX()<=player2.getX()+50 && b2.getX()>=player2.getX()-50){
				//do not move
			}
			else if(player2.getX()>b2.getX()){
				b2.moveRight();
				b2.faceRight();
			}else if(player2.getX()<=b2.getX()){
				b2.moveLeft();
				b2.faceLeft();
			}else{
				//do nothing for now
			}
			*/
		}
	}
	public boolean attackMode(Player player){
		attack = true;
		

		pic2 = new ImageIcon("enemyFiles/BossAttack.gif").getImage();
	
		
		
		if(!finished)
		{
			//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
			if(player.getX()>x-100 && player.getX()<x+width && player.getY()>y-200 && player.getY()<y+height)
			{
				//System.out.println("Item received");
				//System.out.println("Player hit");
				hit = true;
				//soon he'll have lives
				return true; // no purpose at the moment
			}
			return false;
		}
		else
			return false;
		
	}
	public void neutralMode(){
		attack = false;
		pic2 = new ImageIcon("enemyFiles/BossAttack.gif").getImage();
		
	}
	public boolean ifDead(){
		return finished;
	}
	public void drawMe(Graphics g){
		if(!faceRight){
			if(!finished && !(attack))//if not dead
				g.drawImage(pic1, x, y, null);
			else if(!finished && attack)
				g.drawImage(pic2, x-100, y-100, null);
			else//if finished
				g.drawImage(pic4,x,y,null);
		}else{
			if(!finished && !(attack))//if not dead
				g.drawImage(pic1Mirror, x, y, null);
			else if(!finished && attack)
				g.drawImage(pic2Mirror, x-100, y-100, null);
			else//if finished
				g.drawImage(pic4Mirror,x,y,null);
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
		}
		
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
		
		System.out.println("testing");
		//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
		if(player.getX()>x-100 && player.getX()<x+width && player.getY()>y-200 && player.getY()<y+height)
		{
			//System.out.println("Item received");
			//System.out.println("Boss hit");
			healthBar--;
			if(healthBar==0){
				finished = true;
			}
			hit = true;
			//soon he'll have lives
			return true; // no purpose at the moment
		}
		return false;
	}
	public void stopHit(){
		hit = false;
	}
	public boolean ifHit(){
		return finished;
	}
	
	public void moveUp(){
		if(!finished && !attack)
			y= y - 2;
	}
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void moveDown(){
		if(!finished && !attack)
			y= y + 2;
	}
	public  int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void moveLeft(){
		if(!finished && !attack)
			x = x -2;
	}
	public void moveRight(){
		if(!finished && !attack)
			x = x + 2;
	}
	

}