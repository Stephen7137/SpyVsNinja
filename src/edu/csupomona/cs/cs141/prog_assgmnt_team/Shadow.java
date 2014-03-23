/**
 * 
 */
package edu.csupomona.cs.cs141.prog_assgmnt_team;


/**
 * This class creates the animated shadow used in {@link GUI}.
 * It keeps track of the frame the shadow is at as well as the
 * location of where to be drawn.
 */
public class Shadow extends GUIEntity {

	static String fileName = "assets/shadowSheet.png";
	
	static int startX = 0;
	
	static int startY = 0;
	
	static int width = 64;
	
	static int hieght = 0;
	
	static int frames = 4;
	
	boolean visible;
	
	long lastFrameChagne;
	
	int frameNumber = 0;
	
	long frameDuration = 50;
	
	/**
	 * loads the sprites for the first time.
	 * @param x Location X of the sprite.
	 * @param y Location Y of the sprite.
	 */
	public Shadow(int x, int y) {
		super(fileName, startX, startY, width, hieght, x, y, frames);
	}
	
	/**
	 * Changes the sprite to show the tile below.
	 * @param delta time between displayed image.
	 * @return returns value if sprite has finished changing to full revealed.
	 */
	public boolean reveal(long delta){
		lastFrameChagne += delta;
		
		if(lastFrameChagne > frameDuration){
			lastFrameChagne = 0;
			
			frameNumber ++;
			
			if(frameNumber > animatedSprite.length - 1){
				frameNumber = 4;
				visible = true;
			}else{
				sprite = animatedSprite[frameNumber];
			}
		}
		
		return visible;
	}
	
	/**
	 * Changes sprite to hide the tile.
	 * @param delta time between displayed image.
	 * @return returns true when sprite is on frame 0.
	 */
	public boolean hide(long delta){
		lastFrameChagne += delta;
		
		if(lastFrameChagne > frameDuration){
			lastFrameChagne = 0;
			
			frameNumber--;
			
			if(frameNumber < 0){
				visible = false;
				frameNumber = 0;
			}
			
			sprite = animatedSprite[frameNumber];
		}
		
		return visible;
	}
}
