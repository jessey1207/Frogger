import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bike extends MovingObject {
	public Bike(float x, float y, boolean direction) throws SlickException {
		/** set all properties of the bike */
		setX(x);
		setY(y);
		setDirection(direction);
		setSpeed(SPEED);
		setImage(BIKE_IMG);
		setWidth(App.IMAGE_HEIGHT);
	}
	private final Image BIKE_IMG = new Image("assets/bike.png");
	/** bike speed as a float */
	private static final float SPEED = 0.2f;
	/** left bound of the bike, in pixels */
	private final int LEFTBOUND = 24;
	/** right bound of the bike, in pixels */
	private final int RIGHTBOUND = 1000;
		
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		if (leftBoundHit()) {
			setDirection(RIGHT);
		} 
		if (rightBoundHit()) {
			setDirection(LEFT);
		}
	}

	/** Change direction if bike hits position 24.
	 * @return if Bike has hit position 24.
	 */
	private boolean leftBoundHit() {
		return ((getX()-LEFTBOUND)<1 && getDirection()==LEFT);
	}
	
	/** Change direction if bike hits position 1000.
	 * @return if Bike has hit position 1000.
	 */
	private boolean rightBoundHit() {
		return ((RIGHTBOUND-getX())<1 && getDirection()==RIGHT);
	}
}
