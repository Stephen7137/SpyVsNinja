/**
 * CS 141: Introduction to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #Group project
 *
 * Develop a game where the user plays as a spy and has to search
 * 9 Different rooms on a grid. User must avoid ninjas to not be
 * killed while searching for the rooms. There are power ups spread
 * through out the grid to help player reach the room. Player wins
 * when they check the room with the documents.
 *
 * Team #We just blew your freakin’ mind!!!” 
 *   Joshua Ludwig, Oscar Zaragoza, Stephen Jackson, Mohamed Saad
 */
package edu.csupomona.cs.cs141.prog_assgmnt_team;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.EntityDirec;
import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.playerAct;

/**
 * This class creates the window on which the game is to be played
 * and creates the window the player inputs save/loading from.
 */
public class GUI extends UserInterface {
	
	private Canvas canvas = new Canvas();

	private JFrame window = new JFrame("Spy Vs Ninja!");
	
	private JPanel panel = (JPanel) window.getContentPane();
	
	private JFrame userInput;
	
	private JLabel userMessage;
	
	private JTextField userString;
	
	private JPanel userInputPanel;
	
	private JButton component;
	
	private JComboBox<String> userDropDown;
	
	private BufferStrategy strategy;
	
	private String[][] grid = new String[9][9];
	
	private long lastLoopTime;
	
	private Display display;
	
	private RoomSprite[][] spriteRoom = new RoomSprite[3][3];
	
	private NinjaSprite[] ninja = new NinjaSprite[6];
	
	private PlayerSprite player;
	
	private Shadow[][] shadow = new Shadow[9][9];
	
	private PowerUpSprite powerUp;
	
	private Menu menu;
	
	long delta;
	
	boolean leftPressed, rightPressed, upPressed, downPressed, spacePressed, released, enter;
	
	private EntityDirec direction = EntityDirec.WEST;
	
	private playerAct action = playerAct.WAIT;
	
	int playerAction;
	
	boolean waitForPlayer = true;
	
	int startingX = 11 + 64, startingY = 11 + 32;
	
	int playerX, playerY;
	
	long ninjaWarning, emptyAlert;
	
	long turnDuration = 400;
	
	boolean delay = true;
	
	int cursorX = 0;
	
	boolean option, help, start, sent, mainMenu, error = false, paused, save, load, dead;
	
	double turn, turnWait = 50;
	
	String input;
	
	/**
	 * Constructor creates the window and reveals it to the player
	 */
	public GUI(){
		
		panel.setPreferredSize(new Dimension(800, 600));
		
		panel.setLayout(null);
		
		
		canvas.setBounds(0,0,800, 600);
		panel.add(canvas);
		
		canvas.setIgnoreRepaint(true);
		
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		
		canvas.addKeyListener(new KeyInputHandler());
		
		canvas.requestFocus();
		
		initializeEntities();
	
	}
	
	/**
	 * This method loads all of the sprites used during the game.
	 */
	private void initializeEntities(){
		
		for(int i = 0; i < spriteRoom.length; i++){
			for(int j = 0; j < spriteRoom.length; j++){
				spriteRoom[i][j] = new RoomSprite(startingX + ( i * 64 ) * 3,
						startingY + ( j * 64 ) * 3);
			}
		}
		
		for(int i = 0; i < shadow.length; i++){
			for(int j = 0; j < shadow.length; j++){
				shadow[i][j] = new Shadow(11 + 64 * i, 11 + 64 * j);
			}
		}			
		
		player = new PlayerSprite();
		
		for(int i = 0; i < ninja.length; i++){
			ninja[i] = new NinjaSprite();
		}
		
		display = new Display();
		
		powerUp = new PowerUpSprite();
		
		menu = new Menu();
	}
	
	/**
	 * This method resets all user input at start of the game.
	 */
	private void startGame() {
		upPressed = false;
		downPressed = false;
		leftPressed = false;
		rightPressed = false;
		spacePressed = false;
	}
	
