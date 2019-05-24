import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bus extends MovingObject {
	public Bus(float x, float y, boolean direction) throws SlickException {
		/** set all properties of the bus */
		setX(x);
		setY(y);
		setDirection(direction);
		setSpeed(SPEED);
		setImage(BUS_IMG);
		setWidth(App.IMAGE_HEIGHT);
	}
	private final Image BUS_IMG = new Image("assets/bus.png");
	/** bus speed as a float */
	private static final float SPEED = 0.15f;
}