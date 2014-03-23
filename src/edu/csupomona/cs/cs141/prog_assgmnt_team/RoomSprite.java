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
 * This class keeps track of all of the rooms and document rooms
 * that need to be drawn to screen.
 */
public class RoomSprite extends GUIEntity {

	static String fileNameRoom = "assets/room.png";
	
	static String fileNameDoc = "assets/doc.png";
	
	Sprite docRoom;
	
	Sprite room;
	
	/**
	 * This constructor loads the sprite of the room and document room.
	 * @param x Location of X position of sprite.
	 * @param y Location of Y position of sprite.
	 */
	public RoomSprite(int x, int y) {
		super(fileNameRoom, 0, 0, 0, 0, x, y);
		
		docRoom = SpriteLoader.get().getSprite(fileNameDoc, 0, 0, 0, 0);
		
		room = sprite;
	}
	
	public void drawRoom(Graphics g){
		sprite = room;
		drawEntity(g);
	}
	
	public void drawDoc(Graphics g){
		sprite = docRoom;
		drawEntity(g);
	}


}
