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
 * This class represents the player. It is what the user will control when playing the game.
 */
public class Player extends EntityMovement{
	private String name = "P";
	
	private int lives;
	
	private int ammo;
	
	private int invulnTurn;
	
	private int entityX;
	
	private int entityY;
	
	public Player(){
		lives = 3;
		ammo = 1;
		invulnTurn = 0;
	}
	
	/**
	 * This method returns the {@link #name} of the player
	 * 
	 * @return The name of player {@link #name}
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method decreases the {@link #lives} of the {@link Player}.
	 * @return Number of lives.
	 */
	public int killPlayer(){		
		if(lives > 0){
			lives--;	
		}
		
		return lives;
	}
	
	/**
	 * This method decreases the amount of ammo the player currently holds.
	 */
	public void shoot(){
		
		if(ammo > 0){
			ammo--;
		}
	}
	
	/**
	 * This method counts down the turns left on invulnerability.
	 */
	public void invuln(){
		
		if(invulnTurn > 0){
			invulnTurn--;
		}
	}
	
	/**
	 * This method is used to add ammo the the players inventory.
	 * @param i Amount of ammo to add
	 */
	public void addAmmo(int i){
		ammo += i;
	}
	
	/**
	 * This method is used to add invulnerability to the player.
	 * @param i Turns of invulnerability to add
	 */
	public void addInvuln(int i){
		invulnTurn = i;
	}
	
	/**
	 * This method resets player statistics
	 */
	public void reset(){
		ammo = 1;
		invulnTurn = 0;
		lives = 3;
	}
	
	public int getLives(){
		return lives;
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public int getInvuln(){
		return invulnTurn;
	}

	public void setPos(int x, int y) {
		entityX = x;
		entityY = y;
	}

	public int getEntityX() {
		return entityX;
	}

	public int getEntityY() {
		return entityY;
	}
}
