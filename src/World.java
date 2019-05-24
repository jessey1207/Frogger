import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
/**
 * Class for game level.
 * Initializes, updates, and renders game level.
 * @author Jessey Fung.
 */
public class World {
	// Create
	private static Frog player;
	private static ExtraLife extraLife;

	// Set player constants
	private final static float PLAYER_X = 512;
	private final static float PLAYER_Y = 720;
	private final static int START_LEVEL = 0;
	private final static int ALL_FILLED = 5;
	private int level = START_LEVEL;

	// Lives and extra life
	private static ArrayList<Life> lives = new ArrayList<Life>();
	private static int livesRemaining;

	private GameData game;
	public static int time;
	private int fullGoals=0;
	private boolean safe = true;

	public World() throws SlickException {
		startGame();
	}
	
	/** Update level state for a frame.
	 * @param input Current input (keyboard state).
	 * @param delta Time passed since last frame (milliseconds).
	 * @throws SlickException.
	 */
	public void update(Input input, int delta) throws SlickException { 
		time += delta;
		
		// Check if player is in water
		if (player.getY() < Tile.safety_lane2) {
			checkRide();
			checkGoal();
			if (!safe) {
				removeLife();
			} 
		} 
		else {
			player.attach(null);
		}

		// Update all game objects
		updateObstacles(delta);
		player.update(input, delta);
		extraLife.update(delta);
		
		// Check extra life 
		if (extraLife.isTimeUp()) {
			generateExtraLife();
		}
		if (player.getBoundingBox().intersects(extraLife.getBoundingBox())) {
			generateExtraLife();
			livesRemaining+=1;
			lives.add(new Life(24+(livesRemaining-1)*32, 744));
		}
		if (lives.isEmpty()) {
			System.exit(0);;
		}
		
		// Level ends when all 5 goals are full
		checkEnd();
	}
	
	/** Render game level to reflect the current state of the level.
	 * @param g The Slick graphics object, used for drawing.
	 * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException {
		// Draw all of the objects in the game
		game.render(g);
		renderAll(g);
	}
	
	/** Read game data from file and start the game.
	 * Initialize goals and set the level.
	 * @throws SlickException
	 */
	public void startGame() throws SlickException {
		// Read game data
		game = new GameData(level+".lvl");
		
		// Make sure all goals are empty
		for (Goal goal : GameData.goals) {
			goal.reset();
		}
		fullGoals=0;
		
		// Start at level 0
		if (level == START_LEVEL) {
			initialiseLives();
		}

		resetGame();
		generateExtraLife();
	}
	
	/** Create the Extra Life object.
	 * @throws SlickException.
	 */
	public void generateExtraLife() throws SlickException {
		extraLife = new ExtraLife();
	}
	
	/** Initialize number of lives.
	 * @throws SlickException
	 */
	public void initialiseLives() throws SlickException {
		// Start with 3 lives, located same distance apart
		livesRemaining = Life.INITIAL;
		for(int nLife=0; nLife<Life.INITIAL; nLife++) {
			lives.add(new Life(24+nLife*32, 744));
		}
	}
	
	/** Updates all the moving objects in the game.
	 * @param input Current input (keyboard state).
	 * @param delta Time passed since last frame (milliseconds).
	 * @throws SlickException.
	 */
	public void updateObstacles(int delta) throws SlickException {
		for (Bus bus : GameData.buses) {
			bus.update(delta);
			if(bus.getBoundingBox().intersects(player.getBoundingBox()) ) {
				removeLife();
			}
		}
		for (Bike bike : GameData.bikes) {
			bike.update(delta);
			if(bike.getBoundingBox().intersects(player.getBoundingBox()) ) {
				removeLife();
			}
		}
		for (Racecar racecar : GameData.racecars) {
			racecar.update(delta);
			if(racecar.getBoundingBox().intersects(player.getBoundingBox()) ) {
				removeLife();
			}
		}
		for (Bulldozer bulldozer : GameData.bulldozers) {
			bulldozer.update(delta);
			if (bulldozer.pushZone().intersects(player.getBoundingBox())) {
				player.attach(bulldozer);
			} 
		}
		for (Log log : GameData.logs) {
			log.update(delta);
		}
		for (LongLog longlog : GameData.longLogs) {
			longlog.update(delta);
		}
		for (Turtle turtle : GameData.turtles) {
			turtle.update(delta);
		}
	}
	
	/** Check if the frog is on a rideable object.
	 */
	public void checkRide() { 
		safe = false;
		for (Log log : GameData.logs) {
			if (log.getBoundingBox().intersects(player.getBoundingBox())) {
				safe = true;
				player.attach(log);
			}
		}
		for (LongLog longLog : GameData.longLogs) {
			if (longLog.getBoundingBox().intersects(player.getBoundingBox())) {
				safe = true;
				player.attach(longLog);
			}
		}
		for (Turtle turtle : GameData.turtles) {
			if (turtle.getBoundingBox().intersects(player.getBoundingBox())) {
				if (turtle.isUnderwater()) {
					safe = false;
				} else {
					player.attach(turtle);
					safe = true;
				}
			}
		}
		
	}
	
	/** Check the state of the goals in the game.
	 * Keep track of how many goals have been reached.
	 * Kill player if a goal is entered twice.
	 * @throws SlickException
	 */
	public void checkGoal() throws SlickException {
		for (Goal goal: GameData.goals) {
			if (goal.getBoundingBox().intersects(player.getBoundingBox())) {
				safe=true;
				player.attach(null);
				if (goal.isFull()) {
					safe=false;
				} else {
					resetGame();
					goal.reached();
					fullGoals +=1;
				}
			}
		}
	}
	 
	/** Render all game objects in the game to reflect their current states.
	 * @param g The Slick graphics object, used for drawing.
	 */
	public void renderAll(Graphics g) {
		for (Bulldozer bulldozer: GameData.bulldozers) {
			bulldozer.render();
		}
		for (Bus bus: GameData.buses) {
			bus.render();
		}
		for (Bike bike: GameData.bikes) {
			bike.render();
		}
		for (Racecar racecar: GameData.racecars) {
			racecar.render();
		}
		for (Log log: GameData.logs) {
			log.render();
		}
		for (LongLog longlog: GameData.longLogs) {
			longlog.render();
		}
		for (Turtle turtle: GameData.turtles) {
			turtle.render();
		}
		for (Goal goal: GameData.goals) {
			if (goal.isFull()) {
				goal.render();
			}
		}
		for (Life life: lives) {
			life.render();
		}
		player.render();
		extraLife.render();
	}
	
	/** Removes a life.
	 * @throws SlickException.
	 */
	public static void removeLife() throws SlickException {
		livesRemaining -=1;
		lives.remove(livesRemaining);
		resetGame();
	}
	
	/** Check if all goals have been filled.
	 * @throws SlickException.
	 */
	public void checkEnd() throws SlickException {
		if (fullGoals==ALL_FILLED) {
			level +=1;
			game.reset();
			startGame();
		}
	}
	
	/** Reset the level.
	 * @throws SlickException.
	 */
	public static void resetGame() throws SlickException {
		player = new Frog(PLAYER_X, PLAYER_Y);
		player.attach(null);
	}
}