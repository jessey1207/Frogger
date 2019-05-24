import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** 
 * Class for the goal in the game.
 * @author Jessey Fung.
 */
public class Goal {
	/** properties of the goal */
	private final float x;
	private final float y;
	private final Image goalFrog;
	/** goal width is twice the size of all images' height */
	private final int width = App.IMAGE_HEIGHT*2;
	
	private boolean reached;
	
	public Goal(float x) throws SlickException {
		goalFrog = new Image("assets/frog.png");
		this.x = x+App.IMAGE_HEIGHT/2;
		this.y = Tile.goal_area;
		reached=false;
	}
	
	/**
	 * @return the BoundingBox (collision frame) of the goal. 
	 */
	public BoundingBox getBoundingBox() {
		return new BoundingBox(this.x+width/2,this.y-App.COLLISIONHEIGHTMARGIN,
				this.x-width/2, this.y+App.COLLISIONHEIGHTMARGIN);
	}
	
	/** Render the goal to reflect it's current state.
	 */
	public void render() {
		goalFrog.drawCentered(this.x, this.y);
	}
	
	/** Fill goal.
	 */
	public void reached() {
		this.reached = true;
	}
	
	/** Determine if goal is full.
	 * @return if goal is full.
	 */
	public boolean isFull() {
		return this.reached;
	}
	
	/** Clear goal.
	 */
	public void reset() {
		this.reached = false;
	}
}
