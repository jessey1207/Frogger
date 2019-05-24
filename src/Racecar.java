import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Racecar extends MovingObject {
	public Racecar(float x, float y, boolean direction) throws SlickException {
		/** set all properties of the race car */
		setX(x);
		setY(y);
		setDirection(direction);
		setSpeed(SPEED);
		setImage(RACECAR_IMG);
		setWidth(App.IMAGE_HEIGHT);
	}
	private final Image RACECAR_IMG = new Image("assets/racecar.png");
	/** race car speed as a float */
	private static final float SPEED = 0.5f;
}