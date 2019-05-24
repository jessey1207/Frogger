import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Turtle extends MovingObject {
	public Turtle(float x, float y, boolean direction) throws SlickException {
		/** set all properties of the turtle */
		setX(x);
		setY(y);
		setDirection(direction);
		setSpeed(SPEED);
		setImage(TURTLE_IMG);
		setWidth(IMAGE_WIDTH);
	}
	private final Image TURTLE_IMG = new Image("assets/turtles.png");
	/** turtle speed as a float */
	private static final float SPEED = 0.085f;
	/** turtle width, in pixels */
	private final int IMAGE_WIDTH = 144;
	/** time under water, in seconds */
	private final int SINK_DURATION = 2;
	/** time of first sink, in seconds */
	private final int FIRST_SINK = 7;
	/** time between each sink, in seconds */
	private final int SINK_CYCLE = 9;
	
	// Timers and turtle condition
	private boolean isUnderwater = false;
	private int currentTime;
	private int sinkTime;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(int delta) throws SlickException {
		super.update(delta);
		 
		currentTime = World.time/1000;
		
		// Sink first at 7 seconds, then every 9 seconds
		if (currentTime == FIRST_SINK || 
				(currentTime-FIRST_SINK)%SINK_CYCLE==0) {
			sink();
			sinkTime = currentTime;
		}
		
		checkResurface();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render() {
		if (!isUnderwater() ) {
			super.render();
		}
	}
	
	/** Sink turtle.
	 */
	private void sink() {
		isUnderwater = true;
	}
	
	/** Check if turtle should be under water.
	 */
	private void checkResurface() {
		if (currentTime == (sinkTime+SINK_DURATION)) {
			isUnderwater = false;
		}
	}
	
	/** Determine if turtle is under water.
	 * @return if turtle is under water.
	 */
	public boolean isUnderwater() {
		return isUnderwater;
	}
}