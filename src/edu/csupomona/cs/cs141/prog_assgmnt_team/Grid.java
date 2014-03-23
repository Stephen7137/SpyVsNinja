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

import java.io.Serializable;
import java.util.Random;

import edu.csupomona.cs.cs141.prog_assgmnt_team.SpyVsNinja.EntityDirec;

/**
 *This class creates the grid that the keeps track of all
 *game entities.
 */
public class Grid implements Serializable{

	/**
	 * This field is the {@link Grid}'s grids where it keeps track
	 * of {@link Entity}. Initialized by {@link #setGrid(boolean)}, {@link #placeNinja()},
	 * {@link #placePower(boolean)}, and {@link #placeEmptySpace()}.
	 */
	private Entity[][] grid = new Entity[9][9];
	
	private String[][] coveredGrid = new String[9][9];
	
	private Random rand = new Random();
	
	/**
	 * This fields tells {@link Grid} how many {@link #ninja} are to be placed in
	 * {@link #grid} when starting the game.
	 */
	private final int numNinja = 6;
	
	boolean radarOn;
	
	public Ninja[] ninja = new Ninja[numNinja];
	
	public Player player = new Player();
	
	private Room empRoom = new Room();
	
	private Room docRoom = new Room();
	
	private Invulnerability invuln = new Invulnerability();
	
	private ExtraBullet addBullet = new ExtraBullet();
	
	private Radar radar = new Radar();
	
	private EmptySpace emptySpace = new EmptySpace();
	
	private int spawnX = 0, spawnY = 8;
	
	private int level = 1;
	
	/**
	 * This method sets up the {@link #grid}. First places the {@link #empRoom} and 
	 * {@link #docRoom} by treating {@link #grid} as a 3X3 square and places a room
	 * in the center of each square. Each room has a 3 in 10 chance to get the 
	 * {@link #docRoom}. The method then calls {@link #placeNinja()} and 
	 * {@link #placePower(boolean)}. finally placing {@link #player} and calling method
	 * {@link #placePower(boolean)}.
	 * @param hardmode 
	 */
	public void setGrid(boolean hardmode){
		
	
		for(int x = 0; x < grid.length; x++ ){
			for(int y = 0; y < grid[x].length; y++ ){
				grid[x][y] = null;
			}
		}
		
		boolean docPlaced = false;
		
		docRoom.giveDoc();
		
		while(!docPlaced){			
			for(int x = 0; x < (grid.length / 3); x++ ){
				for(int y = 0; y < (grid[x].length / 3); y++ ){
					int lottery = rand.nextInt(10);
					if( lottery < 3 && !docPlaced){
						grid[(x * 3) + 1][(y* 3) + 1] = docRoom;
						docPlaced = true;
					}else{
						grid[(x * 3) + 1][(y* 3) + 1] = empRoom;
					}
				}
			}
		}
		
		placeNinja();
		
		placePower(hardmode);
				
		grid[spawnX][spawnY] = player;
		player.setPos(spawnX,spawnY);
		
		placeEmptySpace();
		
	}
	
	/**
	 * This method fills {@link #grid} that still has a {@code null} with
	 * {@link #emptySpace}.
	 */
	private void placeEmptySpace(){
		for(int i = 0; i < grid.length; i++ ){
			for(int j = 0; j < grid[i].length; j++ ){
				if(grid[i][j] == null ){
					grid[i][j] = emptySpace; 
				}
			}
		}
	}
	
	/**
	 * This method places {@link #ninja} in {@link #grid}. {@link #ninja} are placed 
	 * randomly in {@link #grid} that is {@link #emptySpace}. {@link #ninja} are not 
	 * placed in safe zone, 3X3 area, near player spawn. Number of starting 
	 * {@link #ninja} are assigned to {@link Grid}.
	 */
	private void placeNinja(){
		boolean validPlace = false;
		for(int i = 0; i < numNinja; i++){
			while(!validPlace){
				int x = rand.nextInt(9);
				int y = rand.nextInt(9);
				
				ninja[i] = new Ninja(x,y);	
				
				if(grid[x][y] == null && !(x < 3 && y > 5) ){
					grid[x][y] = ninja[i];
					ninja[i].setPos(x, y);
					ninja[i].resetNinja();;
					validPlace = true;
				}else{
					validPlace = false;
				}
			}
			validPlace = false;
		}
	}
	
	/**
	 * Places power ups in {@link #grid}. {@link #invuln}, {@link #addBullet},
	 * and {@link #radar} place on the grid. Only one power up of each type is
	 * placed on the board. Power up is only placed in free space {@link #emptySpace}
	 * in the {@link #grid}.
	 */
	private void placePower(boolean hardmode){
		
		int numPowerUp = 1;
		
		if(hardmode){
			numPowerUp = 2;
		}
		
		for(int i = 0; i < numPowerUp ; i++){
			boolean validPlace = false;
		
			for( int j = 0; j < 3; j++){
				validPlace = false;
				while(!validPlace){
					int x = rand.nextInt(9);
					int y = rand.nextInt(9);
					
					if(grid[x][y] == null && j == 0  && !(x < 3 && y > 5) ){
						grid[x][y] = invuln;
						validPlace = true;
					}else if(grid[x][y] == null  && j == 1  && !(x < 3 && y > 5) ){
						grid[x][y] = addBullet;
						validPlace = true;
					}else if(grid[x][y] == null && j == 2  && !(x < 3 && y > 5) ){
						grid[x][y] = radar;
						validPlace = true;
					}else{
						validPlace = false;
					}
				}	
			}
		}
	}
	
