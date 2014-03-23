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
import java.awt.Graphics2D;

/**
 * This method keeps track of all of menu sprites used during the 
 * main menu and pause menu.
 */
public class Menu extends GUIEntity  {

	static String fileNameBackground = "assets/menuBackGround.png";
	
	static String fileNameMainMenu = "assets/menu.png";
	
	static String fileNamePauseMenu = "assets/pauseMenu.png";
	
	static String fileNameDebugOff = "assets/debugOff.png";
	
	static String fileNameDebugOn = "assets/debugOn.png";
	
	static String fileNameHardMode = "assets/hardmode.png";
	
	static String fileNameNormalMode = "assets/normalmode.png";
	
	static String fileNameCursor = "assets/cursor.png";
	
	static String fileNameHelp = "assets/help.png";
	
	static String fileNameReturn = "assets/return.png";
	
	Sprite background, mainMenu, pauseMenu, debugOff;
	
	Sprite debugOn, hardMode, normalMode, cursor, help, returnSprite;
	
	/**
	 * This constructor loads the sprites to be used for the menu
	 */
	public Menu() {
		super(fileNameBackground, 0, 0, 0, 0, 0, 0);
		
		background = sprite;
		mainMenu = SpriteLoader.get().getSprite(fileNameMainMenu,0,0,0,0);
		pauseMenu = SpriteLoader.get().getSprite(fileNamePauseMenu,0,0,0,0);
		debugOff = SpriteLoader.get().getSprite(fileNameDebugOff,0,0,0,0);
		debugOn = SpriteLoader.get().getSprite(fileNameDebugOn,0,0,0,0);
		hardMode = SpriteLoader.get().getSprite(fileNameHardMode,0,0,0,0);
		normalMode = SpriteLoader.get().getSprite(fileNameNormalMode,0,0,0,0);		
		cursor = SpriteLoader.get().getSprite(fileNameCursor,0,0,0,0);
		help = SpriteLoader.get().getSprite(fileNameHelp,0,0,0,0);
		returnSprite = SpriteLoader.get().getSprite(fileNameReturn,0,0,0,0);
	}
	
	public void drawMenuBackground(Graphics g){
		spriteX = 0;
		spriteY = 0;
		
		sprite = background;
		drawEntity(g);
	}
	
	public void drawMainMenu(Graphics g, int cursorX){
	
		spriteX = 300;
		spriteY = 250;
		
		sprite = mainMenu;
		drawEntity(g);
		
		drawCursor(g, cursorX);
	}
	
	public void drawStartGame(Graphics g, int cursorX){
		
		spriteX = 300;
		spriteY = 250;
		
		sprite = normalMode;
		drawEntity(g);
		
		spriteX = 300;
		spriteY = 290;
		
		sprite = hardMode;
		drawEntity(g);
		
		spriteX = 300;
		spriteY = 330;
		
		sprite = returnSprite;
		drawEntity(g);
		
		drawCursor(g, cursorX);
	}
	
	public void drawMainOption(Graphics g, int cursorX, boolean debug){
		
		spriteX = 300;
		spriteY = 250;
		
		if(debug){
			sprite = debugOn;
		}else{
			sprite = debugOff;
		}
		
		drawEntity(g);
		
		spriteX = 300;
		spriteY = 290;
		
		sprite = returnSprite;
		drawEntity(g);
		
		drawCursor(g, cursorX);
	}

	public void drawHelp(Graphics g){
		spriteX = 0;
		spriteY = 0;
		
		sprite = help;
		drawEntity(g);
		
		spriteX = 100;
		spriteY = 525;
		
		sprite = returnSprite;
		drawEntity(g);
				
		sprite = cursor;
		drawEntity(g);
	}
	
	
	public void drawCursor(Graphics g, int cursorX){
		spriteX = 300;
		spriteY = 250 + (cursorX * 40);
		
		sprite = cursor;
		drawEntity(g);
	}

	public void drawPauseOption(Graphics2D g, int cursorX,
			boolean debug, boolean hard) {
		spriteX = 300;
		spriteY = 250;
		
		if(debug){
			sprite = debugOn;
		}else{
			sprite = debugOff;
		}
		
		drawEntity(g);
		
		spriteX = 300;
		spriteY = 290;
		
		if(hard){
			sprite = hardMode;
		}else{
			sprite = normalMode;
		}
		
		drawEntity(g);
		
		spriteX = 300;
		spriteY = 330;
		
		sprite = returnSprite;
		drawEntity(g);
		
		drawCursor(g, cursorX);
		
	}

	public void drawPauseMenu(Graphics2D g, int cursorX) {
		spriteX = 300;
		spriteY = 250;
		
		sprite = pauseMenu;
		drawEntity(g);
		
		drawCursor(g, cursorX);
		
	}

}
