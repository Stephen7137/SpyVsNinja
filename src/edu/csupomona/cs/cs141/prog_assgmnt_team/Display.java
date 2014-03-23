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
 * This class keeps track of all the sprites that make up the dispaly
 * such as lives,ammo,invuln,and if a room is empty or not. It also keeps
 * track of sprites during the next level screen.
 */
public class Display extends GUIEntity {

	static String fileNameNumber = "assets/numbers.png";
	
	static String fileNameAlert = "assets/warning.png";
	
	static String fileNameLevel = "assets/nextLevel.png";
	
	static String fileGameOver = "assets/GameOver.png";
	
	static String fileGameOverWin = "assets/GameOverWin.png";
	
	static String backgroundFile = "assets/svnbackground.png";
	
	static String fileNameDead = "assets/dead.png";
	
	static int frames = 10;
	
	static int width = 16, hieght = 28;
	
	static int startX = 606, startY = 20;
	
	Sprite alert[] = new Sprite[2];
	
	Sprite nextLevel,gameOver,gameOverWin, background, dead;
	
	/**
	 * Constructor loads all of the sprites need for the display.
	 */
	public Display() {
		super(fileNameNumber, 0, 0, width, hieght, startX, startY, frames);
		alert[0] = SpriteLoader.get().getSprite(fileNameAlert,0,0,122,66);
		alert[1] = SpriteLoader.get().getSprite(fileNameAlert,0,66,122,66);
		nextLevel = SpriteLoader.get().getSprite(fileNameLevel,0,0,0,0);
		gameOver = SpriteLoader.get().getSprite(fileGameOver,0,0,0,0);
		gameOverWin = SpriteLoader.get().getSprite(fileGameOverWin,0,0,0,0);
		background = SpriteLoader.get().getSprite(backgroundFile,0,0,0,0);
		dead = SpriteLoader.get().getSprite(fileNameDead,0,0,0,0);
	}
	
	public void drawBackground(Graphics g){
		spriteX = 0;
		spriteY = 0;
				
		sprite = background;
		
		drawEntity(g);
		
	}
	
	public void drawDisplay(Graphics g, int lives, int ammo, int invuln, int level){
		
		drawLevel(g,level);
		drawAmmo(g,ammo);
		drawLives(g,lives);
		drawInvuln(g,invuln);
	}
	
	/**
	 * Drawn the level sprites to tell the player what level 
	 * they are on. has three sprites of show the numbers.
	 * @param g where the sprite is to be drawn to.
	 * @param level the level the player is on.
	 */
	public void drawLevel(Graphics g, int level){
		
		spriteX = 702;
		spriteY = 77;
		
		sprite = animatedSprite[level%10];
		drawEntity(g);
		
		spriteX = 686;
		sprite = animatedSprite[(level/10)%10];
		drawEntity(g);
		
		spriteX = 670;
		sprite = animatedSprite[(level/100)%10];
		drawEntity(g);
	}
	
public void drawAmmo(Graphics g, int ammo){
		
		spriteX = 733;
		spriteY = 217;
		
		sprite = animatedSprite[ammo%10];
		drawEntity(g);
	}

	public void drawLives(Graphics g, int lives){
	
		spriteX = 685;
		spriteY = 161;
		
		sprite = animatedSprite[lives%10];
		drawEntity(g);
	}	
	
	public void drawInvuln(Graphics g, int invuln){
		
		spriteX = 684;
		spriteY = 329;
		
		sprite = animatedSprite[invuln%10];
		drawEntity(g);
	}
	
	public void drawNinjaWarning(Graphics g){
		spriteX = 633;
		spriteY = 380;
		
		sprite = alert[0];
		drawEntity(g);
	}
	
	public void drawEmptyAlert(Graphics g){
		spriteX = 633;
		spriteY = 463;
		
		sprite = alert[1];
		drawEntity(g);
	}
	
	public void drawNextLevel(Graphics g, int level){
		spriteX = 240;
		spriteY = 240;
		
		sprite = nextLevel;
		drawEntity(g);
		
		spriteY = 239;
		spriteX = 376;
		sprite = animatedSprite[level%10];
		drawEntity(g);
		
		spriteX = 360;
		sprite = animatedSprite[(level/10)%10];
		drawEntity(g);
		
		spriteX = 344;
		sprite = animatedSprite[(level/100)%10];
		drawEntity(g);
	}
	
	public void drawGameOver(Graphics g, int level, boolean game){
		
		drawNextLevel(g, level);
		
		spriteY = 300;
		spriteX = 225;
		
		if(game){
			sprite = gameOverWin;
		}else{
			sprite = gameOver;
		}
		
		drawEntity(g);
	}

	public void drawDead(Graphics g) {
		spriteX = 200;
		spriteY = 320;
		
		sprite = dead;
		drawEntity(g);
	}
}
