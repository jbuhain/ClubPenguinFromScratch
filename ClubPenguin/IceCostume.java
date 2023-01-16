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
public class IceCostume extends Item{
	
	//will have commands that have dialogue pop ups

	public IceCostume(int x, int y, Image pic1, int count){
		super(x,y,pic1,count);
	}
	//sets a trap
	public void drawMe(Graphics g){
		if(!use){
			//System.out.println(x+" " + y);
			g.drawImage(pic1, x, y, null);	
		}
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