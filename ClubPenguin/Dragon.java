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
public class Dragon extends Npc
{
	protected Image pic1;
	protected Image pic2;
	protected Image pic3;
	protected Image pic4;
	protected Image dialogue1;
	protected Image dialogue2;
	protected Image dialogue3;
	protected Image dialogue4;
	protected boolean visible;
	protected boolean moving;
	
	private boolean walkLeft;
//maybe run in screen instead?
	public Dragon(int x, int y){
		
		super(x,y);
		visible = false;
		walkLeft = false;
		moving = false;
		try {
			pic1 = ImageIO.read(new File("characterFiles/Dragon/DragonWalkLeft.png"));
			pic2 = ImageIO.read(new File("characterFiles/Dragon/DragonWalkRight.png"));
			pic3 = new ImageIcon("characterFiles/Dragon/Thunder.gif").getImage();
			pic3 = new ImageIcon("characterFiles/Dragon/smallExplosion.gif").getImage();
			dialogue1 = ImageIO.read(new File("characterFiles/Dragon/DragonDialogue1.png"));
			dialogue2 = ImageIO.read(new File("characterFiles/Dragon/DragonDialogue2.png"));
		} catch (IOException e) {
			System.out.println("Could not load images. ");
			System.exit(-1);
		}
		dialogue = 1;
	}
	

	public void drawMe(Graphics g){
		//g.setColor(orange);
		//g.fillRect(x,y,width,height);
		//g.setColor(gray);
		//g.fillRect(x,y+15,75,20);
		if(walkLeft){
			g.drawImage(pic1, x, y, null);
		}
		else{
			g.drawImage(pic2, x, y, null);
		}
		if(visible && x==155){
			g.drawImage(pic3, x-100, y-100, null);
		}
		
		if(dialogue==1){
			System.out.println("dialogue");
			g.drawImage(dialogue1,x+30,y-130,null);
		}
		else if(dialogue==2){
			g.drawImage(dialogue2,x+30,y-130,null);
		}
		
		if(moving){
			g.drawImage(pic4,x,y-130,null);
		}
	
		//do more dialogues
		
		
	}
	public void moving(){
		moving = true;
	}
	public void prone(){
		moving = false;
	}
	public void visible(){
		visible = true;
	}
	public void nextDialogue(){
		dialogue++;
	}
	public int getDialogue(){
		return dialogue;
	}
	
	public void moveUp(){
		y= y - 5;
	}
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void moveDown(){
		y= y + 5;
	}
	public  int getX(){
		int tempX = x;
		return tempX;
	}
	public int getY(){
		int tempY = y;
		return tempY;
	}
	public void moveLeft(){
		walkLeft = true;
		x = x -3;
	}
	public void moveRight(){
		walkLeft = false;
		x = x +5;
	}
	

}