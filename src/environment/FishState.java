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
	
	public FishState() {
		this.position = new Vector(0, 0);
		this.heading = new Vector(1, 0);
		this.speed = 0;
		this.nutrients = 1000;
		this.alive = true;
	}
	
	public FishState clone() {
		FishState fs = new FishState();
		fs.position = position.clone();
		fs.heading = heading.clone();
		fs.speed = speed;
		fs.nutrients = nutrients;
		fs.alive = alive;
		return fs;
	}
	
	public Vector getPosition() {
		return position;
	}

	// accessor
	public double getRudderDegrees() {
		return Math.tan(heading.y / heading.x);
	}

	// accessor
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
