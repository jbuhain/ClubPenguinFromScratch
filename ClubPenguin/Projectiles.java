import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Projectiles implements Runnable{
	private int x; 
	private int y;
	private int type;
	private boolean visible;
	private Boss p1;
	private Image shards;
	public Projectiles(int x, int y){ 
		this.x = x;
		this.y = y;
		this.type = type;
		visible = true;
		
		try{
			shards = ImageIO.read(new File("itemFiles/Shard.png"));
		}catch(IOException e){
			
		}
	}
	public void drawMe(Graphics g){
		if(visible){
			g.drawImage(shards,x,y,null);
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void run(){
		//System.out.println("test");
		while(true){
			try {
				Thread.sleep(1);
			} catch(InterruptedException ex) {
				return;
			}
			
			if(visible){
				y++;
				
				if(y==-50 || y==1000){ //check for out of bounce
					visible = false;
					return;
				}
			}
			else{
				return;
			}
		}
	}
	public void disappear(){
		visible = false;
	}
	public boolean visible(){
		return visible;
	}
	//add checkcollision method later!!!
}
