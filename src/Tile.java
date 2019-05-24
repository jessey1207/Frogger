import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * Class for each background tile that exists in the game.
 * @author Jessey Fung.
 */
public class Tile {
	
	// Define all types of background tiles as strings
	private final static String GRASS = "grass";
	private final static String TREE = "tree";
	
	// Each tile has their own image and position
	private final float x;
	private final float y;
	private Image image;
	
	// Keep track of lanes
	public static float safety_lane1 = 0;
	public static float safety_lane2 = 0;
	public static float goal_area = 0;
	private boolean isTree = false;
	
	public BoundingBox frame=null;

	public Tile(String type, float XPos, float YPos) throws SlickException {
		
		// Determine which image to assign
		String tileType = type.toLowerCase();
		this.image = new Image("assets/"+tileType+".png");
		
		if (tileType.equals(GRASS)) {
			// Grass means safety
			if (safety_lane1 == -1 && safety_lane1 != YPos) {
				safety_lane1 = YPos;
			} else {
				safety_lane2 = YPos;
			}
			
		} else if (tileType.equals(TREE)) {
			// Bigger tree lane means goal area
			isTree=true;
			if (goal_area==0 || YPos > goal_area) {
				goal_area = YPos;
			}
		}
		
		this.x = XPos;
		this.y = YPos;
		
		frame = new BoundingBox(this.x, this.y,
				this.x, this.y);
	}
	
	/** Determine if the tile is a tree.
	 * @return if tile is a tree.
	 */
	public boolean isTree() {
		return isTree;
	}
	
	/**
	 * @return the current image of tile.
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * @return the current X-position of tile.
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * @return the current Y-position of tile.
	 */
	public float getY() {
		return this.y;
	}
	
	/** Render the tile for the game background.
	 * @throws SlickException
	 */
	public void drawTile() throws SlickException {
		getImage().drawCentered(x, y);
	}
}
