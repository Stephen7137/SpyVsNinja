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

import java.awt.Graphics;

/**
 * This class keeps track of the location and draws the three power 
 * ups to the screen.
 */
public class PowerUpSprite extends GUIEntity  {

	static int frames = 3;
	
	static String fileName = "assets/powerUpSpriteSheet.png";
	
	/**
	 * This is the constructor that loads the power up sprite sheet.
	 */
	public PowerUpSprite() {
		super(fileName, 0, 0, 64, 64, 0, 0, frames);
	}
	
	public void drawInvuln(Graphics g, int x, int y){
		spriteX = x * 64 + 11;
		spriteY = y * 64 + 11;
		
		sprite = animatedSprite[1];
		drawEntity(g);
	}
	
	public void drawExtrAmmo(Graphics g, int x, int y){
		spriteX = x * 64 + 11;
		spriteY = y * 64 + 11;
		
		sprite = animatedSprite[2];
		drawEntity(g);
	}
	
	public void drawRadar(Graphics g, int x, int y){
		spriteX = x * 64 + 11;
		spriteY = y * 64 + 11;
		
		sprite = animatedSprite[0];
		drawEntity(g);
	}

}
