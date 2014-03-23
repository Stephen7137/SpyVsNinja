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

import java.util.Random;

import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.EntityDirec;

/**
 * This class represents a ninja in the game. These are the game's enemies.
 */
public class Ninja extends EntityMovement{
	
	String name = "N";
	
	EntityDirec direction;
	
	private int entityX;
	
	private int entityY;
	
	boolean alive;
	
	public Ninja(int x, int y){
		entityX = x;
		entityY = y;
	}
	
	public String getName(){
		return name;
	}
	
	public void killNinja(){
		alive = false;
	}
	
	public void resetNinja(){
		alive = true;
	}

	public boolean ninjLocation(int x, int y) {
		boolean correctNinja;
		
		if( entityX == x && entityY == y){
			correctNinja = true;
		}else{
			correctNinja = false;
		}
		return correctNinja;
	}

	/**
	 * This method determines ninja movement.
	 * @param grid Game board
	 * @param emptySpace The space the ninja is occupying
	 * @param hardmode Determines if the game is in hard mode
	 */
	public void ninjaMovement(Entity[][] grid, Entity emptySpace, boolean hardmode) {
		if(alive){
			boolean openSpace = false;
			openSpace = ninjaLook(grid);
			if(openSpace){
				boolean validPlace = false;
				if(hardmode){
					validPlace = hardmodeNinjaAI(grid,emptySpace);
				}
				if(!validPlace || !hardmode){
					normalNinjaAI(grid,emptySpace);
				}
			}
		}
	}
	
	/**
	 * This method is used to determine ninja movement on hard mode.
	 * The ninja moves after the player if they come close to each other.
	 * @param grid Game board
	 * @param emptySpace The space the ninja is occupying
	 * @return Will return true if the ninja can move there or false if it cannot
	 */
	private boolean hardmodeNinjaAI(Entity[][] grid,  Entity emptySpace){
		
		boolean follow = true;
		
		if (getGridEntity(grid, entityX + 2, entityY).equalsIgnoreCase("P") ||
				getGridEntity(grid, entityX + 3, entityY).equalsIgnoreCase("P")){
			direction = EntityDirec.EAST;
		}else if(getGridEntity(grid, entityX -2, entityY).equalsIgnoreCase("P") ||
				getGridEntity(grid, entityX -3, entityY).equalsIgnoreCase("P")){
			direction = EntityDirec.WEST;
		}else if(getGridEntity(grid, entityX, entityY + 2).equalsIgnoreCase("P") ||
				getGridEntity(grid, entityX, entityY + 3).equalsIgnoreCase("P")){
			direction = EntityDirec.NORTH;
		}else if(getGridEntity(grid, entityX, entityY - 2).equalsIgnoreCase("P") ||
				getGridEntity(grid, entityX, entityY - 3).equalsIgnoreCase("P")){
			direction = EntityDirec.SOUTH;
		}else{
			follow = false;
		}
		
		boolean validPlace = false;
		if(follow){
			String nextSpace = collisionEntity(grid, direction,	entityX, entityY);
			if (nextSpace.equals(" ")){
				move(grid, emptySpace, entityX, entityY);
				validPlace = true;
			}
		}
		return validPlace;
	}

	/**
	 * This method determines ninja movement when not on hard mode.
	 * The ninja movement is random.
	 * @param grid Game board
	 * @param emptySpace Space the ninja is occupying
	 */
	public void normalNinjaAI(Entity[][] grid, Entity emptySpace){
		Random move = new Random();
		
		boolean validPlace = false;
		
		while(!validPlace){
			int choice =  move.nextInt(4);
			
			switch(choice){
			case 0 : 
				direction = EntityDirec.NORTH;
				break;
			case 1:
				direction = EntityDirec.SOUTH;
				break;
			case 2 :
				direction = EntityDirec.EAST;
				break;
			case 3 :
				direction = EntityDirec.WEST;
				break;	
			}
		
			String nextSpace = collisionEntity(grid, direction,
					entityX, entityY);
			if (nextSpace.equals(" ")){
				move(grid, emptySpace,entityX, entityY);
				validPlace = true;
			}else {
				validPlace = false;
			}
		}
	}
		/**
		 * This method is used to check if the ninja has any open spaces surrounding it.
		 * @param grid Game board
		 * @return true if any spaces surrounding ninja are empty
		 */
	private boolean ninjaLook(Entity[][] grid){
		boolean openSpace = false;

		if(getGridEntity(grid, entityX-1, entityY).equalsIgnoreCase(" ")){
			openSpace = true;
		}
		if(getGridEntity(grid, entityX+1, entityY).equalsIgnoreCase(" ")){
			openSpace = true;
		}
		if(getGridEntity(grid,entityX, entityY-1).equalsIgnoreCase(" ")){
			openSpace = true;
		}
		if(getGridEntity(grid,entityX, entityY+1).equalsIgnoreCase(" ")){
			openSpace = true;
		}

		return openSpace;
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
	
	public boolean getNinjaStatus(){
		return alive;
	}
}


