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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.EntityDirec;
import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.playerAct;

/**
 * This class represents an instance of the game. It keeps track of the grid and everything in the grid.
 */

public class GameEngine {
	
	/**
	 * This object in {@link GameEngine} will hold the location of all entities in the game
	 * including consumables, level, and lives.
	 */
	Grid grid = new Grid();
	
	/**
	 * This object in {@link GameEngine} creates the user input to display the board to the
	 * user and prompts for user input.
	 */
	UserInterface userInput;
	
	/**
	 * This field holds the value for player location.
	 * This field is set by {@link #runGame} and {@link #ninjaKill}.
	 */
	int playerX, playerY;
	
	/**
	 * This field hold the action from the user. This field is edited by {@link #startGame()}
	 * and {@link #runGame}
	 */
	playerAct action;
	
	/**
	 * This field hold the player direction to be used in {@link GameEngine}.
	 * This field is edited by {@link #startGame} as a default value and {@link #userAction()}
	 * changes the value for use in the game.
	 */
	EntityDirec direction;
	
	/**
	 * This field tells the game to stop running and go back to main menu. This field starts
	 * {@code true}. This is edited by {@link #startGame()}, {@link #ninjaKill}, and  {@link #userAction}.
	 */
	boolean gameOver = true;
	
	/**
	 * This field changes the game mode to debug mode. This field is default {@code false} and
	 * is edited by {@link #userAction}.
	 */
	boolean debug = false;
	
	/**
	 * This field changes the game mode to hard mode. This field is default {@code false} and
	 * is edited by {@link #userAction}.
	 */
	boolean hardmode = false;
	
	/**
	 * This field tells the game to stop running at the end of the level. This field starts
	 * {@code true}. This is edited by {@link #startGame} and {@link #playerMoves}.
	 */
	boolean gameRunning = true;
	
	/**
	 * These fields hold the current value of consumables. These are edited by {@link #updateConsumables}
	 */
	int lives, ammo, invuln, level;
	
	/**
	 * This field tells the game engine the max possible level the player can reach. default 15
	 */
	int maxLevel = 15;
	
	/**
	 * This field is used to determine if the player has won the game.
	 */
	boolean playerWins;
	
	/**
	 * This field is used when the player wants to exit the game.
	 * To exit, this value is set to true.
	 */
	boolean exit = false;
	
	/**
	 * This field is used when the player wants to load a saved game.
	 * To load, this value is set to true.
	 */
	boolean load = false;
	
	/**
	 * This method is used to initialize an instance of the game.
	 */
	public void startGame(UserInterface userInterface){
		
		userInput = userInterface;
		
		exit = false;
		
		while(!exit){	
			
			action = userInput.menu(debug);
			userAction();
			
			boolean runOnce = false;
			while(!gameOver){
				runOnce = true;
				
				gameRunning = true;
				if(!load){
					grid.setGrid(hardmode);
				}else{
					load = false;
				}
				
				grid.setCover();
				updateUserInterface();
				updateConsumables();
				direction = EntityDirec.WEST;
				
				runGame();
				
				if(level < maxLevel){
					userInput.nextLevel(level);
				}else{
					playerWins = true;
					gameOver = true;
				}
			}
			
			if(runOnce){
				userInput.gameOver(level, playerWins);	
				grid.resetLevel();
			}
		}
	}
	
	/**
	 * This method is used to run the game once initialized. It checks for player deaths, lets the user take his turn,
	 * and updates the user interface.
	 */
	private void runGame(){
		
		while(gameRunning){
			
			
			
			if(!userInput.delay()){
				if(!debug){
					ninjaKill();	
				}
				if(gameOver){
					break;
				}
				grid.moveNinja(hardmode,level);
				grid.player.invuln();
			}
			
			updateConsumables();
			playerX = grid.player.getEntityX();
			playerY = grid.player.getEntityY();
			updateUserInterface();
			userInput.displayGrid(lives, ammo, invuln, level, debug, hardmode);
			
			action = userInput.userInput(false);
			grid.setCover();
			userAction();
			
			if(action == playerAct.LOOK){
				updateUserInterface();
				updateConsumables();
				userInput.displayGrid(lives, ammo, invuln, level,debug,hardmode);
				action = userInput.userInput(true);
				userAction();
			}
			
			updateEntity();
			grid.setCover();
			if(action == playerAct.WAIT){
				playerLooks();	
			}
			updateUserInterface();
		}
	}
	
