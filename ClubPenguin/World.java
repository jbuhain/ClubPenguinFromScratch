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
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.*;

public class World{
	private Player p1;
	private Image world1_pic;
	private Image world2_pic;
	private Image world3_pic;
	private Image world4_pic;
	private Image world5_pic;
	private Image world6_pic;
	
	private Image crate_pic;
	private Image dragonFeet_pic;
	private Image dragonEgg_pic;
	private Image dragonPotion_pic;
	private Image dragonSuit_pic;
	
	private HashTable<Item> itemStorage;
	private HashTable<Item> characterStorage;
	private Item ice_costume;
	private Item fire_costume;
	private Item dragonFeet_item;
	private Item dragonEgg_item;
	private Item dragonPotion_item;
	private Item dragonSuit_item;
	
	private Item crate1;
	private Item crate2;
	private Item crate3;
	private Item crate4;
	private Item crate5;
	
	private int itemsCollected;
	
	private Image fire_costume_image;
	private Image ice_costume_image;

	
	private Image hud;
	
	private Boss b1; 
	private Boss b2;
	
	private Lever lever;
	
	private Dummy d1;
	private Dummy d2;
	
	private Dummy d3;
	private Dummy d4;
	
	private Npc sensei;
	private Npc elsa;
	private Npc dragon;
	private Npc king;
	
	private Sound soundPlayer;
	
	private boolean dragonPopUp;
	private boolean dragonFlying;
	private boolean secondPart;
	
	private boolean moveRightBoss;
	private boolean moveLeftBoss;
	
	private IceBoss iceBoss;
	
	private int hitCounter;
	
	private boolean toFinalLevel;
	
