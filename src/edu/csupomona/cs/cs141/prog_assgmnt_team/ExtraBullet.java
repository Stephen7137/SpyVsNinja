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
 * This class represents an extra bullet powerup. It gives the player an additional bullet
 * if it is stepped on.
 */
public class ExtraBullet extends PowerUp{
	private String name = "B";
	
	private int addBullet = 1;
	
	public String getName(){
		return name;
	}
	
	public int getBullet(){
		return addBullet;
	}
}