	/**
	 * Displays the grid to the player and takes the user input.
	 * @param lives lives of the player
	 * @param ammo ammo the player currently has.
	 * @param invuln how many steps of invulnerability.
	 * @param level what level the player is on.
	 * @param debug boolean value for debug.
	 * @param hard boolean value for hard mode.
	 */
	public void displayGrid(int lives, int ammo, int invuln, int level, boolean debug, boolean hard){
		
		if(paused){
			pauseMenu(debug,hard);
		}else{
			movePlayer();
		}
		
		if(paused){
			drawPause(debug, hard);
		}else{
			drawScreen(lives, ammo, invuln, level);
		}
	}
	
	/**
	 * This method composes the frame on which the to be drawn to the screen.
	 * @param lives lives of the player
	 * @param ammo ammo the player currently has.
	 * @param invuln how many steps of invulnerability.
	 * @param level what level the player is on.
	 */
	public void drawScreen(int lives, int ammo, int invuln, int level){
		
		delta = System.nanoTime()/1000000 - lastLoopTime;
		lastLoopTime = System.nanoTime()/1000000;
		
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
		
		display.drawBackground(graphics);
		
		display.drawDisplay(graphics, lives, ammo, invuln, level);
		
		if(ninjaWarning > 0){
			display.drawNinjaWarning(graphics);
			ninjaWarning -= delta;
		}
		
		if(emptyAlert > 0){
			display.drawEmptyAlert(graphics);
			emptyAlert -= delta;
		}
		
		for(int i = 0; i < ninja.length; i++){
			if(ninja[i].getNinjaStatus()){
				ninja[i].drawEntity(graphics);
			}
		}
		
		for(int i = 0; i < shadow.length; i++){
			for(int j = 0; j < shadow.length; j++){
				
				if(grid[i][j] != "-"){
					if(!shadow[i][j].reveal(delta)){
						shadow[i][j].drawEntity(graphics);
					}
					
					if(grid[i][j].equalsIgnoreCase("R")){
						powerUp.drawRadar(graphics, i, j);
					}else if(grid[i][j].equalsIgnoreCase("I")){
						powerUp.drawInvuln(graphics, i, j);
					}else if(grid[i][j].equalsIgnoreCase("B")){
						powerUp.drawExtrAmmo(graphics, i, j);
					}
				}else if(grid[i][j] == "-"){
					shadow[i][j].hide(delta);
					shadow[i][j].drawEntity(graphics);
				}
			}
		}
		
		player.drawEntity(graphics);
		
		for(int i = 0; i < spriteRoom.length; i++){
			for(int j = 0; j < spriteRoom.length; j++){
				if(grid[(i * 3) + 1][(j * 3) + 1].equalsIgnoreCase("D")){
					spriteRoom[i][j].drawDoc(graphics);
				}else{
					spriteRoom[i][j].drawRoom(graphics);
				}
			}
		}
		if(waitForPlayer){
			graphics.setColor(Color.BLACK);
			graphics.fillRect(12, 12, 576, 576);
			display.drawNextLevel(graphics, level);
			if(dead){
				display.drawDead(graphics);
			}
		}
		
		graphics.dispose();
		strategy.show();
		
		try { Thread.sleep(10); } catch (Exception e) {}
	}
	
