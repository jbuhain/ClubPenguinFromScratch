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


import java.util.ArrayList;
import java.util.ListIterator;

//forimage
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.Font;
import java.awt.BorderLayout;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Iterator;
import java.util.*;
import java.net.*;

import javax.swing.JFrame;

import javax.swing.ImageIcon;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.net.*;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ClientScreen extends JPanel implements ActionListener, Runnable{ 
	private JTextField msgTextfield;
	private JTextArea chatView;
	private JScrollPane chatViewPaneSet;
	private boolean firstLogIn;
	
	private String msg;
	
	private String hostName;
	private int portNumber;
	private Socket serverSocket;
	private BufferedReader in;
	private PrintWriter out;
	
	private PushbackInputStream inObj;		
	private ObjectInputStream inObjREAL;	
	private ObjectOutputStream outObj;	
	
	private Input input;
	private ArrayList<Player> playerList;
	private String username;
	private int playerCode;
	

	
	/*
	Player Stuff
	*/
	
	private Image pic1_fire;
	private Image pic2_fire;
	private BufferedImage pic3_fire;
	private BufferedImage pic4_fire;
	private BufferedImage pic5_fire;
	
	private Image pic1_ice;
	private Image pic2_ice;
	private BufferedImage pic3_ice;
	private BufferedImage pic4_ice;
	private BufferedImage pic5_ice;
	
	private Image pic1_reg;
	private BufferedImage pic2_reg;
	private BufferedImage pic3_reg;
	private BufferedImage pic4_reg;
	
	private Image pic1_reg_blue;
	private BufferedImage pic2_reg_blue;
	private BufferedImage pic3_reg_blue;
	private BufferedImage pic4_reg_blue;
	private BufferedImage frozenImage;
	
	private Image loadingScreen;
	
	private Image fragment;
	
	private BufferedImage playerGraveyard;
	private World world;
	private Image fragmentLvl1;
	
	//initial character color choose 
	private int colorSelect;
	
	private DLList<Integer> functionDLList;
	
	private JButton scrollColorButton;
	private boolean loading;
	
	private int player_ability_time = 0;
	private boolean player_ability_cooldown;
	
	private int player_dialogue_time = 0;
	private boolean player_dialogue_cooldown;

	private int walkingCooldown_time;
	
	private Sound soundPlayer;
	/*
	-utilize player code
	Have 1 player (the "1st" player who joined) create the array of enemies, and will manage enemy movements by passing in enemy coordinates and attack(optional) 
	send it to the rest by broadcasting it. 
	do not draw it specifically 
	each player has a check collision (check old code for execution) 
	for now, do not implement health just assume u get hit visible is false 
	****make sure to send only during/after receiving commands do not send stuff every 10 ms or so...
	
	*/
    public ClientScreen() throws IOException{
        this.setLayout(null);
		
		setup();
		///////////////
		//Pictures 
		try {
			pic1_fire = new ImageIcon("characterFiles/FireNinja/FireMoveRight.gif").getImage();
			pic2_fire = new ImageIcon("characterFiles/FireNinja/FireMoveLeft.gif").getImage();
			pic3_fire = ImageIO.read(new File("characterFiles/FireNinja/FireNinjaCostume.png"));
			pic4_fire = ImageIO.read(new File("characterFiles/FireNinja/FireCostumeWalkLeft.png"));
			pic5_fire = ImageIO.read(new File("characterFiles/FireNinja/FireCostumeWalkRight.png"));
			frozenImage = ImageIO.read(new File("characterFiles/FrozenAnimation.png"));
		} catch (IOException e) {
			System.out.println("Could not load Fire Ninja Pictures");
			System.exit(-1);
		}
		
		try{
			pic1_reg = new ImageIcon("characterFiles/RegularPenguin/MainCharacter.gif").getImage();
			pic2_reg = ImageIO.read(new File("characterFiles/RegularPenguin/Prone.png"));
			pic3_reg = ImageIO.read(new File("characterFiles/RegularPenguin/MoveLeft.png"));
			pic4_reg = ImageIO.read(new File("characterFiles/RegularPenguin/MoveRight.png"));
		} catch (IOException e) {
			System.out.println("Could not load Regular Penguin Images");
			System.exit(-1);
		}
		
		try{
			pic1_ice = ImageIO.read(new File("characterFiles/IceNinja/IceNinjaAttackRight.png"));
			pic2_ice = ImageIO.read(new File("characterFiles/IceNinja/IceNinjaAttackLeft.png"));
			pic3_ice = ImageIO.read(new File("characterFiles/IceNinja/IceNinjaCostume.png"));
			pic4_ice = ImageIO.read(new File("characterFiles/IceNinja/IceCostumeWalkLeft.png"));
			pic5_ice = ImageIO.read(new File("characterFiles/IceNinja/IceCostumeWalkRight.png"));
		} catch (IOException e) {
			System.out.println("Could not load Ice Ninja Costumes" + e);
			System.exit(-1);
		}
			
		try{
			loadingScreen = ImageIO.read(new File("mapFiles/start.png"));
			fragment = ImageIO.read(new File("mapFiles/fragment.png"));
			
			
		} catch (IOException e) {
			System.out.println("Could not load map/interface images");
			System.exit(-1);
		}
		
		////////////////////
		input = new Input(this);
    }
	public void setup() throws IOException{
		///////////////
		//BASIC VARIABLESS
		username = "";
		playerCode = -1;
		firstLogIn = true;
		loading = false;
		walkingCooldown_time = 0;
		
		playerList = new ArrayList<Player>();
		player_ability_cooldown = false;
		player_dialogue_cooldown = false;
		/////////////// 
		//INTERFACE STUFF
		//chatView = new JTextArea(400,300);
		
		msgTextfield = new JTextField(30);
        msgTextfield.setBounds(500,430, 380, 75);
		msgTextfield.setFont(new Font("Arial", Font.BOLD, 40)); 
		this.add(msgTextfield);
		msgTextfield.addActionListener(this);
		this.setFocusable(true);

		//NETWORKING STUFF
		hostName = "localhost";
        portNumber = 444;
        serverSocket = new Socket(hostName,portNumber);
		inObj = new PushbackInputStream(serverSocket.getInputStream());		
		inObjREAL = new ObjectInputStream(serverSocket.getInputStream());	
		outObj = new ObjectOutputStream(serverSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		out = new PrintWriter(serverSocket.getOutputStream(),true);
		repaint();
	}
	public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(1280,780);
		
    }
	public void paintComponent(Graphics g){
        //draw background
		super.paintComponent(g);
		
		
		if(firstLogIn){
			g.drawImage(loadingScreen,0,0,null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 35)); 
			g.drawString("Enter Username and click",450,390);
			g.drawString("Enter to begin.",550,425);
		}
		else{
			if(loading){
				g.drawImage(loadingScreen,0,0,null);
				g.setFont(new Font("Arial", Font.BOLD, 55)); 
				g.setColor(Color.WHITE);
				g.drawString("Waiting for other players...",390,460);
				if(playerList.size()>1){
					loading = false;
					soundPlayer.playSound("soundFiles/backgroundMusic.mp3",true);
					System.out.println("playing music");
				}
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 25)); 
				g.drawString("Player Count: " + playerList.size(),950,100);
				repaint();
			}
			else{
				Player temp = world.drawMe(g,getPlayer(playerCode),playerList);
				if(temp!=null){
					if(temp.ifFire()){
						getPlayer(playerCode).fireMode();
					}
					else if(temp.ifIce()){
						getPlayer(playerCode).iceMode();
					}
					sendInfo(getPlayer(playerCode));
				}
				for(int i = 0; i<playerList.size();i++){
					if(playerList.get(i).getLocationRow()==getPlayer(playerCode).getLocationRow()&&playerList.get(i).getLocationCol()==getPlayer(playerCode).getLocationCol()){
						if(playerList.get(i).ifFire()){
							playerList.get(i).drawMe(g,pic1_fire,pic2_fire,pic3_fire,pic4_fire,pic5_fire, pic1_reg,pic2_reg,pic3_reg,pic4_reg,frozenImage);
						}
						else if(playerList.get(i).ifIce()){
							playerList.get(i).drawMe(g,pic1_ice,pic2_ice,pic3_ice,pic4_ice,pic5_ice, pic1_reg,pic2_reg,pic3_reg,pic4_reg,frozenImage);
						}
						else{
							playerList.get(i).drawMe(g,pic1_fire,pic2_fire,pic3_fire,pic4_fire,pic5_fire, pic1_reg,pic2_reg,pic3_reg,pic4_reg,frozenImage);
						}
						
						//playerList.get(i).drawMe(g,pic1_fire,pic2_fire,pic3_fire,pic4_fire,pic5_fire, pic1_reg_blue,pic2_reg_blue,pic3_reg_blue,pic4_reg_blue);
						if(getPlayer(playerCode).getLocationRow()==0 && getPlayer(playerCode).getLocationCol()==0){
							g.drawImage(fragment,298,585,null);
						}
					}
				}
				
				int tempX = 550;
				if(player_ability_cooldown){
					int player1_sec_temp = player_ability_time;
					player1_sec_temp*=2;
					while(player1_sec_temp>0){
						g.setColor(Color.GREEN);
						g.fillRect(tempX,30,2,20);
						g.setColor(Color.BLACK);
						g.drawRect(tempX,30,2,20);
						tempX+=2;
						player1_sec_temp--;
					}
					
				}
				
				
			}
			
		}
		
	}

	public void run(){
        while(true){
			try {
				Thread.sleep(10);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			if(!firstLogIn){
				/*
				if(Input.keyboard[87] && Input.keyboard[65]){
					getPlayer(playerCode).moveDiagonalForwardLeft();
					sendInfo(getPlayer(playerCode));
					System.out.println("DiagonalForwardLeft");
				}
				else if(Input.keyboard[87] && Input.keyboard[68]){
					getPlayer(playerCode).moveDiagonalForwardRight();
					sendInfo(getPlayer(playerCode));
					System.out.println("moveDiagonalForwardRight");
				}
				else if(Input.keyboard[83] && Input.keyboard[65]){
					getPlayer(playerCode).moveDiagonalBackwardLeft();
					sendInfo(getPlayer(playerCode));
					System.out.println("moveDiagonalBackwardLeft");
				}
				else if(Input.keyboard[83] && Input.keyboard[68]){
					getPlayer(playerCode).moveDiagonalBackwardRight();
					sendInfo(getPlayer(playerCode));
					System.out.println("moveDiagonalBackwardRight");
				}
				*/
				if(!player_ability_cooldown && (Input.keyboard[87]||Input.keyboard[83]||Input.keyboard[65]||Input.keyboard[68])){
					if (Input.keyboard[87]) {
						getPlayer(playerCode).moveUp();
					}
					else if (Input.keyboard[83]) {
						getPlayer(playerCode).moveDown();
					}
					
					if (Input.keyboard[65]) {
						getPlayer(playerCode).moveLeft();
					}
					else if (Input.keyboard[68]) {
						getPlayer(playerCode).moveRight();
					}
					
					walkingCooldown_time = 10;
					//add ability moves here in another if statement outside the if statement with "or" exception
					
					sendInfo(getPlayer(playerCode));
					
					worldInteractions();
				}
				else if(!player_ability_cooldown && (Input.keyboard[69]||Input.keyboard[81]) &&(getPlayer(playerCode).ifFire()||getPlayer(playerCode).ifIce())){
					if(Input.keyboard[69]){
						getPlayer(playerCode).startFireMoveRight();
					}
					else{
						getPlayer(playerCode).startFireMoveLeft();
					}
					soundPlayer.playSound("soundFiles/woosh.mp3");
					player_ability_time = 75; //cooldown time
					player_ability_cooldown = true;
					
					
					
					if(getPlayer(playerCode).getLocationRow()==0 && getPlayer(playerCode).getLocationCol()==2){
						sendInfo("Ice Drop");
					}
					else{
						world.playerAttack(getPlayer(playerCode));
						sendInfo(getPlayer(playerCode));
					}
					
				}
				else if(player_ability_cooldown){
					if(player_ability_time>0){
						player_ability_time--;
					}
					if(player_ability_time==0){
						player_ability_cooldown = false;
						world.stopHitForAll();
						getPlayer(playerCode).prone();
						sendInfo(getPlayer(playerCode));
					}
					
				}
				else if(player_dialogue_cooldown){
					if(player_dialogue_time>0){
						player_dialogue_time--;
					}
					if(player_dialogue_time==0){
						player_dialogue_cooldown = false;
						world.stopHitForAll();
						getPlayer(playerCode).prone();
						sendInfo(getPlayer(playerCode));
					}
					
				}
				else if(!getPlayer(playerCode).isProne() && !player_ability_cooldown && walkingCooldown_time == 0){
					getPlayer(playerCode).prone();
					sendInfo(getPlayer(playerCode));
				}
				if(Input.keyboard[49] && !player_dialogue_cooldown){
					player_dialogue_time = 75;
					player_dialogue_cooldown = true;
					
					sendInfo("Next Sensei Dialogue");
				}
				if(walkingCooldown_time!=0){
					walkingCooldown_time--;
				}
				if(Input.keyboard[32] && getPlayer(playerCode).getLocationRow()==1 && getPlayer(playerCode).getLocationCol()==2){
					try{
						setup();
					} catch (IOException e) {
						System.out.println("Could not load map/interface images");
						System.exit(-1);
					}
				}
				else if(Input.keyboard[32] && !player_dialogue_cooldown){
					player_dialogue_time = 75;
					player_dialogue_cooldown = true;
					if(getPlayer(playerCode).getLocationRow()== 2 && getPlayer(playerCode).getLocationCol()==0){
						getPlayer(playerCode).setWorldLocation(1,2);
						System.out.println("hello");
					}
					else if(getPlayer(playerCode).getLocationRow()==0 && getPlayer(playerCode).getLocationCol()==1){
						getPlayer(playerCode).setWorldLocation(0,2);
						System.out.println("hello");
					}
					else if(getPlayer(playerCode).getLocationRow()==1 && getPlayer(playerCode).getLocationCol()==0){
						getPlayer(playerCode).setWorldLocation(1,1);
						System.out.println("hello");
					}
					else if(getPlayer(playerCode).getLocationRow()==0 && getPlayer(playerCode).getLocationCol()==0){
						if(playerCode==1001){
							getPlayer(playerCode).setWorldLocation(0,1);
						}
						else{
							getPlayer(playerCode).setWorldLocation(1,0);
							System.out.println("hello");
						}
					}
				}
				updateAll();
			}
		}
	}
	/*
	
	*/
	public void updateAll(){
		try{
			//System.out.println(inObj.available());
			while(inObj.available()>0){
				Object obj = inObjREAL.readObject();
				if(obj instanceof Player){
					Player temp = (Player)obj; 
					//set Player(1) values here::::
					//set boolean values later
					
					if(!contains(temp.getIDCode())){ //if does not contain temp, create new 
						playerList.add(temp);
					}
					else{
						updatePlayer(temp.getIDCode(),temp);
					}
				}
				else if(obj.toString().equals("Ice Drop")){
					world.hit();
				}
				else if(obj.toString().equals("Next Sensei Dialogue")){
					if(getPlayer(playerCode).getLocationRow()==0 && getPlayer(playerCode).getLocationCol()==2){
						if(world.worldClear(getPlayer(playerCode))){
							getPlayer(playerCode).setWorldLocation(1,2);
							getPlayer(playerCode).setLocation(300,434);
							sendInfo(getPlayer(playerCode));
							try {
								Thread.sleep(50);
							} catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
						}
					}
					else{
						while(!world.nextDialogue(getPlayer(playerCode).getLocationRow(),getPlayer(playerCode).getLocationCol())){
							try {
								Thread.sleep(30);
							} catch(InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							world.nextDialogue(getPlayer(playerCode).getLocationRow(),getPlayer(playerCode).getLocationCol());
							if(getPlayer(playerCode).getLocationRow()==1 && getPlayer(playerCode).getLocationCol()==1){
								worldInteractions();
							}
							repaint();
						}
					}
				}
				else if(obj.toString().equals("disengage")){
					//System.out.println("ATTACK");
					world.stopAttacking();
				}
				else if(obj.toString().equals("attack")){
					//System.out.println("ATTACK");
					Player temp = world.attackMode(getPlayer(playerCode));
					if(temp!=null){
						System.out.println("ATTACK");
						updatePlayer(temp.getIDCode(),temp);
						player_ability_time = 75; //cooldown time
						player_ability_cooldown = true;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Class does not exist" + e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to");
			System.exit(1);
		}
		repaint();
	}
	public void sendInfo(Player temp){
		try{
			outObj.reset();
			outObj.writeObject(temp);
		}catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to");
			System.exit(1);
		}
	}
	public void sendInfo(String temp){
		try{
			outObj.reset();
			outObj.writeObject(temp);
		}catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to");
			System.exit(1);
		}
	}
	public void initialSend(){
		try{
			outObj.reset();
			outObj.writeObject(new Player(626,626,username));
			
			while(firstLogIn){
				while(inObj.available()>0){
					Object obj = inObjREAL.readObject();
					if(obj instanceof Player){
						Player temp = (Player)obj; 
						if(!contains(temp.getIDCode())){ //if does not contain temp, create new 
							playerList.add(temp);
						}
						else{
							updatePlayer(temp.getIDCode(),temp);
						}
						
						//System.out.println("test");
						if(temp.getUsername().equals(username)){
							playerCode = temp.getIDCode();
							world = new World(getPlayer(playerCode));
							firstLogIn = false;
							if(playerList.size()<2){
								loading = true;
							}
							else{
								soundPlayer.playSound("soundFiles/backgroundMusic.mp3",true);
								System.out.println("playing music");
							}
						}
						repaint();
					}
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Class does not exist" + e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to");
			System.exit(1);
		}
		repaint();
	}
	public void actionPerformed(ActionEvent e) {
	
		if(firstLogIn){
			username = msgTextfield.getText();
			initialSend();
			msgTextfield.setText("");
			remove(msgTextfield);
			repaint();
		}
		else{
			
			/*
			out.println(username + ": " + msgTextfield.getText());
			msgTextfield.setText("");
			*/
		}
		
		repaint();
    }
	//Gate 1
	//X: 160-215
	//Y: <=490
	
	//Gate 215
	//X:560-680
	//Y:<=410
	public void worldInteractions(){
		if(getPlayer(playerCode).getLocationRow()==0 && getPlayer(playerCode).getLocationCol()==0){
			if(getPlayer(playerCode).getX()>160 && getPlayer(playerCode).getX()<215 && getPlayer(playerCode).getY()<490){
				getPlayer(playerCode).setWorldLocation(1,0);
				getPlayer(playerCode).setLocation(650,434);
				world.nextDialogue(getPlayer(playerCode).getLocationRow(),getPlayer(playerCode).getLocationCol());
				sendInfo(getPlayer(playerCode));
			}
			else if(getPlayer(playerCode).getX()>560 && getPlayer(playerCode).getX()<680 && getPlayer(playerCode).getY()<410){
				getPlayer(playerCode).setWorldLocation(0,1);
				getPlayer(playerCode).setLocation(644,518);
				world.nextDialogue(getPlayer(playerCode).getLocationRow(),getPlayer(playerCode).getLocationCol());
				sendInfo(getPlayer(playerCode));
			}
		}
		if(getPlayer(playerCode).getLocationRow()==1 && getPlayer(playerCode).getLocationCol()==0){
			if(getPlayer(playerCode).getX()>1100 && getPlayer(playerCode).getY()>438 && getPlayer(playerCode).getY()<514){
				getPlayer(playerCode).setWorldLocation(1,1);
				getPlayer(playerCode).setLocation(1000,200);
				world.nextDialogue(getPlayer(playerCode).getLocationRow(),getPlayer(playerCode).getLocationCol());
				sendInfo(getPlayer(playerCode));
			}
		}
		if(getPlayer(playerCode).getLocationRow()==0 && getPlayer(playerCode).getLocationCol()==1){
			if(getPlayer(playerCode).getX()>570 && getPlayer(playerCode).getX()<745 && getPlayer(playerCode).getY()>640){
				getPlayer(playerCode).setWorldLocation(0,2);
				getPlayer(playerCode).setLocation(110,614);
				world.nextDialogue(getPlayer(playerCode).getLocationRow(),getPlayer(playerCode).getLocationCol());
				sendInfo(getPlayer(playerCode));
			}
		}
		if(getPlayer(playerCode).getLocationRow()==1 && getPlayer(playerCode).getLocationCol()==1){
			if(getPlayer(playerCode).getX()>=1404 && getPlayer(playerCode).getY()<=-5){
				getPlayer(playerCode).setWorldLocation(0,2);
				getPlayer(playerCode).setLocation(110,614);
				world.skipToSecondPart();
				world.nextDialogue(getPlayer(playerCode).getLocationRow(),getPlayer(playerCode).getLocationCol());
				sendInfo(getPlayer(playerCode));
			}
		}
	}
	public void updatePlayer(int idCode, Player updatedPlayer){
		for(int i = 0; i<playerList.size();i++){
			if(playerList.get(i).getIDCode()==idCode){
				playerList.set(i,updatedPlayer);
			}
		}
	}
	public Player getPlayer(int idCode){
		for(int i = 0; i<playerList.size();i++){
			if(playerList.get(i).getIDCode()==idCode){
				return playerList.get(i);
			}
		}
		return null;
	}
	public boolean contains(int idCode){
		for(int i = 0; i<playerList.size();i++){
			if(playerList.get(i).getIDCode()==idCode){
				return true;
			}
		}
		return false;
	}
}
