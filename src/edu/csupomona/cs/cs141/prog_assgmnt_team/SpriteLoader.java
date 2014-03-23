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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * This Class Loads sprite in to graphics and stores them to be used
 * when drawn to the screen
 */
public class SpriteLoader {

	private static SpriteLoader single = new SpriteLoader();
	
	public static SpriteLoader get() {
		return single;
	}
	
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	private HashMap<String,BufferedImage> sourceImageStorage = new HashMap<String, BufferedImage>();
	
	/**
	 * loads sprite to graphics and then stores sprite sheet in {@link #sourceImageStorage}
	 * and the sprite to be drawn in {@link #sprites}.
	 * @param fileName the name of the file.
	 * @param x Location of the X coordinate.
	 * @param y Location of the Y coordinate.
	 * @param width The sprites width.
	 * @param height The sprites height.
	 * @return Sprite that can be drawn
	 */
	public Sprite getSprite(String fileName,int x, int y, int width,int height) {

		boolean saved = false;
		
		if (sprites.get(fileName + x + y) != null) {
			return (Sprite) sprites.get(fileName + x + y);
		}
		
		BufferedImage sourceImage = null;
		
		if (sourceImageStorage.get(fileName) != null){
			sourceImage = sourceImageStorage.get(fileName);
			saved = true;
		}else{
			try {
				
				URL url = this.getClass().getClassLoader().getResource(fileName);
				
				if (url == null) {
					fail("Can't find fileName: " + fileName);
				}
				
				sourceImage = ImageIO.read(url);
			} catch (IOException e) {
				fail("Failed to load: " + fileName);
			}
		}
		
		if(width == 0){
			width = sourceImage.getWidth();
		}
		
		if( height == 0){
			height = sourceImage.getHeight();
		}
		
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(width,height,Transparency.BITMASK);
			
		image.getGraphics().drawImage(sourceImage,0,0,width,height,x,y,x + width,y + height,null);
		
		if(!saved){
			sourceImageStorage.put(fileName, sourceImage);
		}
		
		Sprite sprite = new Sprite(image);
		sprites.put(fileName + x + y, sprite);
		
		return sprite;
	}

	private void fail(String message) {
		System.err.println(message);
		System.exit(0);
	}
}