	/**
	 * This method reacts to user input of the keys and moves
	 * the player in the game.
	 */
	public void movePlayer(){
		
		if (leftPressed && released) {
			direction = EntityDirec.WEST;
			action = playerAct.MOVE;
			player.movePlayer(direction);
			released = false;
			delay = false;
		} else if (rightPressed && released) {
			direction = EntityDirec.EAST;
			action = playerAct.MOVE;
			player.movePlayer(direction);
			released = false;
			delay = false;
		} else if (upPressed && released) {
			direction = EntityDirec.NORTH;
			action = playerAct.MOVE;
			player.movePlayer(direction);
			released = false;
			delay = false;
		} else if (downPressed && released) {
			direction = EntityDirec.SOUTH;
			action = playerAct.MOVE;
			player.movePlayer(direction);
			delay = false;
			released = false;
		} else if(spacePressed && released){
			action = playerAct.SHOOT;
			delay = false;
			released = false;
		} 
		
		if(!leftPressed && !rightPressed && !upPressed && !downPressed && !spacePressed){
			released = true;
		}
	}
	
	/**
	 * This method crates a keylistener so player can input commands
	 */
	private class KeyInputHandler extends KeyAdapter {
				
		public void keyPressed(KeyEvent e) {

			if (waitForPlayer) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				spacePressed = true;
			}
			
		} 
		
