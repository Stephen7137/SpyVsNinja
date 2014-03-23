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
 * This class keeps track of all ninja to be drawn to the screen.
 *
 */
public class NinjaSprite extends GUIEntity  {
	
	static String fileName = "assets/ninjaSpriteSheet.png";
	
	int lastX = 0, lastY = 0;
	static int frame = 4;
	
	boolean alive;
	
	/**
	 * This constructor loads the ninja sprite sheet.
	 */
	public NinjaSprite() {
		super(fileName,0,0,64,64,11,11,frame);
	}
	
	/**
	 * This method take the ninja's last location and new
	 * location to draw the sprite facing the correct direction.
	 * @param x location of new X position.
	 * @param y location of new Y position.
	 * @param b if the ninja is alive or not.
	 */
	public void setPos(int x, int y, boolean b){
		
		int directX = lastX - x;
		int directY = lastY - y;
		
		if(directX == 0 && directY == -1){
			sprite = animatedSprite[2];
		}else if(directX == 1 && directY == 0){
			sprite = animatedSprite[3];
		}else if(directX == 0 && directY == 1){
			sprite = animatedSprite[0];
		}else if(directX == -1 && directY == 0){
			sprite = animatedSprite[1];
		}
		
		lastX = x;
		lastY = y;
		
		alive = b;
		
		spriteX = 12 + (double)x * 64;
		spriteY = 12 + (double)y * 64;
	}
	
	public boolean getNinjaStatus(){
		return alive;
	}
}

