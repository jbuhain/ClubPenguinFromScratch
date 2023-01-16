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
public class Npc 
{
	protected int x;
	protected int y;
	
	protected int dialogue;
	//will have commands that have dialogue pop ups
	

	public Npc(int x, int y)
	{
		
		this.x = x;
		this.y = y;
		
		dialogue = 1; 
	}
	public boolean checkCollision(Player player){
		return false;
	}
	public void drawMe(Graphics g)
	{
		
	}
	public void moving(){
		
	}
	public void prone(){
		
	}
	public void visible(){
		
	}
	public void imprison(){
		
	}
	public void free(){
		
	}
	public int getDialogue()
	{
		return dialogue;
	}
	public void setDialogue(int x)
	{
		dialogue = x;
	}
	
	public void nextDialogue()
	{
		dialogue++;
	}
	
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
	public void setCoordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

}