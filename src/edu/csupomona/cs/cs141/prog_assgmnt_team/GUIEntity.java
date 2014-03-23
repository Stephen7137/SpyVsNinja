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
 * This class creates the sprite and saves the sprites location
 * on the screen.
 */
public abstract class GUIEntity {
	
	protected double spriteX;
	
	protected double spriteY;

	protected Sprite sprite;
	
	protected Sprite animatedSprite[];

	protected double velocityX;

	protected double velocityY;
	
	/**
	 * Constructor loads sprite form file and set the position of the sprite.
	 * @param fileName name of the file.
	 * @param startX Where X position of the sprite starts in sprite sheet.
	 * @param startY Where Y position of the sprite starts in sprite sheet.
	 * @param width Width of the sprite.
	 * @param hieght hieght of the sprite.
	 * @param x location X of the sprite on screen.
	 * @param y location Y of the sprite on screen.
	 */
	public GUIEntity(String fileName,int startX,int startY,int width,int hieght,int x,int y) {
		sprite = SpriteLoader.get().getSprite(fileName,startX,startY,width,hieght);
		spriteX = x;
		spriteY = y;
	}
	
	/**
	 * Constructor loads sprite form file and set the position of the 
	 * animated sprite.
	 * @param fileName name of the file.
	 * @param startX Where X position of the sprite starts in sprite sheet.
	 * @param startY Where Y position of the sprite starts in sprite sheet.
	 * @param width Width of the sprite.
	 * @param hieght hieght of the sprite.
	 * @param x location X of the sprite on screen.
	 * @param y location Y of the sprite on screen.
	 * @param frames how many frames a sprite is made of.
	 */
	public GUIEntity(String fileName,int startX,int startY,int width,int hieght,int x,int y, int frames) {
		
		animatedSprite = new Sprite[frames];
		
		for(int i = 0; i < frames; i++){
			animatedSprite[i] = SpriteLoader.get().getSprite(fileName,startX + width * i,startY,width, hieght);
		}
		
		sprite = animatedSprite[1];
		
		spriteX = x;
		spriteY = y;
	}
	
	int mode = 1;
	
	public void moveSprite(long delta) {
		
		spriteX += (delta * velocityX) / 1000;
		spriteY += (delta * velocityY) / 1000;
	}
	
	public void setXVelocity(double vX) {
		velocityX = vX;
	}

	public void setYVelocity(double vY) {
		velocityY = vY;
	}
	
	public double getXvelocity() {
		return velocityX;
	}

	public double getYvelocity() {
		return velocityY;
	}
	
	public void drawEntity(Graphics g) {
		sprite.drawSprite(g,(int) spriteX,(int) spriteY);
	}

	public int getSpriteX() {
		return (int) spriteX;
	}

	public int getSpriteY() {
		return (int) spriteY;
	}
	
}
