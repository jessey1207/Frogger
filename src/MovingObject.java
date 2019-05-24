import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
                                                                                                                            
/** 
 * Class for all moving objects in the game.
 * Handles update, movement, and rendering.
 * Includes all getters and setters.
 * @author Jessey Fung.
 */
public class MovingObject {
	/** properties of each moving object */
	private float x;
	private float y;
	private boolean direction;
	private float speed;
	private Image image;
	private int width;
	
	/** the traveling direction of each moving object */
	public static final boolean RIGHT = true;
	public static final boolean LEFT = false;
	
	/** Update moving object for a frame.
	 * @param delta Time passed since last frame (milliseconds).
	 * @throws SlickException.
	 */
	public void update(int delta) throws SlickException {
		// Set default movement of object to move across lane
		float dx=0;
		dx = delta*speed;
		if (direction==LEFT) {
			dx = (-1)*dx;
		} 
		move(dx);
	}
	/** Update moving object that uses keyboard input for a frame.
	 * @param input.
	 * @param delta.
	 * @throws SlickException.
	 */
	public void update(Input input, int delta) throws SlickException {
	
	}
	
	/** Move object that requires X-axis only.
	 * @param dx Movement size across x-axis.
	 */
	public void move(float dx) {
		setX(getX()+dx);
		// Move in and out of screen smoothly
		if (getX()>getRightBound()) {
			setX(getLeftBound());
		}
		if (getX()<getLeftBound()) {
			setX(getRightBound());
		}
	}
	
	/** Move object that requires both X-axis and Y-axis.
	 * @param dx Movement size across x-axis.
	 * @param dy Movement size across y-axis.
	 * @throws SlickException.
	 */
	public void move(float dx, float dy) throws SlickException {
		
	}
	
	/** Render moving object to reflect it's current state.
	 */
	public void render() {
		getImage().drawCentered(x,y);
	}

	/**
	 * @param x X-position to set.
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * @return the current X-position of object.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param y Y-position to set.
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * @return the current Y-position of object.
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param image Image to set.
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	/**
	 * @return the current image of object.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param direction Direction to set.
	 */
	public void setDirection(boolean direction) {
		if (direction) {
			this.direction = RIGHT;
		} else {
		this.direction = LEFT; 
		}
	}
	
	/**
	 * @return the current direction of object.
	 */
	public boolean getDirection() {
		return direction;
	}

	/**
	 * @param speed Speed to set.
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * @return the current Speed of object.
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param width Image width to set.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * @return the current width of object.
	 */
	public int getWidth() {
		return width;	
	}
	
	/**
	 * @return the Right Boundary.
	 */
	public int getRightBound() {
		int rightBound = App.SCREEN_WIDTH+width;
		return rightBound;
	}
	
	/**
	 * @return the Left Boundary.
	 */
	public int getLeftBound() {
		int leftBound = 0-width;
		return leftBound;
	}
	
	/**
	 * @return the BoundingBox (collision frame) of object.
	 */
	public BoundingBox getBoundingBox() {
		return new BoundingBox(this.x+width/2,this.y-App.COLLISIONHEIGHTMARGIN,
				this.x-width/2, this.y+App.COLLISIONHEIGHTMARGIN);
	}
}

