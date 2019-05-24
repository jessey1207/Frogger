import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ExtraLife extends MovingObject {
	private final Image EXTRALIFE_IMG = new Image("assets/extralife.png");
	
	// Only appears on logs for 14 seconds
	/** logs as numbers, for randomizing */
	private final int SHORTLOG = 1;
	private final int LONGLOG = 2;
	/** on-screen duration, in seconds */
	private final int SHOW_DURATION = 14;
	
	// Randomly picks a log from a random log array
	private int randomLogArray;
	private int randomLog;
	private MovingObject logChosen;
	
	// Timers and movements
	private int currentTime;
	private int timeCreated;
	private int generationTime;
	private int showTime;
	private int movementTimer = 2;
	private int distance=0;
	private boolean isShown = false;
	private boolean right = true;
	private boolean timesUp = false;
	private MovingObject attached = null;
	
	/**
	 * Constructor to create an Extra Life. 
	 * Sets all properties of the Extra Life.
	 * Generates number from random as time in seconds before appearing.
	 * Picks the type of log to appear on from random.
	 * Picks which log of that type to appear on from random.
	 * @throws SlickException
	 */
	public ExtraLife() throws SlickException {
		setImage(EXTRALIFE_IMG);
		setWidth(App.IMAGE_HEIGHT);
		timeCreated = World.time/1000;
		
		Random random = new Random();
		generationTime = random.nextInt(11)+25;
		
		randomLogArray = random.nextInt(2) + 1;
		
		if (randomLogArray == SHORTLOG) {
			randomLog = random.nextInt((GameData.logs.size()));
			logChosen = GameData.logs.get(randomLog);

		} else if (randomLogArray == LONGLOG) {
			randomLog = random.nextInt((GameData.longLogs.size()));
			logChosen = GameData.longLogs.get(randomLog);
		}	
	}
	
	@Override
	public void update(int delta) throws SlickException {
		float dx=0;
		currentTime = World.time/1000;
		
		// Appears after every randomly selected time from World
		if (currentTime == timeCreated+generationTime) {
			isShown = true;
			showTime = currentTime;
		}
		
		checkTimer();
		if (isShown) {
			attach(logChosen);
			setX(logChosen.getX()+distance);
			setY(logChosen.getY());
			
			// Move right if possible, otherwise continuously move left
			if ((getX()+getWidth())>(logChosen.getX()+logChosen.getWidth()/2)) {
				right = false;
			}
			if ((getX()-getWidth())<(logChosen.getX()-logChosen.getWidth()/2)) {
				right=true;
			}
			
			// Move around log every 2 seconds
			if (currentTime==(showTime+movementTimer) ) {
				if (right==true) {
					moveRight();
				} else if (right==false) {
					moveLeft();
				}
			}
		}
		
		// Get speed and direction from log attached
		if (attached!=null) {
			boolean right = attached.getDirection();
			
			if (right) {
				dx = delta* attached.getSpeed();
			} else {
				dx = (-1)*delta* attached.getSpeed();
			}
		}
		move(dx);
	}
	
	@Override
	public void move(float dx) {
		setX(getX()+dx);
	}
	
	/** Attach to a log.
	 * @param Obstacle to be attached to
	 */
	void attach(MovingObject obstacle) {
		attached = obstacle;
	}
	
	/** Move an image size to the right.
	 */
	private void moveRight() {
		distance += getWidth();
		movementTimer +=2;
	}
	
	/** Move an image size to the left.
	 */
	private void moveLeft() {
		distance -= getWidth();
		movementTimer +=2;
	}

	/** Check if render time is up.
	 */
	public void	checkTimer() {
		if (currentTime==showTime+SHOW_DURATION && isShown) {
			isShown = false;
			timesUp = true;
		}
	}
	
	 /** Determine if object should be shown.
	  * @return if object should be shown.
	  */
	public boolean isTimeUp() {
		return timesUp;
	}
	
	@Override
	public void render() {
		if (isShown) {
			super.render();
		}
	}
}