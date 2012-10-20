package environment;

//////////////////////////////////////////////////////
//  A c-style struct for fish state information
//////////////////////////////////////////////////////

public class FishState {
	protected Vector position;
	protected Vector rudderDirection;
	protected double speed;
	protected double nutrients;
	protected boolean alive;
	protected int radius;
	
	public FishState() {
		this.position = new Vector(0, 0);
		this.rudderDirection = new Vector(1, 0);
		this.speed = 0;
		this.nutrients = 0;
		this.alive = true;
		this.radius = 10;
	}
	
	public Vector getPosition() {
		return position;
	}

	// accessor
	public double getRudderDegrees() {
		return Math.tan(rudderDirection.y / rudderDirection.x);
	}

	// accessor
	public Vector getRudderVector() {
		return rudderDirection;
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
		return radius;
	}
}
