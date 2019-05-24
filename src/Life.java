import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * Class for each life shown at the bottom left of the game.
 * @author Jessey Fung.
 */
public class Life {
	public final static int INITIAL = 3;
	private float x;
	private float y;
	private final Image image;
	
	public Life(float x, float y) throws SlickException {
		this.x = x;
		this.y = y;
		image = new Image("assets/lives.png");
	}
	
	/** Render life.
	 */
	public void render() {
		image.drawCentered(this.x, this.y);
	}
}
