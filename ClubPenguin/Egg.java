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
public class Egg extends Item{
	
	//will have commands that have dialogue pop ups
	private boolean boxMode;
	private Image box;
	public Egg(int x, int y, Image pic1, int count){
		super(x,y,pic1,count);
		boxMode = true;
		try {
			box = ImageIO.read(new File("itemFiles/Crate.png"));
		} catch (IOException e) {
			System.out.println("Could not load images. ");
			System.exit(-1);
		}
	}
	//sets a trap
	@Override
	public void drawMe(Graphics g){
		if(boxMode){
			g.drawImage(box, x, y, null);	
		}
		else if(!use){
			//System.out.println(x+" " + y);
			g.drawImage(pic1, x+10, y, null);	
		}
	}
	public boolean openCrate(Player player){
		if(!use)
		{
			//System.out.println("player x: " + player.getX() +  "has to be between " + (x-200) + "and " + (x+width));
			if(player.getX()>x-100 && player.getX()<x+200 && player.getY()>y-100 && player.getY()<y+height)
			{
				//System.out.println("Item received");
				//System.out.println("Boss hit");
				visible = true;
				boxMode = false;
				use = false;
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