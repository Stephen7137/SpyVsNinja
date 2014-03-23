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

/**
 * This class represents a room in the grid. The document is hidden in 1 of the 9 rooms in every level.
 */
public class Room implements Entity{
	private String name0 = "D";
	
	private String name1 = "E";
	
	private boolean hasDoc;
		
	public String getName(){
		String name;
		if(hasDoc){
			name = name0;
		}else{
			name = name1;
		}
		return name;
	}
	
	public void giveDoc(){
		hasDoc = true;
	}
}
