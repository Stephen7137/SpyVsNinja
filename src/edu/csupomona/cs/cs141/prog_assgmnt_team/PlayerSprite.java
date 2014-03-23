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

import java.awt.Rectangle;

import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.EntityDirec;

/**
 * This class keeps track of the player sprite and what direction they
 * are face as well as draws them to the screen.
 */
public class PlayerSprite extends GUIEntity {
	
	static String fileName = "assets/playerSpriteSheet.png";
	
	static int frame = 4;
	
	int gridSpace = 64;
	
	private Rectangle self = new Rectangle();
	
	private Rectangle object = new Rectangle();
	
	/**
	 * This is the constructor that loads the sprite sheet.
	 */
	public PlayerSprite() {
		super(fileName, 0,0,64,64,11,523,frame);
	}
	
	/**
	 * This method changes the direction of the player depending
	 * on the user input.
	 * @param direction This is a enum that tells the direction the player
	 * is facing.
	 */
	public void movePlayer(EntityDirec direction){
		switch(direction){
		case NORTH:
			sprite = animatedSprite[0];
			break;
		case EAST:
			sprite = animatedSprite[1];
			break;
		case SOUTH:
			sprite = animatedSprite[2];
			break;
		case WEST:
			sprite = animatedSprite[3];
			break;
		default:
			break;
		}
	}
	
	/**
	 * This method resets the player sprite to spawn.
	 */
	public void resetSprite(){
		spriteX = 12;
		spriteY = 524;
	}
		
	/**
	 * This method tells the sprite where to move of the screen.
	 * Player moves acording to grid and is changed from number on 
	 * grid to number of pixels on screen.
	 * @param x location X on the grid.
	 * @param y location Y on the grid.
	 */
	public void setPos(int x, int y){
		spriteX = 12 + (double)x * 64;
		spriteY = 12 + (double)y * 64;
	}
}
