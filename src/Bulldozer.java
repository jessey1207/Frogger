import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bulldozer extends MovingObject {
	public Bulldozer(float x, float y, boolean direction) throws SlickException {
		/** set all properties of the bulldozer */
		setX(x);
		setY(y);
		setDirection(direction);
		setSpeed(SPEED);
		setImage(BULLDOZER_IMG);
		setWidth(App.IMAGE_HEIGHT);
	}
	private final Image BULLDOZER_IMG = new Image("assets/bulldozer.png");
	/** bulldozer speed as a float */
	private static final float SPEED = 0.05f;
	
	/** Determine if anything needs to be pushed by bulldozer.
	 * @return if something needs to be pushed.
	 */
	public BoundingBox pushZone() {
		// Push player if player is located to the right of bulldozer
		return new BoundingBox(getX()+getWidth(), getY()-App.COLLISIONHEIGHTMARGIN,
				getX()+getWidth()/2, getY()+App.COLLISIONHEIGHTMARGIN);
	}

}