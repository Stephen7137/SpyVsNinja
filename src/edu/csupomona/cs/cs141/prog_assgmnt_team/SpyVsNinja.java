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

import java.util.Scanner;

/**
 * This class is used to run the game Spy Vs. Ninja.
 * Hope you enjoy!
 */
public class SpyVsNinja {
	
	
	/**
	 * This enum allows for easy reference to the direction of the player.
	 * 
	 * @author Stephen
	 */
	public enum EntityDirec{NORTH,SOUTH,EAST,WEST};
	
	/**
	 * This enum allows for easy access to possible player action for the user.
	 * 
	 * @author Stephen
	 */
	public enum playerAct{LOOK,SHOOT,MOVE,SAVE,LOAD,DEBUG,WAIT,HARD, EXIT, START, RETURN};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		Scanner input = new Scanner(System.in);
		
		System.out.print("Run Gui or Console: ");
		String buffer = input.nextLine();
		
		System.out.println();
		
		GameEngine gameEngine = new GameEngine();
		
		if(buffer.equalsIgnoreCase("GUI")){
			gameEngine.startGame(new GUI());
		}else if(buffer.equalsIgnoreCase("Console")){
			gameEngine.startGame(new UserInterface());
		}
		
		input.close();

		System.exit(0);
	}

}
