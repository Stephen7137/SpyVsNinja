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

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.playerAct;
import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.EntityDirec;

/**
 * This class represents the user interface. It is used to take input and display
 * information from the current game.
 */
public class UserInterface {

	Scanner userInput = new Scanner(System.in);
	
	String [][] grid = new String[9][9];
	
	/**
	 * This method is used to display the current state of the grid, including spaces that are covered.
	 * @param lives Number of lives
	 * @param ammo Number of bullets
	 * @param invuln Number of turns of invulnerability left
	 * @param level Level the player is currently on
	 * @param hardmode 
	 * @param debug 
	 */
	public void displayGrid(int lives, int ammo, int invuln, int level, boolean debug, boolean hardmode){
		System.out.println("Spy vs Ninja!\tLevel: " + level);
		System.out.println("Live(s): " + lives + " Ammo: " + ammo + " Invuln Turns: " + invuln);
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				System.out.print("[" + grid[j][i] + "]");
			}
			System.out.println();
		}
	}
	
	/**
	 * This method is used to get user input for what action to take. Used once a turn.
	 * @param look Determines if the player wants to look
	 * @return Whatever action the player chooses
	 */
	public playerAct userInput(boolean look){
		System.out.println("What action to you want to take?");
		if(!look){
			System.out.print("Look(l)/Shoot(s)/Move(m)/Wait(w)/Save/Load/Exit: ");
		}else{
			System.out.print("Shoot(s)/Move(m)/Wait(w)/Save/Load/Exit: ");
		}
		String answer = userInput.nextLine();
		
		playerAct action;
		
		if((answer.equalsIgnoreCase("look") ||
				answer.equalsIgnoreCase("l")) && !look){
			action = playerAct.LOOK;
		}else if((answer.equalsIgnoreCase("shoot") ||
				answer.equalsIgnoreCase("s"))){
			action = playerAct.SHOOT;
		}else if((answer.equalsIgnoreCase("move") ||
				answer.equalsIgnoreCase("m"))){
			action = playerAct.MOVE;;
		}else if(answer.equalsIgnoreCase("save")){
			action = playerAct.SAVE;
		}else if(answer.equalsIgnoreCase("load")){
			action = playerAct.LOAD;
		}else if(answer.equalsIgnoreCase("debug")){
			action = playerAct.DEBUG;
		}else if(answer.equalsIgnoreCase("hard")){
			action = playerAct.HARD;
		}else if(answer.equalsIgnoreCase("exit")){
			action = playerAct.RETURN;
		}else {
			action = playerAct.WAIT;
		}
		System.out.println();
		
		return action;
	}
	
	/**
	 * This method gets user input for what direction to move/look in
	 * @return Direction of player's choosing
	 */
	public EntityDirec getUserDirection(){
		System.out.println("Which Direction?");
		System.out.print("North(n)/South(s)/East(e)/West(w): ");
		String answer = userInput.nextLine();
		
		EntityDirec direction;
		
		if((answer.equalsIgnoreCase("east") ||
				answer.equalsIgnoreCase("e"))){
			direction = EntityDirec.EAST;
		}else if((answer.equalsIgnoreCase("south") ||
				answer.equalsIgnoreCase("s"))){
			direction = EntityDirec.SOUTH;
		}else if((answer.equalsIgnoreCase("west") ||
				answer.equalsIgnoreCase("w"))){
			direction = EntityDirec.WEST;
		}else{
			direction = EntityDirec.NORTH;
		}
		System.out.println();
		
		return direction;
		
	}
	
	/**
	 * This method updates the grid to be displayed to the user.
	 * @param x is the vertical position in the grid.
	 * @param y is the horizontal position in the grid.
	 * @param name is the name of the entity at position x and y.
	 */
	public void updateUserGrid(int x, int y, String name){
		grid[x][y] = name;
	}
	
	public void pathingError(){
		System.out.println("Can not move there.");
	}

	public void emptyRoom() {
		System.out.println("The room is empty.\n");
		
	}

	public void docRoom() {
		System.out.println("You found the documents.");
		
	}

	public void outOfAmmo() {
		System.out.println("\t=Out of Ammo=\n");
		
	}

	/**
	 * This method is used when the player wins the game or loses all lives.
	 * It displays a message dependent on wheter the player won or lost.
	 * @param level Level the player gets to when game ends
	 * @param playerWins Determines if the player has won or lost
	 */
	public void gameOver(int level, boolean playerWins) {
		if(playerWins){
			System.out.println("You Won!!!\nYou are a master spy.");
		}else{
			System.out.println("\tGame Over\nYou made it to level " + level + "!");
			System.out.println("Better luck next time.\n");
		}
		
	}

	public void nextLevel(int level) {
		System.out.println("LEVEL " + level + "\n");
		
	}

	/**
	 * This method is used to display the menu to the user.
	 * From here the user may start a game, exit, load a game, and toggle hard/debug mode.
	 * @param debug 
	 * @return Action of player's choosing
	 */
	public playerAct menu(boolean debug) {
		boolean help = false;
		playerAct action = playerAct.WAIT;
		do{
			System.out.println("Welcome to Spy vs Ninja");
			
			System.out.print("start/Load/help/exit: ");
		
			String answer = userInput.nextLine();
			
			if(answer.equalsIgnoreCase("start")){
				System.out.print("Normal or Hard: ");
				answer = userInput.nextLine();
				if(answer.equalsIgnoreCase("hard")){
					action = playerAct.HARD;
				}else{
					action = playerAct.START;
				}
			}else if(answer.equalsIgnoreCase("exit")){
				action = playerAct.EXIT;
			}else if(answer.equalsIgnoreCase("load")){
				action = playerAct.LOAD;
			}else if(answer.equalsIgnoreCase("debug")){
				action = playerAct.DEBUG;
			}else if(answer.equalsIgnoreCase("help")){
				help = true;
				displayHelp();
			}
			System.out.println();
		}while(help);
		
		return action;
	}

	public void displayHelp(){
		System.out.println("Welcome to Spy Vs. Ninja, a taut psychological thrill ride packed into an easy to learn game!");
		System.out.println("Each level contains 9 rooms [R] dispersed evenly throughout. One of these rooms contains a document [D].");
		System.out.println("Your role as a spy [P] is simple: infiltrate the building and find the document, but be wary of ninjas [N].");
		System.out.println("You start with a gun and one bullet, which you can use to kill ninjas with. You may find various ");
		System.out.println("powerups in the building to help you. Invulnerability [I] prevents you from being killed for 5 turns, ");
		System.out.println("Bullet [B] will give you a extra bullet, and Radar [R] will show you the current location of the documents");
		System.out.println("Every turn, you may choose to look around, shoot in a given direction, or move. If you get too close to a ");
		System.out.println("ninja, you die. Die three times, and it's game over. Good luck!");
	}
	
	public void warning() {
		System.out.println("DANGER!!! Ninja has been seen.\n");
		
	}
	
	public void playerPos(int entityX, int entityY) {
	}

	public void ninjaPos(int i, int entityX, int entityY, boolean b) {
	}

	public boolean delay() {
		return false;
	}

	public void numOfNinjas(int numOfNinja) {		
	}

	/**
	 * This method is used to get user input for a save file name
	 * @return Name of the file to save to
	 */
	public String UserSave() {
		System.out.println("Please enter file name you would like: ");
		String result = userInput.nextLine();
		return result;
	}

	/**
	 * This method is used to get user input for a load file name
	 * @return Name of the file to load from
	 */
	public String userLoad() {
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
		
		if(fileName != null){
			System.out.println("Save Files:");
			for(int i = 0;i < fileName.length;i++){
				System.out.println(fileName[i].getName());
			}
		}else{
			System.out.println("No Saved files");
		}
		
		System.out.print("Please enter name of file to load: ");
		String result = userInput.nextLine();
		return result;
	}

	public void fileNotFound(String fileName) {
		System.out.println("File" + fileName + " not found");
		
	}

	public void playerKilled() {
		System.out.println("You just lost a Life.");
		
	}
}