	public World(Player p1){
		this.p1 = p1;
		
		dragonPopUp = false;
		dragonFlying = false;
		secondPart = false;
		toFinalLevel = false;
		itemsCollected = 0;
		hitCounter = 1;
		itemStorage = new HashTable<Item>();
		characterStorage = new HashTable<Item>();
		try {
			world1_pic = ImageIO.read(new File("mapFiles/world1.png"));
			world2_pic = ImageIO.read(new File("mapFiles/world2.png"));
			world3_pic = ImageIO.read(new File("mapFiles/world3.png"));
			world4_pic = ImageIO.read(new File("mapFiles/world4.png"));
			world5_pic = ImageIO.read(new File("mapFiles/world5.png"));
			world6_pic = ImageIO.read(new File("mapFiles/world6.png"));
			
			fire_costume_image = ImageIO.read(new File("characterFiles/FireNinja/FireCostumeItem.png"));
			ice_costume_image = ImageIO.read(new File("characterFiles/IceNinja/IceCostumeItem.png"));
			
			dragonFeet_pic = ImageIO.read(new File("itemFiles/DragonFeet.png"));
			dragonEgg_pic = ImageIO.read(new File("itemFiles/DragonEgg.png"));
			dragonPotion_pic = ImageIO.read(new File("itemFiles/dragonPotion.png"));
			dragonSuit_pic = ImageIO.read(new File("itemFiles/DragonSuit.png"));
			
			crate_pic = ImageIO.read(new File("itemFiles/Crate.png"));
			
			hud = ImageIO.read(new File("characterFiles/hud.png"));
		} catch (IOException e) {
			System.out.println("Could not load image" + e);
			System.exit(-1);
		}
		
		b1 = new Boss(1300,420); 
		b2 = new Boss(1300,420); 
		iceBoss = new IceBoss(900,460);
		b1.neutralMode();
		b2.neutralMode();
		sensei = new Sensei(750,600);
		elsa = new Elsa(650,390);
		dragon = new Dragon(155,265);
		lever = new Lever(100,600);
		king = new King(990,425);
		
		ice_costume = new IceCostume(600,270,ice_costume_image,1);
		fire_costume = new FireCostume(600,270,fire_costume_image,2);
		
		dragonFeet_item = new Feet(1214,606,dragonFeet_pic,3);
		dragonEgg_item = new Egg(224,122,dragonEgg_pic,4);
		dragonPotion_item = new Potion(560,390,dragonPotion_pic,5);
		dragonSuit_item = new Suit(254,626,dragonSuit_pic,6);
		
		crate1 = new Crate(44,278,crate_pic,11);
		crate2 = new Crate(272,390,crate_pic,11);
		crate3 = new Crate(692,502,crate_pic,11);
		crate4 = new Crate(884,214,crate_pic,11);
		crate5 = new Crate(1214,270,crate_pic,11);
		
		moveRightBoss = false; 
		moveLeftBoss = false;
		
		d1 = new Dummy(310,360,1);
		d2 = new Dummy(840,360,1);
		
		d3 = new Dummy(310,360,2);
		d4 = new Dummy(840,360,2);
		
		
		
		itemStorage.add(ice_costume);
		itemStorage.add(fire_costume);
		
		itemStorage.add(dragonFeet_item);
		itemStorage.add(dragonEgg_item);
		itemStorage.add(dragonPotion_item);
		itemStorage.add(dragonSuit_item);
	}
	public void drawMe(Graphics g){
		
	}
	public Player drawMe(Graphics g, Player p1, ArrayList<Player> playerList){
		//System.out.println("X: " +p1.getX() + "  Y: " + p1.getY());
		if(p1.getLocationRow()==0 && p1.getLocationCol()==0){ // (0,0)
			//System.out.println("X: " +p1.getX() + "  Y: " + p1.getY());
			g.drawImage(world1_pic,0,0,null);
			sensei.drawMe(g);
		}
		if(p1.getLocationRow()==0 && p1.getLocationCol()==1){ // (0,1)
			//System.out.println("X: " +p1.getX() + "  Y: " + p1.getY());
			boolean alternateMovement = false;
			Player otherChar = null;
			for(int i = 0; i<playerList.size();i++){
				if(!playerList.get(i).getUsername().equals(p1.getUsername())){
					if(playerList.get(i).getLocationRow()==p1.getLocationRow() && playerList.get(i).getLocationCol()==p1.getLocationCol()){
						otherChar = playerList.get(i);
						alternateMovement = true;
					}
				}
			}
		
			g.drawImage(world2_pic,0,0,null);
			ice_costume.drawMe(g);
			ice_costume.visible();
			
			d3.drawMe(g);
			d4.drawMe(g);
			d3.visible();
			d4.visible();
			
			if(sensei.getY()!=150){
				sensei.setCoordinates(644,150);
			}
			
			sensei.drawMe(g);
			
			b2.drawMe(g);
			if(!b2.ifHit() && d3.ifHit() && d4.ifHit()){
				if(!alternateMovement){
					if(b2.getY()<=p1.getY()+50 && b2.getY()>=p1.getY()-50){
						//do not move
					}
					else if(p1.getY()>b2.getY()){
						b2.moveDown();
					}else if(p1.getY()<=b2.getY()){
						b2.moveUp();
					}else{
						//do nothing for now
					}
					
					if(b2.getX()<=p1.getX()+50 && b2.getX()>=p1.getX()-50){
						//do not move
					}
					else if(p1.getX()>b2.getX()){
						b2.moveRight();
						b2.faceRight();
					}else if(p1.getX()<=b2.getX()){
						b2.moveLeft();
						b2.faceLeft();
					}else{
						//do nothing for now
					}
				}
				else{ //alternate movement
					if(otherChar.getIDCode()>p1.getIDCode()){
						if(b2.getY()<=p1.getY()+50 && b2.getY()>=p1.getY()-50){
							//do not move
						}
						else if(p1.getY()>b2.getY()){
							b2.moveDown();
						}else if(p1.getY()<=b2.getY()){
							b2.moveUp();
						}else{
							//do nothing for now
						}
						
						if(b2.getX()<=p1.getX()+50 && b2.getX()>=p1.getX()-50){
							//do not move
						}
						else if(p1.getX()>b2.getX()){
							b2.moveRight();
							b2.faceRight();
						}else if(p1.getX()<=b2.getX()){
							b2.moveLeft();
							b2.faceLeft();
						}else{
							//do nothing for now
						}
					}
					else{
						if(b2.getY()<=otherChar.getY()+50 && b2.getY()>=otherChar.getY()-50){
							//do not move
						}
						else if(otherChar.getY()>b2.getY()){
							b2.moveDown();
						}else if(otherChar.getY()<=b2.getY()){
							b2.moveUp();
						}else{
							//do nothing for now
						}
						
						if(b2.getX()<=otherChar.getX()+50 && b2.getX()>=otherChar.getX()-50){
							//do not move
						}
						else if(otherChar.getX()>b2.getX()){
							b2.moveRight();
							b2.faceRight();
						}else if(otherChar.getX()<=b2.getX()){
							b2.moveLeft();
							b2.faceLeft();
						}else{
							//do nothing for now
						}
					}
				}
			}
		}
		if(p1.getLocationRow()==0 && p1.getLocationCol()==2){ // (0,0)
			//System.out.println("X: " +p1.getX() + "  Y: " + p1.getY());
			g.drawImage(world3_pic,0,0,null);
			elsa.drawMe(g);
			lever.drawMe(g);
			if(!secondPart){
				if(elsa.checkCollision(p1)){
					p1.stuck();
					iceBoss.drawMe(g);
					iceBoss.prone();
					
					for(int i = 0; i<playerList.size();i++){
						if(!playerList.get(i).getUsername().equals(p1.getUsername())){
							if(playerList.get(i).getLocationRow()==p1.getLocationRow() && playerList.get(i).getLocationCol()==p1.getLocationCol()){
								secondPart = true;
								moveLeftBoss = true;
								skipToSecondPart();
							}
						}
					}
					
				}
			}
			else{ //if secondPart
			    iceBoss.drawMe(g);
				if(moveLeftBoss){
					iceBoss.moveLeft();
				}
				else if(moveRightBoss){
					iceBoss.moveRight();
				}
				
				if(iceBoss.getX()==200){
					moveRightBoss = true;
					moveLeftBoss = false;
				}
				else if(iceBoss.getX()==900){
					moveLeftBoss = true;
					moveRightBoss = false;
				}
				if(hitCounter == 1 && lever.checkCollision(g,iceBoss)){
					System.out.println("Hit");
					iceBoss.loseHealth();
					hitCounter = 0;
				}
				if(iceBoss.ifHit()){
					elsa.setDialogue(4);
					p1.unStuck();
				}
			}
		}
		if(p1.getLocationRow()==1 && p1.getLocationCol()==0){ // (1,0)
			//System.out.println("X: " +p1.getX() + "  Y: " + p1.getY());
			boolean alternateMovement = false;
			Player otherChar = null;
			
			for(int i = 0; i<playerList.size();i++){
				if(!playerList.get(i).getUsername().equals(p1.getUsername())){
					if(playerList.get(i).getLocationRow()==p1.getLocationRow() && playerList.get(i).getLocationCol()==p1.getLocationCol()){
						otherChar = playerList.get(i);
						alternateMovement = true;
					}
				}
			}
			
			if(sensei.getY()!=150){
				sensei.setCoordinates(644,150);
			}
			
			g.drawImage(world4_pic,0,0,null);
			fire_costume.drawMe(g);
			fire_costume.visible();
			
			d1.drawMe(g);
			d2.drawMe(g);
			d1.visible();
			d2.visible();
		
			sensei.drawMe(g);
			
			b1.drawMe(g);
			// || !b2.ifHit()
			if(!b1.ifHit() && d1.ifHit() && d2.ifHit()){
				
				if(!alternateMovement){
					if(b1.getY()<=p1.getY()+50 && b1.getY()>=p1.getY()-50){
						//do not move
					}
					else if(p1.getY()>b1.getY()){
						b1.moveDown();
					}else if(p1.getY()<=b1.getY()){
						b1.moveUp();
					}else{
						//do nothing for now
					}
					
					if(b1.getX()<=p1.getX()+50 && b1.getX()>=p1.getX()-50){
						//do not move
					}
					else if(p1.getX()>b1.getX()){
						b1.moveRight();
						b1.faceRight();
					}else if(p1.getX()<=b1.getX()){
						b1.moveLeft();
						b1.faceLeft();
					}else{
						//do nothing for now
					}
				}
				else{ //alternate movement
					if(otherChar.getIDCode()>p1.getIDCode()){
						if(b1.getY()<=p1.getY()+50 && b1.getY()>=p1.getY()-50){
							//do not move
						}
						else if(p1.getY()>b1.getY()){
							b1.moveDown();
						}else if(p1.getY()<=b1.getY()){
							b1.moveUp();
						}else{
							//do nothing for now
						}
						
						if(b1.getX()<=p1.getX()+50 && b1.getX()>=p1.getX()-50){
							//do not move
						}
						else if(p1.getX()>b1.getX()){
							b1.moveRight();
							b1.faceRight();
						}else if(p1.getX()<=b1.getX()){
							b1.moveLeft();
							b1.faceLeft();
						}else{
							//do nothing for now
						}
					}
					else{
						if(b1.getY()<=otherChar.getY()+50 && b1.getY()>=otherChar.getY()-50){
							//do not move
						}
						else if(otherChar.getY()>b1.getY()){
							b1.moveDown();
						}else if(otherChar.getY()<=b1.getY()){
							b1.moveUp();
						}else{
							//do nothing for now
						}
						
						if(b1.getX()<=otherChar.getX()+50 && b1.getX()>=otherChar.getX()-50){
							//do not move
						}
						else if(otherChar.getX()>b1.getX()){
							b1.moveRight();
							b1.faceRight();
						}else if(otherChar.getX()<=b1.getX()){
							b1.moveLeft();
							b1.faceLeft();
						}else{
							//do nothing for now
						}
					}
				}
			}
			
		}
		if(p1.getLocationRow()==1 && p1.getLocationCol()==2){ // (1,2)
			g.drawImage(world6_pic,0,0,null);
			king.drawMe(g);
			elsa.setCoordinates(715,410);
			elsa.drawMe(g);
			elsa.free();
			elsa.setDialogue(100);
			
			return p1;
			///////////////////////////////////////////////////
		}
		if(p1.getLocationRow()==1 && p1.getLocationCol()==1){ // (1,1)
			g.drawImage(world5_pic,0,0,null);
			sensei.drawMe(g);
			sensei.setCoordinates(519,110);
			dragonFeet_item.drawMe(g);
			dragonEgg_item.drawMe(g);
			dragonPotion_item.drawMe(g);
			dragonSuit_item.drawMe(g);
			
			crate1.drawMe(g);
			crate2.drawMe(g);
			crate3.drawMe(g);
			crate4.drawMe(g);
			crate5.drawMe(g);
			if(dragonPopUp){
				dragon.drawMe(g);
				dragon.visible();
			}
			if(dragonFlying){
				p1.setLocation(dragon.getX()+35,dragon.getY());
				return p1;
			}
		}
		
		if(!(p1.getLocationRow()==1 && p1.getLocationCol()==2)){
			g.drawImage(hud,0,700,null);
		}
		
		
		
		/////////////////////////////////////////
		DLList<Item>[] temp1 = characterStorage.getTable();
		for(int i = 0; i< temp1.length; i++){
			for(int z = 0; z<temp1[i].size(); z++){
				if(temp1[i].get(z) instanceof IceCostume){
					g.drawImage(ice_costume_image, 220, 730, null);
				}
				else if(temp1[i].get(z) instanceof FireCostume){
					g.drawImage(fire_costume_image, 220+45, 730, null);
				}
				else if(temp1[i].get(z) instanceof Egg){
					g.drawImage(dragonEgg_pic, 860, 725, null);
				}
				else if(temp1[i].get(z) instanceof Feet){
					g.drawImage(dragonFeet_pic, 850+45, 750, null);
				}				
				else if(temp1[i].get(z) instanceof Potion){
					g.drawImage(dragonPotion_pic, 850+45+45, 730, null);
				}
				else if(temp1[i].get(z) instanceof Suit){
					g.drawImage(dragonSuit_pic, 850+45+45+50, 727, null);
				}					
			}
		}
		DLList<Item>[] temp2 = itemStorage.getTable();
		for(int i = 0; i< temp2.length; i++){
			for(int z = 0; z<temp2[i].size(); z++){
				temp2[i].get(z).checkCollision(p1);
				if(temp2[i].get(z).checkUse()){
					if(!p1.ifIce() && temp2[i].get(z) instanceof IceCostume){
						//System.out.println("eyo");
						System.out.println(p1.ifIce());
						p1.iceMode();
						
						d3.setDialogue(3);
						d4.setDialogue(4);
						
						soundPlayer.playSound("soundFiles/itemPicked.mp3");
						return p1;
					}
					else if(!p1.ifFire() && temp2[i].get(z) instanceof FireCostume){
						//System.out.println("eyo");
						p1.fireMode();
						System.out.println(p1.ifFire());
						
						d1.setDialogue(1);
						d2.setDialogue(2);
						
						soundPlayer.playSound("soundFiles/itemPicked.mp3");
						return p1;
					}
					else if(temp2[i].get(z) instanceof Feet){
						//System.out.println("eyo");
						itemsCollected++;
						soundPlayer.playSound("soundFiles/itemPicked.mp3");
					}
					else if(temp2[i].get(z) instanceof Egg){
						//System.out.println("eyo");
						itemsCollected++;
						soundPlayer.playSound("soundFiles/itemPicked.mp3");
					}
					else if(temp2[i].get(z) instanceof Potion){
						//System.out.println("eyo");
						itemsCollected++;
						soundPlayer.playSound("soundFiles/itemPicked.mp3");
					}
					else if(temp2[i].get(z) instanceof Suit){
						//System.out.println("eyo");
						itemsCollected++;
						soundPlayer.playSound("soundFiles/itemPicked.mp3");
					}
						
					/////////////
					characterStorage.add(temp2[i].get(z));
					itemStorage.remove(temp2[i].get(z));
				}
			}
		}
		
		return null;
	}
	public void startCutscene(){
		
	}
	public Player attackMode(Player p1){
		if(p1.getLocationRow()==0 && p1.getLocationCol()==1){
			//System.out.println("test");
			if(b2.attackMode(p1)){
				p1.loseHealth();
				soundPlayer.playSound("soundFiles/playerHit.mp3");
				return p1;
			}
		}
		else if(p1.getLocationRow()==1 && p1.getLocationCol()==0){
			//System.out.println("test");
			if(b1.attackMode(p1)){
				p1.loseHealth();
				soundPlayer.playSound("soundFiles/playerHit.mp3");
				return p1;
			}
		}
		return null;
	}
	public void stopHitForAll(){
		d1.stopHit();
		d2.stopHit();//do for all of em
		d3.stopHit();
		d4.stopHit();
	}
	public void playerAttack(Player p1){
		if(p1.getLocationRow()==1 && p1.getLocationCol()==0){
			d1.checkCollision(p1);
			d2.checkCollision(p1);
			if(b1.checkCollision(p1)){
				System.out.println("enemy hit");
			}
			else{
				System.out.println("enemy not hit");
			}
			
		}else if(p1.getLocationRow()==0 && p1.getLocationCol()==1){
			d3.checkCollision(p1);
			d4.checkCollision(p1);
			if(b2.checkCollision(p1)){
				System.out.println("enemy hit");
			}
			else{
				System.out.println("enemy not hit");
			}
		}
		else if(p1.getLocationRow()==1 && p1.getLocationCol()==1){
			//System.out.println("item hit"); 
			dragonFeet_item.openCrate(p1);
			dragonEgg_item.openCrate(p1);
			dragonPotion_item.openCrate(p1);
			dragonSuit_item.openCrate(p1);
			soundPlayer.playSound("soundFiles/playerHit.mp3");
			crate1.openCrate(p1);
			crate2.openCrate(p1);
			crate3.openCrate(p1);
			crate4.openCrate(p1);
			crate5.openCrate(p1);
		}
	}
	public void hit(){
		lever.hit();
		hitCounter = 1;
		soundPlayer.playSound("soundFiles/playerHit.mp3");
	}
	public boolean worldClear(Player p1){
		int getLocationRowTemp = p1.getLocationRow();
		int getLocationColTemp = p1.getLocationCol();
		if(getLocationRowTemp == 0 && getLocationColTemp == 0){
			if(sensei.getDialogue()==2){
				return true;
			}
		}
		else if(getLocationRowTemp == 0 && getLocationColTemp == 1){
			return b2.ifDead();
		}
		else if(getLocationRowTemp == 1 && getLocationColTemp == 0){
			return b1.ifDead();
		}
		else if(getLocationRowTemp == 0 && getLocationColTemp == 2){
			return iceBoss.ifHit();
		}
		return false;
	}
	public boolean nextDialogue(int getRow, int getCol){
		if(getRow == 0 && getCol == 0){
			if(sensei.getDialogue()==1){
				if(sensei.getY()>=482 && sensei.getX()==750){
					sensei.moveUp();
					return false;
				}
				else if(sensei.getX()>=330 && sensei.getY()<=482){
					sensei.moveLeft();
					return false;
				}
				else if(sensei.getX()>=250){
					
					sensei.moveLeft();
					sensei.moveLeft();
					
					sensei.moveDown();
					return false;
				}
				else{
					sensei.nextDialogue();
					return true;
				}
			}
		}
		else if(getRow == 0 && getCol == 1){
			if(sensei.getDialogue()==3 && b2.ifHit()){
				sensei.setDialogue(4);
				return true;
			}
			else if(sensei.getDialogue()==2){
				sensei.setDialogue(3);
				return true;
			}
		}
		else if(getRow == 1 && getCol == 0){
			if(sensei.getDialogue()==3 && b1.ifHit()){
				sensei.setDialogue(4);
				return true;
			}
			else if(sensei.getDialogue()==2){
				sensei.setDialogue(3);
				return true;
			}
		}
		else if(getRow == 1 && getCol == 1){
			if(itemsCollected<4){
				sensei.setDialogue(5);
			}
			else if(itemsCollected>=4 && !dragonPopUp){
				dragonPopUp = true;
				sensei.setCoordinates(1600,200);
			}
			else if(dragon.getDialogue()==2){
				dragonFlying = true;//1400 x
				if(dragon.getX()<=900){
					dragon.moveRight();
					dragon.moving();
					return false;
				}
				else if(dragon.getX()<=1400){
					if(dragon.getY()>=0){
						dragon.moveUp();
					}
					dragon.moveRight();
					dragon.moving();
					return false;
				}
				dragon.prone();
				//dragon.setDialogue(3);  //then make dragon fly to the player2's world
			}
			else if(dragon.getDialogue()==1){
				dragon.setCoordinates(154,265);
				dragon.setDialogue(2);
			} 
		}
		return true;
	}
	public void skipToSecondPart(){
		secondPart = true;
		elsa.imprison();
		elsa.setDialogue(3);
	}
	public void restartDragonInteraction(){
		dragon = new Dragon(155,265);
	}
	public void stopAttacking(){
		b1.neutralMode();
		b2.neutralMode();
	}
}