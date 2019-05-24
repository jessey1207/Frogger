import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Frog extends MovingObject {
	public Frog(float x, float y) throws SlickException {
		/** set all properties of the frog */
		setX(x);
		setY(y);
		setImage(FROG_IMG);
	}
	private final Image FROG_IMG = new Image("assets/frog.png");
	
	private MovingObject attached = null;
	
	@Override
	public void update(Input input, int delta) throws SlickException {
		float dx=0, dy=0;
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
		
		// If attached to an obstacle, get speed and direction
		if (attached!=null) {
			boolean right = attached.getDirection();
			if (right) {
				dx = delta* attached.getSpeed();
			} else {
				dx = (-1)*delta* attached.getSpeed();
			}
		}
		
		// Player cannot move right or left if being pushed by a bulldozer
		if ((getY() == GameData.bulldozerLane1 || getY()== GameData.bulldozerLane2)
				&& attached!=null) {
			
			if (input.isKeyPressed(Input.KEY_UP)) {
				dy -= App.IMAGE_HEIGHT;
			}
			if (input.isKeyPressed(Input.KEY_DOWN)) {	
				dy +=App.IMAGE_HEIGHT;
			}
			
		} else {
			if (input.isKeyPressed(Input.KEY_UP)) {
				dy -= App.IMAGE_HEIGHT;
			}
			if (input.isKeyPressed(Input.KEY_DOWN)) {	
				dy +=App.IMAGE_HEIGHT;
			}
			if (input.isKeyPressed(Input.KEY_RIGHT)) {
				dx += App.IMAGE_HEIGHT;
			}
			if (input.isKeyPressed(Input.KEY_LEFT)) {
				dx -= App.IMAGE_HEIGHT;
			}
		}
		move(dx,dy);
	}

	/** Move the frog.
	 * 
	 * @param dx Movement size across X-axis.
	 * @param dy Movement size across Y-axis.
	 * @throws SlickException .
	 */
	public void move(float dx, float dy) throws SlickException  {
		setX(getX()+dx);
	    setY(getY()+dy);
	    
	    // Cannot touch bulldozer object
	    for (Bulldozer bulldozer : GameData.bulldozers) {
			if (bulldozer.getBoundingBox().intersects(this.getBoundingBox())) {
				setX(getX()-dx);
			    setY(getY()-dy); 
			}
		}
       
	    // Player can be pushed off the screen by bulldozer and will lose a life
        if ((int)getY()!=GameData.bulldozerLane1 && (int)getY() != GameData.bulldozerLane2) {
			if (getX() < 0 ) {
				setX(0);
			}
			if (getX() > App.SCREEN_WIDTH) {
				setX(App.SCREEN_WIDTH);
			}
			if (getY() < 0) {
				setY(0);
			}
			if (getY() > App.SCREEN_HEIGHT) {
				setY(App.SCREEN_HEIGHT);
			}
        } else {
        	if (getX()>(App.SCREEN_WIDTH+App.IMAGE_HEIGHT/2) || (getX()<0-App.IMAGE_HEIGHT/2)) {
        		World.removeLife();
        	}
        }
    }
	
	/** Attach to another object.
	 * @param Obstacle to be attached to.
	 */
	void attach(MovingObject obstacle) {
		attached = obstacle;
	}
}