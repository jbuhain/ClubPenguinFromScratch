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
public class Elsa extends Npc
{
	protected Image pic1;
	protected Image pic2;
	protected Image dialogue1;
	protected Image dialogue2;
	protected Image dialogue3;
	protected Image dialogue4;
	
	private boolean imprisoned;
	
//maybe run in screen instead?
	public Elsa(int x, int y){
		
		super(x,y);
		
		dialogue = 1; 
		try {
			pic1 = ImageIO.read(new File("npcFiles/Elsa.png"));
			pic2 = ImageIO.read(new File("npcFiles/Cage.png"));
			
			dialogue1 = ImageIO.read(new File("npcFiles/ElsaDialogue1.png"));
			dialogue2 = ImageIO.read(new File("npcFiles/ElsaDialogue2.png"));
			
			dialogue3 = ImageIO.read(new File("npcFiles/ElsaDialogue3.png"));
			
			dialogue4 = ImageIO.read(new File("npcFiles/ElsaDialogue4.png"));
			
		} catch (IOException e) {
			System.out.println("Could not load images. ");
			System.exit(-1);
		}
		imprisoned = false;
	}
	
	public void imprison(){
		imprisoned = true;
	}
	public void free(){
		imprisoned = false;
	}
	public void drawMe(Graphics g){
		//g.setColor(orange);
		//g.fillRect(x,y,width,height);
		//g.setColor(gray);
		//g.fillRect(x,y+15,75,20);
		g.drawImage(pic1, x, y, null);
		if(imprisoned){
			g.drawImage(pic2, x-100, y-100, null);
		}
		
		if(dialogue==1){
			g.drawImage(dialogue1,x+30,y-130,null);
		}
		else if(dialogue==2){
			g.drawImage(dialogue2,x+30,y-130,null);
		}
		else if(dialogue==3){
			g.drawImage(dialogue3,x+30,y-130,null);
		}
		
		else if(dialogue==4){
			g.drawImage(dialogue4,x+30,y-130,null);
		}
		//do more dialogues
		
		
	}
	public boolean checkCollision(Player player){
		
		System.out.println("testing");
		//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
		if(player.getX()>x-300 && player.getX()<x+100 && player.getY()>y-200 && player.getY()<y+100){
			//System.out.println("Item received");
			//System.out.println("Boss hit");
			dialogue = 2;
			imprisoned = true;
			//soon he'll have lives
			return true; // no purpose at the moment
		}
		return false;
	}
	public void nextDialogue()
	{
		dialogue++;
	}
	public int getDialogue()
	{
		return dialogue;
	}
	
	public void moveUp()
	{
		y= y - 5;
	}
	public void setCoordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void moveDown()
	{
		y= y + 5;
	}
	public  int getX()
	{
		int tempX = x;
		return tempX;
	}
	public int getY()
	{
		int tempY = y;
		return tempY;
	}
	public void moveLeft()
	{
		x = x -3;
	}
	public void moveRight()
	{
		x = x +5;
	}
	

}