	/**
	 * This method is used to update {@link Entity} locations on the grid.
	 */
	public void updateEntity(){
		userInput.playerPos(grid.player.getEntityX(), grid.player.getEntityY());
		
		for(int i = 0; i < grid.numOfNinja(); i++){
			userInput.ninjaPos(i, grid.ninja[i].getEntityX(), grid.ninja[i].getEntityY(),grid.ninja[i].getNinjaStatus());
		}
	}
	
	/**
	 * This method checks every ninja to see if the player is adjacent to them.
	 * If the player is adjacent the ninja kills them.
	 */
	private void ninjaKill(){	
		playerX = grid.player.getEntityX();
		playerY = grid.player.getEntityY();
		
		if( invuln == 0){
			if(grid.getGridEntity(playerX-1, playerY).equalsIgnoreCase("N") ||
					grid.getGridEntity(playerX+1, playerY).equalsIgnoreCase("N") ||
					grid.getGridEntity(playerX, playerY-1).equalsIgnoreCase("N") ||
					grid.getGridEntity(playerX, playerY+1).equalsIgnoreCase("N")){
				if(lives == 0){
					gameOver = true;
				}
				grid.killPlayer(playerX, playerY);
				userInput.playerKilled();
			}
		}
	}
	
	/**
	 * This method is used when the player wishes to look in a direction on the grid.
	 * It reveals 2 tiles in the chosen direction when called.
	 */
	public void playerLooks(){
		switch(direction){
		case NORTH:
			grid.revealCover(playerX, playerY - 2);
			grid.revealCover(playerX, playerY - 3);
			break;
		case EAST: 
			grid.revealCover(playerX + 2, playerY);
			grid.revealCover(playerX + 3, playerY);
			break;
		case SOUTH: 
			grid.revealCover(playerX, playerY + 2);
			grid.revealCover(playerX, playerY + 3);
			break;
		case WEST: 
			grid.revealCover(playerX - 2, playerY);
			grid.revealCover(playerX - 3, playerY);
		}
	}
	
	/**
	 * This method checks to see if a ninja is in direction of gun being fired.
	 * It checks from user position to the end of the grid. The direction (north) {@code 0}, 
	 * (east) {@code 1}, (south) {@code 2}, and (west) {@code 3}.
	 */
	public void playerShoots(){
		
		if(ammo > 0){
			switch(direction){
			case NORTH:
				for(int i = playerY; i >= 0; i--){
					if(grid.getGridEntity(playerX, i) == "N"){
						grid.killNinja(playerX,i);
					}
				}
				break;
			case EAST: 
				for(int i = playerX; i < (grid.getGridLenght()); i++){
					if(grid.getGridEntity(i, playerY) == "N"){
						grid.killNinja(i,playerY);
					}
				}
				break;
			case SOUTH: 
				for(int i = playerY; i < (grid.getGridLenght()); i++){
					if(grid.getGridEntity(playerX, i) == "N"){
						grid.killNinja(playerX,i);
					}
				}
				break;
			case WEST: 
				for(int i = playerX; i >= 0; i--){
					if(grid.getGridEntity(i, playerY) == "N"){
						grid.killNinja(i,playerY);
					}
				}
			}
		}else{
			userInput.outOfAmmo();
		}
		grid.player.shoot();
	}
	
