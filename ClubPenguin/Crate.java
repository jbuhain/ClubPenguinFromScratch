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
public class Crate extends Item{
	
	//will have commands that have dialogue pop ups
	
	public Crate(int x, int y, Image pic1, int count){
		super(x,y,pic1,count);
	}
	//sets a trap
	@Override
	public void drawMe(Graphics g){
		if(boxMode){
			g.drawImage(pic1, x, y, null);	
		}
	}
	@Override
	public boolean checkCollision(Player player){
		return false;
	}
	@Override
	public boolean openCrate(Player player){
		if(!use)
		{
			//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
			if(player.getX()>x-100 && player.getX()<x+200 && player.getY()>y-100 && player.getY()<y+height)
			{
				//System.out.println("Item received");
				//System.out.println("Boss hit");
				boxMode = false;
				use = true;
				//soon he'll have lives
				return true; // no purpose at the moment
			}
			return false;
		}
		else
			return false;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	//stats
	public void inUse(){
		use = true;
	}
	

}