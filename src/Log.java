import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Log extends MovingObject {
	public Log(float x, float y, boolean direction) throws SlickException {
		/** set all properties of the log */
		setX(x);
		setY(y);
		setDirection(direction);
		setSpeed(SPEED);
		setImage(LOG_IMG);
		setWidth(IMAGE_WIDTH);
	}
	private final Image LOG_IMG = new Image("assets/log.png");
	/** log speed as a float */
	private static final float SPEED = 0.1f;
	/** log width, in pixels */
	private final int IMAGE_WIDTH = 132;
}
