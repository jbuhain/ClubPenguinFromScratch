import java.io.Serializable;
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
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player implements Serializable{
	private int x; 
	private int y;
	private String username;
	private int idCode;
	private int health;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean fireMoveLeft;
	private boolean fireMoveRight;
	private boolean invisible;
	private boolean fireClothes;
	private boolean iceClothes;
	private boolean moving;
	private int world_location_ROW;
	private int world_location_COL;
	private Font usernameFont;
	private int colorNum;
	private boolean stuck;
	//private int locationNum; //secondary
	/*
	Location Num Key
	0/1/2 
	3/4/5
	
	*/
	public Player(int x, int y, String username){ //(int x, int y, String username, int colorNum)
		this.x = x;
		this.y = y;
		this.username = username;
		health = 5;
		idCode = -1;
		world_location_ROW = 0;
		world_location_COL = 0;
		movingLeft = false;
		movingRight = false;
		fireMoveLeft = false;
		fireMoveRight = false;
		invisible = false;
		fireClothes = false;
		iceClothes = false;
		moving = false;
		stuck = false;
		usernameFont = new Font("Arial", Font.PLAIN, 12); 
		//this.colorNum = colorNum;
	}
	public int getColorNum(){
		return colorNum;
	}
	public void drawMe(Graphics g, Image pic4, Image pic5, BufferedImage pic6, BufferedImage pic7, BufferedImage pic8,Image moveGif, BufferedImage pronePic, BufferedImage moveLeft, BufferedImage moveRight, BufferedImage iceBlock){
		//g.setColor(orange);
		//g.fillRect(x,y,width,height);
		//g.setColor(gray);
		//g.fillRect(x,y+15,75,20);
		if(fireClothes || invisible){
			if(invisible){
				
			}
			else if(moving && movingRight){//Moving Right
				g.drawImage(pic8, x, y, null);
			}	
			else if(moving && movingLeft){//Moving Left
				g.drawImage(pic7, x, y, null);
			}
			else if(fireMoveRight){//Attack Right
				g.drawImage(pic4, x, y-50, null);
				//System.out.println("WAAT");
			}
			else if(fireMoveLeft){//Attack Left
				g.drawImage(pic5, x-50, y-50, null);
			}
			else if(fireClothes){
				g.drawImage(pic6,x,y,null); //Prone
			}
			else {
				System.out.println("error");
			}
		}
		else if(iceClothes || invisible){
			if(invisible){
				
			}
			else if(moving && movingRight){//Moving Right
				g.drawImage(pic8, x-50, y-20, null);
			}	
			else if(moving && movingLeft){//Moving Left
				g.drawImage(pic7, x, y-20, null);
			}
			else if(fireMoveRight){//Attack Right
				g.drawImage(pic4, x, y, null);
				//System.out.println("WAAT");
			}
			else if(fireMoveLeft){//Attack Left
				g.drawImage(pic5, x-50, y, null);
			}
			else if(iceClothes){
				g.drawImage(pic6,x,y,null); //Prone
			}
			else {
				System.out.println("error");
			}
		}
		else{
			if(moving){
				g.drawImage(moveGif,x,y,null);
			}
			else if(moving && movingRight){
				g.drawImage(moveRight,x,y,null);
			}
			else if(moving && movingLeft){
				g.drawImage(moveLeft,x,y,null);
			}
			else{
				g.drawImage(pronePic,x,y,null);
			}
		}
		
		if(stuck){
			g.drawImage(iceBlock,x-50,y-75,null);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(usernameFont);
		g.drawString(username,x+20,y+95);
	}
	public int getLocationRow(){
		return world_location_ROW;
	}
	public int getLocationCol(){
		return world_location_COL;
	}
	public void setWorldLocation(int row, int col){
		world_location_ROW = row;
		world_location_COL = col;
	}
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	public boolean isAlive(){
		if(health==0){
			return true;
		}
		return false;
	}
	public void loseHealth(){
		setLocation(600,270);
	}
	public boolean isProne(){
		if(fireMoveLeft||fireMoveRight||moving){
			return false;
		}
		return true;
	}
	public String toString(){
		return "" + idCode;
	}
	public int getIDCode(){
		return idCode;
	}
	public void unStuck(){
		stuck = false;
	}
	public void stuck(){
		stuck = true;
	}
	public void setIDCode(int id){
		this.idCode = id;
	}
	public String getUsername(){
		return username;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void moveRight(){
		if(!stuck){
			x+=6;
			movingRight = true;
			movingLeft = false;
			moving = true;
		}
	}
	public void moveLeft(){
		if(!stuck){
			x-=6;
			movingLeft = true;
			movingRight = false;
			moving = true;
		}
	}
	public void moveUp(){
		if(!stuck){
			y-=4;
			moving = true;
		}
	}
	public void moveDown(){
		if(!stuck){
			y+=4;
			moving = true;
		}
	}

	public void prone(){
		movingLeft = false;
		movingRight = false;
		fireMoveLeft = false;
		fireMoveRight = false;
		moving = false;
	}
	public void startFireMoveRight(){
		fireMoveRight = true;
	}
	public void startFireMoveLeft(){
		fireMoveLeft = true;
	}
	public void fireMode(){
		fireClothes = true;
	}
	public void iceMode(){
		iceClothes = true;
	}
	public boolean ifFire(){
		return fireClothes;
	}
	public boolean ifIce(){
		return iceClothes;
	}
	/*
	public void moveDiagonalForwardRight(){
		x+=4;
		y-=3;
	}
	public void moveDiagonalForwardLeft(){
		x-=4;
		y-=3;
	}
	public void moveDiagonalBackwardRight(){
		x+=4;
		y+=3;
	}
	public void moveDiagonalBackwardLeft(){
		x-=4;
		y+=3;
	}
	*/
}