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

import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.EntityDirec;

/**
 * This class represents movement on the grid.
 */
public abstract class EntityMovement implements Entity{
	
	static int moveX = 0;
		
	static int moveY = 0;
	
	public abstract void setPos(int x, int y);
	
	public abstract int getEntityX();
	
	public abstract int getEntityY();
	
	/**
	 * This method is used to check for collisions before moving an {@link Entity} to a space.
	 * It returns the name of whatever entity is in the space in question.
	 * @param grid Game board
	 * @param direction Direction entity is attempting to move in
	 * @param entityX Original X coordinate
	 * @param entityY Original Y coordinate
	 */
	public static String collisionEntity(Entity[][] grid, EntityDirec direction,
			int entityX, int entityY){
		
		switch(direction){
		case NORTH:
			moveX = 0;
			moveY = -1;
			break;
		case EAST: 
			moveX = 1;
			moveY = 0;
			break;
		case SOUTH: 
			moveX = 0;
			moveY = 1;
			break;
		case WEST: 
			moveX = -1;
			moveY = 0;
		}
		
		String name;
		
		name = getGridEntity(grid,entityX + moveX, entityY + moveY);
		
		return name;
	}
	
	
	/**
	 * This method is to move {@link Player} or {@link Ninja} on the {@link Grid}.
	 * Intended to move adjacent square but can switch any position in {@link Grid}.
	 * 
	 * @param grid The grid that game is on.
	 * @param emptySpace {@link Entity} of empty grid.
	 * @param entityX X location on board .
	 * @param entityY location on board.
	 */
	public void move(Entity[][] grid, Entity emptySpace,
			int entityX, int entityY){
		grid[entityX + moveX][entityY + moveY] = grid[entityX][entityY];
		grid[entityX][entityY] = emptySpace;
		
		entityX += moveX;
		entityY += moveY;
		
		setPos(entityX, entityY);
		
		moveX = 0;
		moveY = 0;
	}
	
	/**
	 * This method is used to check what entity presides in a certain space on the grid.
	 * @param grid Game board
	 * @param x X location on board
	 * @param y Y location on board
	 * @return Entity name
	 */
	public static String getGridEntity(Entity[][] grid, int x, int y){
		String name;
		
		if( x < 0 || y < 0 || x >= grid.length || y >= grid.length){
			name = "X";
		}else{
		 name = grid[x][y].getName();	
		}
		
		return name;
	}

	
}
