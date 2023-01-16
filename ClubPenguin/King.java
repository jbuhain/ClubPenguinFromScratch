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
public class King extends Npc
{
	protected Image pic1;
	protected Image pic2;
	protected Image pic3;
	protected Image dialogue1;

	protected boolean trap;
	

	public King(int x, int y)
	{
		
		super(x,y);
		dialogue = 1; 
		try {
			pic1 = ImageIO.read(new File("npcFiles/KingFinal.png"));
			pic2 = ImageIO.read(new File("npcFiles/squire.png"));
			dialogue1 = ImageIO.read(new File("npcFiles/KingDialogue1.png"));
		} catch (IOException e) {
			System.out.println("Could not load images. ");
			System.exit(-1);
		}
		trap = true;
	}
	

	public void drawMe(Graphics g)
	{
		g.drawImage(pic1, x, y, null);
		g.drawImage(pic2, x-100, y-100, null);
		g.drawImage(pic2, x+100, y-100, null);
		g.drawImage(pic2, x-100, y, null);
		g.drawImage(pic2, x+150, y, null);
		
		if(dialogue==1)
		{
			g.drawImage(dialogue1,x+30,y-130,null);
		}
	
		//do more dialogues
	}
	public void nextDialogue()
	{
		dialogue++;
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