		public void keyReleased(KeyEvent e) {
			
			if (waitForPlayer) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				spacePressed = false;
			}
		}

		public void keyTyped(KeyEvent e) {

			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				waitForPlayer = false;
				dead = false;
				startGame();
			}
			
			if (e.getKeyChar() == 27) {
				if(paused){
					paused = false;
				}else{
					paused = true;
				}
			}
		}

		
	}
	
	public void updateUserGrid(int i, int j, String name){
		grid[i][j] = name;
	}
	
	/**
	 * This method forces the player to look and then sees what
	 * action they want to perform.
	 * @param looked boolean asking if the player is looking or not.
	 * @return returns action to be performed.
	 */
	public playerAct userInput(boolean looked){
		
		playerAct playerAction;
		
		if(!looked){
			playerAction = playerAct.LOOK;
		}else{
			playerAction = action;
			action = playerAct.WAIT;
		}
		
		return playerAction;
	}

	/**
	 * This method drawns the Game of screen and tells the player
	 * what level they are on and if they finished the game or not.
	 * @param level
	 * @param playerWins
	 */
	public void gameOver(int level, boolean playerWins) {
		waitForPlayer = true;

		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
		
		while(waitForPlayer){
			
			display.drawBackground(graphics);
			
			graphics.setColor(Color.BLACK);
			graphics.fillRect(12, 12, 576, 576);
			
			display.drawGameOver(graphics, level, playerWins);
			
			graphics.dispose();
			strategy.show();
			
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}

	public void nextLevel(int level) {
		waitForPlayer = true;		
	}

	public void outOfAmmo() {
	}

	public void emptyRoom() {
		emptyAlert = turnDuration * 3;
	}

	public void docRoom() {
	}

	/**
	 * This method keeps track of the user action during the menu.
	 * keeps track of where they are in the menu and what action they
	 * want to perform such as toggling debug mode or starting a new game.
	 * @param debug boolean value for debug.
	 * @return The action the player wants to happen.
	 */
	public playerAct menu(boolean debug) {
		mainMenu = true;
		waitForPlayer = false;
		
		delta = System.nanoTime()/1000000 - lastLoopTime;
		lastLoopTime = System.nanoTime()/1000000;
		
		if(start){
			moveCursor(3);
			
			if(enter){
				if(cursorX == 0){
					action = playerAct.START;
					waitForPlayer = true;
					start = false;
				}else if(cursorX == 1){
					action = playerAct.HARD;
					waitForPlayer = true;
					start = false;
				}else if(cursorX == 2){
					start = false;
					cursorX = 0;
				}
			}
		}else if(help){
			moveCursor(1);
			
			if(enter){
				help = false;
				cursorX = 0;
			}
		}else if(option){
			moveCursor(2);
			
			if(sent){
				action = playerAct.WAIT;
			}
			
			if(enter){
				if(cursorX == 0){
					action = playerAct.DEBUG;
				}else if(cursorX == 1){
					option = false;
					cursorX = 0;
				}
			}
		}else{
			moveCursor(5);
			
			if(enter){
				switch(cursorX){
				case 0:
					start = true;
					cursorX = 0;
					break;
				case 1:
					buildLoad();
					load = true;
					break;
				case 2:
					help = true;
					cursorX = 0;
					break;
				case 3:
					option = true;
					cursorX = 0;
					break;
				case 4:
					action = playerAct.EXIT;
				}
			}
		}
		
		if(error){
			buildError();
			error = false;
			input = null;
		}
		
		drawMenu(debug);
		
		enter = false;
		sent = true;
		return action;
	}
	
	/**
	 * This method builds the window the player will be saving from.
	 */
	private void buildLoad(){
		URL path = GameEngine.class.getProtectionDomain()
				.getCodeSource().getLocation();
		
		File dir = null;
		try {
			dir = new File(path.toURI());
			dir = dir.getParentFile();
		} catch (URISyntaxException e1) {
		}
				
		File[] fileName = dir.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".dat");
			}
			
		});
		
		buildInformaitonBox();
				
		userInput.setTitle("Load");
		
		if(fileName != null){
			String[] fileSName = new String[fileName.length];
			for(int i = 0;i < fileName.length;i++){
				fileSName[i] = fileName[i].getName();
			}
			
			userMessage = new JLabel("Select Save File Name");
			userDropDown = new JComboBox<>(fileSName);
			userInputPanel.add(userDropDown);
		}else{
			userMessage = new JLabel("No Saved files");
		}
		component = new JButton("Enter");
		component.addActionListener(new componentListener());
		
		userInputPanel.add(userMessage);
		
		userInputPanel.add(component);
		
		Point location = window.getLocationOnScreen();
		
		userInput.add(userInputPanel);
		
		userInput.setLocation(location.x + 250,location.y + 253);
		
		userInput.setVisible(true);
	}
	
	private void buildInformaitonBox(){
		userInput = new JFrame();
		userInputPanel = new JPanel();
		
		userInput.setSize(300, 100);
	}
	
	/**
	 * This class creates a aciton listener so when the player 
	 * its enter one the box for saving/loading it takes the information
	 * and destroys the window.
	 */
	private class componentListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(save){
				input = userString.getText();
				action = playerAct.SAVE;
				cursorX = 0;
				save = false;
			}else if(load){
				input = (String) userDropDown.getSelectedItem();
				action = playerAct.LOAD;
				waitForPlayer = false;
				cursorX = 0;
				load = false;
			}
			userInput.dispose();
		}
	}
	
	private class ErrorListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			userInput.dispose();
		}
	}
	
	/**
	 * This method drawns the menu to the screen.
	 * @param debug boolean value for debug.
	 */
	public void drawMenu(boolean debug){
		
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
		
		menu.drawMenuBackground(graphics);	
		
		if(start){
			menu.drawStartGame(graphics, cursorX);
		}else if(option){
			menu.drawMainOption(graphics, cursorX, debug);
		}else if(help){
			menu.drawHelp(graphics);
		}else{
			menu.drawMainMenu(graphics, cursorX);
		}
		
		graphics.dispose();
		strategy.show();
		
		try { Thread.sleep(10); } catch (Exception e) {}
	}
	
	/**
	 * This method takes user input and moves cursor on the
	 * menu.
	 * @param options the number of places the player can move
	 * when menu is open.
	 */
	public void moveCursor(int options){
		
		if (upPressed && released) {
			cursorX --;
			released = false;
		} else if (downPressed && released) {
			cursorX ++;
			released = false;
		} else if(spacePressed && released){
			enter = true;
			released = false;
		} 
		
		if(cursorX < 0){
			cursorX = 0;
		}
		if(cursorX >= options){
			cursorX = options - 1;
		}
		
		if(!upPressed && !downPressed && !spacePressed){
			enter = false;
			released = true;
		}
	}

	public boolean delay() {
		boolean ninjaDelay;
		
		ninjaDelay = delay;
		delay = true;
		
		return ninjaDelay;
	}

	public void ninjaPos(int i, int entityX, int entityY, boolean b) {
		ninja[i].setPos(entityX, entityY,b);
	}

	public void playerPos(int entityX, int entityY) {
		player.setPos(entityX, entityY);
	}

	public EntityDirec getUserDirection() {
		return direction;
	}

	public void warning() {
		ninjaWarning = turnDuration;
	}

	public String UserSave() {
		return input;
	}

	public String userLoad() {		
		return input;
	}
	
	/**
	 * build the error box to be displayed and sets it visible
	 * to the user.
	 */
	public void buildError(){
		buildInformaitonBox();
		
		userInput.setTitle("ERROR");
		userMessage = new JLabel("File " + input + " not found.");
		
		component = new JButton("Enter");
		component.addActionListener(new ErrorListener());
		
		userInputPanel.add(userMessage);
		userInputPanel.add(component);
		
		Point location = window.getLocationOnScreen();
		
		userInput.add(userInputPanel);
		
		userInput.setLocation(location.x + 250,location.y + 253);
		
		userInput.setVisible(true);
	}

	/**
	 * Creates a box to tell that the file was not found.
	 * @param saveFile name of file.
	 */
	public void fileNotFound(String saveFile) {
		error = true;
		input = saveFile;
		action = playerAct.WAIT;
	}
	
	/**
	 * This method keeps track of the cursor and draws screen of 
	 * the pause menu.
	 * @param debug boolean value for debug.
	 * @param hard boolean value for hard mode.
	 */
	private void pauseMenu(boolean debug, boolean hard) {
			
		if(option){
			moveCursor(3);
			
			if(enter){
				if(cursorX == 0){
					action = playerAct.DEBUG;
				}else if(cursorX == 1){
					action = playerAct.HARD;
				}else if(cursorX == 2){
					option = false;
					cursorX = 0;
				}
			}
		}else{
			moveCursor(4);
			
			if(enter){
				switch(cursorX){
				case 0:
					buildSave();
					save = true;
					cursorX = 0;
					break;
				case 1:
					buildLoad();
					load = true;
					cursorX = 0;
					break;
				case 2:
					option = true;
					cursorX = 0;
					break;
				case 3:
					action = playerAct.RETURN;
					cursorX = 0;
					paused = false;
				}
			}
		}
		
		if(error){
			buildError();
			error = false;
			input = null;
		}
		
		enter = false;
	
	}

	/**
	 * This method creates the pause menu and draw it to the screen.
	 * @param debug boolean value for debug.
	 * @param hard boolean value for hard mode.
	 */
	private void drawPause(boolean debug, boolean hard) {
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
		
		menu.drawMenuBackground(graphics);	
		
		if(option){
			menu.drawPauseOption(graphics, cursorX, debug, hard);
		}else{
			menu.drawPauseMenu(graphics, cursorX);
		}
		
		graphics.dispose();
		strategy.show();
		
		try { Thread.sleep(10); } catch (Exception e) {}
		
	}

	/**
	 * This method creates the screen that the user is to input
	 * save file name.
	 */
	private void buildSave() {
		buildInformaitonBox();
		
		userInput.setTitle("Save");
		userMessage = new JLabel("Enter File Name");
		
		userString = new JTextField(10);
		
		component = new JButton("Enter");
		component.addActionListener(new componentListener());
		
		userInputPanel.add(userMessage);
		userInputPanel.add(userString);
		userInputPanel.add(component);
		
		Point location = window.getLocationOnScreen();
		
		userInput.add(userInputPanel);
		
		userInput.setLocation(location.x + 250,location.y + 253);
		
		userInput.setVisible(true);
	}

	/**
	 * This method pauses the game to notify they are dead.
	 */
	public void playerKilled() {
		waitForPlayer = true;
		dead = true;
	}
}

