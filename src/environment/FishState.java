package environment;

//////////////////////////////////////////////////////
//  A c-style struct for fish state information
//////////////////////////////////////////////////////

public class FishState {
	protected Vector position;
	protected Vector heading;
	protected double speed;
	protected double nutrients;
	protected boolean alive;
	public final int fish_id;
	
	public FishState(int fish_id) {
		this.position = new Vector(0, 0);
		this.heading = new Vector(1, 0);
		this.speed = 0;
		this.nutrients = 1000;
		this.alive = true;
		this.fish_id = fish_id;
	}
	
	// Copy a fish exactly, keeping the same fish id (for state changes).
	public FishState clone() {
		FishState fs = new FishState(fish_id);
		fs.position = position;
		fs.heading = heading;
		fs.speed = speed;
		fs.nutrients = nutrients;
		fs.alive = alive;
		return fs;
	}

	// Copy a fish but use a new id (for reproduction).
	public FishState copy(int fish_id) {
		FishState fs = new FishState(fish_id);
		fs.position = position;
		fs.heading = heading;
		fs.speed = speed;
		fs.nutrients = nutrients;
		fs.alive = alive;
		return fs;
	}
	
	public Vector getPosition() {
		return position;
	}

	public double getRudderDegrees() {
		return Math.tan(heading.y / heading.x);
	}

	public Vector getRudderVector() {
		return heading;
	}

	public double getSpeed() {
		return speed;
	}

	public double getNutrients() {
		return nutrients;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getRadius() {
		return (int)Math.sqrt(nutrients);
	}
}
