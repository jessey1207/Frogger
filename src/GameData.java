import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/** 
 * Class to read CSV file for the game data.
 * Handles object information with array lists.
 * @author Jessey Fung.
 */
public class GameData {
	/** define all obstacles as string for identification from CSV file */
	private final static String BIKE = "bike";
	private final static String BULLDOZER = "bulldozer";
	private final static String RACECAR = "racecar";
	private final static String BUS = "bus";
	private final static String LOG = "log";
	private final static String LONGLOG = "longLog";
	private final static String TURTLE = "turtle";
	
	/** description of each array list position */
	private final int ID = 0;
	private final int X_POSITION = 1;
	private final int Y_POSITION = 2;
	private final int DIRECTION = 3;
	
	private final int TILE_DESCRIPTION = 3;
	private final int OBSTACLE_DESCRIPTION = 4;
	public static int bulldozerLane1 = -1;
	public static int bulldozerLane2 = -1;
	
	/** create array list for all objects */
	public static ArrayList<Tile> background = new ArrayList<Tile>();
	public static ArrayList<Bike> bikes = new ArrayList<Bike>();
	public static ArrayList<Bulldozer> bulldozers = new ArrayList<Bulldozer>();
	public static ArrayList<Racecar> racecars = new ArrayList<Racecar>();
	public static ArrayList<Bus> buses = new ArrayList<Bus>();
	public static ArrayList<Log> logs = new ArrayList<Log>();
	public static ArrayList<LongLog> longLogs = new ArrayList<LongLog>();
	public static ArrayList<Turtle> turtles = new ArrayList<Turtle>();
	public static ArrayList<Goal> goals = new ArrayList<Goal>();
	
	public GameData(String levelNumber) throws SlickException {
		// Read file and assign to corresponding arraylists
		try(Scanner scanner = new Scanner(new File(levelNumber))) {
			while(scanner.hasNext()) {
				String data[]=scanner.nextLine().split(",");
				float initX = Float.parseFloat(data[X_POSITION]);
				float initY = Float.parseFloat(data[Y_POSITION]);
				
				if (data.length == TILE_DESCRIPTION) {
					background.add(new Tile(data[ID], initX, initY));
					
				}  else if (data.length == OBSTACLE_DESCRIPTION) {
					
					boolean direction = Boolean.parseBoolean(data[DIRECTION]);
					createObstacle(data[ID], initX, initY, direction);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Find out where the goals are and create them
		for (int tileSpace=0; tileSpace<=App.SCREEN_WIDTH ; tileSpace+=App.IMAGE_HEIGHT) {
			int count =0;
			for (Tile tile: background) {
				if (tile.getY() == Tile.goal_area && tile.getX()==tileSpace) {
					count =1;
				}
			}
			if (count==0) {
				goals.add(new Goal(tileSpace));
				tileSpace += App.IMAGE_HEIGHT;
			}
		}
	}
	
	/** Create a game object.
	 * @param ID The game object to be created.
	 * @param XPos The X-position of the object
	 * @param YPos The Y-position of the object.
	 * @param direction The direction of the object.
	 * @throws SlickException.
	 */
	public void createObstacle(String ID, float XPos, float YPos, boolean direction) throws SlickException {
		MovingObject obstacle = null;
		
		if (ID.equals(BIKE)) {
			obstacle = new Bike(XPos, YPos, direction);
			bikes.add((Bike) obstacle);
			
		} else if (ID.equals(BULLDOZER)) {
			obstacle = new Bulldozer(XPos, YPos, direction);
			bulldozers.add((Bulldozer) obstacle);
			
			if (bulldozerLane1<0 && bulldozerLane1!=YPos) {
				bulldozerLane1 = (int) YPos;
			} else {
				bulldozerLane2 = (int) YPos;
			}	
			
		} else if (ID.equals(RACECAR)) {
			obstacle = new Racecar(XPos, YPos, direction);
			racecars.add((Racecar) obstacle);
			
		} else if (ID.equals(BUS)) {
			obstacle = new Bus(XPos, YPos, direction);
			buses.add((Bus) obstacle);
			
		} else if (ID.equals(LOG)) {
			obstacle = new Log(XPos, YPos, direction);
			logs.add((Log) obstacle);
			
		} else if (ID.equals(LONGLOG)) {
			obstacle = new LongLog(XPos, YPos, direction);
			longLogs.add((LongLog) obstacle);
			
		} else if (ID.equals(TURTLE)) {
			obstacle = new Turtle(XPos, YPos, direction);
			turtles.add((Turtle) obstacle);
		}
		return;
	}
	
	/** Render all the background tiles for the game.
	 * @param g The Slick graphics object, used for drawing.
	 * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException {
		for(Tile tile: background) {
			tile.drawTile();
		}
	}
	
	/** Empty all game object array lists.
	 */
	public void reset() {
		background.clear();
		goals.clear();
		bikes.clear();
		bulldozers.clear();
		racecars.clear();
		buses.clear();
		logs.clear();
		longLogs.clear();
		turtles.clear();
	}
}