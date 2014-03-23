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
import java.awt.Image;

/**
 * This class keeps track of sprites location on the board
 * and drawns them to the screen.
 *
 */
public class Sprite {
	
	private Image spriteImage;
			
	public Sprite(Image image) {
		spriteImage = image;
	}
	
	public int getWidth() {
		return spriteImage.getWidth(null);
	}

	public int getHeight() {
		return spriteImage.getHeight(null);
	}
	
	public void drawSprite(Graphics g,int x,int y) {
		g.drawImage(spriteImage,x,y,null);
	}
}