	/**
	 * This method is determined by player input. It is used to take player action, in addition
	 * to providing access to debug and hard mode.
	 */
	private void userAction(){
		switch(action){
		case LOOK:
			direction = userInput.getUserDirection();
			playerLooks();
			break;
		case SHOOT:
			direction = userInput.getUserDirection();
			playerShoots();
			break;
		case MOVE:
			direction = userInput.getUserDirection();
			playerMoves();
			break;
		case SAVE:
			save();
			break;
		case LOAD:
			load();
			break;
		case DEBUG:
			if(!debug){
				debug = true;
			}else{
				debug = false;
			}
			break;
		case HARD:
			if(!hardmode){
				hardmode = true;
			}else{
				hardmode = false;
			}
		case START:
			gameOver = false;
			break;
		case EXIT:
			exit = true;
		case RETURN:
			gameRunning = false;
			gameOver = true;
			break;
		}
	}
	
	/**
	 * This method is used to update the user interface. This includes items such as the grid, number of lives,
	 * and powerups.
	 */
	private void updateUserInterface(){
		boolean seen = false;
		if(debug){
			for(int i = 0; i < grid.getGridLenght(); i++){
				for(int j = 0; j < grid.getGridLenght(); j++){
					userInput.updateUserGrid(i, j, grid.getGridEntity(i, j));
				}
			}
		}else{
			for(int i = 0; i < grid.getGridLenght(); i++){
				for(int j = 0; j < grid.getGridLenght(); j++){
					String space = grid.getCoveredGridEntity(i, j);
					if(space == "N" && !seen){
						userInput.warning();
						seen = true;
					}
					userInput.updateUserGrid(i, j, space);
				}
			}
		}
	}
	
	/**
	 * This method is used to determine player movement. It first checks to make sure the player
	 * can move to the specified space, then moves him there if it is safe. If the player attempts to move
	 * onto the document, it will move to the next level.
	 */
	private void playerMoves(){
		String entity = grid.playerCollision(direction);
		boolean safe = false;
		
		switch(entity){
		case "R":
			grid.radar();
			safe = true;
			break;
		case "I":
			grid.addInvuln();
			safe = true;
			break;
		case "B":
			grid.addBullet();
			safe = true;
			break;
		case " ": 
			safe = true;			
		}
		
		if(safe){
			grid.movePlayer();
		}else if(direction == EntityDirec.SOUTH){
			if(entity.equalsIgnoreCase("E")){
				userInput.emptyRoom();
			}else if(entity.equalsIgnoreCase("D")){
				gameRunning = false;
				userInput.docRoom();
				grid.nextLevel();
			}
		}
	}
	
	/**
	 * This method is used to save an instance of the game to a file.
	 * The user must input a name for the file.
	 */
	private void save(){ 
		String saveFile = userInput.UserSave();
		FileOutputStream fis;
		ObjectOutputStream dos;
		try {
			fis = new FileOutputStream(saveFile + ".dat");
			dos = new ObjectOutputStream(fis);
			dos.writeObject(grid);
		} catch (IOException e) {
			userInput.fileNotFound(saveFile);
		}
		 
		
	}
	
	/**
	 * This method is used to load an instance of the game from a file.
	 * The user must input the name of said file.
	 */
	private void load(){
		String saveFile = userInput.userLoad();
		FileInputStream fis;
		ObjectInputStream dos;
		try {
			fis = new FileInputStream(saveFile);
			dos = new ObjectInputStream(fis);
			
			grid = (Grid) dos.readObject();
		
			if(!gameRunning){
				load = true;
			}			
			gameOver = false;
		} catch (IOException | ClassNotFoundException e) {
			userInput.fileNotFound(saveFile);
		}		
	}
	
	/**
	 * This method is used to update the player's current consumables.
	 */
	private void updateConsumables(){
		level = grid.getLevel();
		lives = grid.player.getLives();
		ammo = grid.player.getAmmo();
		invuln = grid.player.getInvuln();
	}
}