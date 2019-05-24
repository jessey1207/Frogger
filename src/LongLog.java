import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LongLog extends MovingObject {
	public LongLog (float x, float y, boolean direction) throws SlickException {
		// Set all properties of the long log
		setX(x);
		setY(y);
		setDirection(direction);
		setSpeed(SPEED);
		setImage(LONGLOG_IMG);
		setWidth(IMAGE_WIDTH);
	}
	private final Image LONGLOG_IMG = new Image("assets/LongLog.png");
	/** long log speed as float */
	private static final float SPEED = 0.07f;
	/** long log width, in pixels */
	private final int IMAGE_WIDTH = 228;
}