	/**
	 * This method is used to cover the contents of the grid from the player.
	 */
	public void setCover(){
		int posX = player.getEntityX();
		int posY = player.getEntityY();
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				
				if(grid[i][j] == empRoom ){
					coveredGrid[i][j] = grid[i][j].getName();
				}else if(grid[i][j] == docRoom){
					if(radarOn){
						coveredGrid[i][j] = grid[i][j].getName();
					}else{
						coveredGrid[i][j] = empRoom.getName();
					}
				}else{
					coveredGrid[i][j] = "-";
				}
			}
		}
		
		coveredGrid[posX][posY] = player.getName();
		
		if((posX - 1) >= 0 && grid[(posX - 1)][posY] != docRoom){
			coveredGrid[(posX - 1)][posY] = grid[(posX - 1)][posY].getName();
		}
		if((posY - 1) >= 0 && grid[posX][posY - 1] != docRoom){
			coveredGrid[posX][posY - 1] = grid[posX][posY - 1].getName();
		}
		if((posX + 1) < coveredGrid.length && grid[(posX + 1)][posY] != docRoom){
			coveredGrid[posX + 1][posY] = grid[(posX + 1)][posY].getName();
		}
		if((posY + 1) < coveredGrid.length && grid[posX][posY + 1] != docRoom){
			coveredGrid[posX][posY + 1] = grid[posX][posY + 1].getName();
		}
		
	}
	
	/**
	 * This method is used to reveal cover at the specified location.
	 * @param posX X location
	 * @param posY Y location
	 */
	public void revealCover(int posX, int posY){
		if(posX >= 0 && posY >= 0 && posX < coveredGrid.length && posY < coveredGrid.length){
			if(grid[posX][posY] != docRoom){
				coveredGrid[posX][posY] = grid[posX][posY].getName();
			}
		}
	}
	
	/**
	 * This method returns the name of the {@link Entity} at a specified location. 
	 * Coordinates given are check to see if they are in {@link #grid}. If coordinates
	 * are out of bounds then function returns a {@code "X"}.
	 * 
	 * @param x Is the X coordinate of the {@link Entity}
	 * @param y Is the Y coordinate of the {@link Entity}
	 * @return Is the name of the {@link Entity}.
	 */
	public String getGridEntity(int x, int y){
		String name;
		
		if( x < 0 || y < 0 || x >= grid.length || y >= grid.length){
			name = "X";
		}else{
		 name = grid[x][y].getName();	
		}
		
		return name;
	}
	
	/**
	 * This method returns the value of the {@link #coveredGrid} to the {@link GameEngine} 
	 * @param x Is the X coordinate of the {@link #coveredGrid}
	 * @param y Is the Y coordinate of the {@link #coveredGrid}
	 * @return Is the name of the {@link #coveredGrid}.
	 */
	public String getCoveredGridEntity(int x, int y){
		return coveredGrid[x][y];
	}
	
	/**
	 * This method tells how long {@link #grid} is.
	 * @return The length as a integer.
	 */
	public int getGridLenght(){
		return grid.length;
	}
	
	/**
	 * This method is used to check for collision with the player when the player moves.
	 * @param direction Direction of player movement
	 * @return Name of entity in the space
	 */
	public String playerCollision(EntityDirec direction){
		return Player.collisionEntity(grid, direction, player.getEntityX(), 
				player.getEntityY());
	}
	
	/**
	 * This method moves the {@link #player} on {@link #grid}
	 */
	public void movePlayer(){
		player.move(grid, emptySpace, player.getEntityX(), 
				player.getEntityY());
	}
	
	/**
	 * This method moves the {@link #ninja} on {@link #grid}
	 * @param hardmode Determines if the game is in hard mode
	 * @param level Determines what level the player is on
	 */
	public void moveNinja(boolean hardmode, int level){
		for(int i = 0; i < ninja.length; i++){
			if( i < level){
				ninja[i].ninjaMovement(grid, emptySpace, hardmode);
			}else{
				ninja[i].ninjaMovement(grid, emptySpace, false);
			}	
		}
	}
		
	/**
	 * This method kills {@link #player} and resets there position to spawn.
	 * @param posX Player X location
	 * @param posY Player Y location
	 */
	public void killPlayer(int posX, int posY){
		player.killPlayer();
		grid[posX][posY] = grid[spawnX][spawnY];
		player.setPos(spawnX, spawnY);
		//coveredGrid[posX][posY] = "-";
		grid[spawnX][spawnY] = player;
		
	}
	
	/**
	 * This method kills the {@link #ninja} and replace it with {@link #emptySpace}
	 * in {@link #grid}.
	 * 
	 * @param x Is the X coordinate of the {@link #ninja}.
	 * @param y Is the Y coordinate of the {@link #ninja}.
	 */
	public void killNinja(int x, int y){
		grid[x][y] = emptySpace;
		
		for(int i = 0; i < ninja.length; i++){
			if(ninja[i].ninjLocation(x,y)){
				ninja[i].killNinja();
			}
		}
	}
	
	public void addBullet(){
		player.addAmmo(addBullet.getBullet());
	}
	
	public void addInvuln(){
		player.addInvuln(invuln.getInvuln());
	}
	
	public void radar(){
		radarOn = true;
	}

	public void resetLevel() {
		level = 1;
		player.reset();
		radarOn = false;
	}
	
	public void nextLevel(){
		level++;
		player.reset();
		radarOn = false;
	}
	
	public int numOfNinja(){
		return numNinja;
	}

	public int getLevel() {
		return level;
	}
}

