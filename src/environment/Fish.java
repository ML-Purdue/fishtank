package environment;
public class Fish {
    private Vector position;
    private Vector rudderDirection;  // Will always be normalized
    private double speed;
	private double nutrients;
    private boolean alive;
    public int radius;
    
    public enum FishCode {
		OK,
		DEAD,
		TOO_SMALL,
		TOO_HUNGRY,
		OUT_OF_BOUNDS,
		COLLISION
	}
    
    public Fish() {
    	radius = 10;
    }
    
    public Fish(double rudderDirection, double speed, int radius) {
    	this.setRudderDirection(rudderDirection);
    	this.speed = speed;
    	this.radius = radius;
    }

    /* Actions */
    /* Degrees are standard math textbook: from the right side counterclockwise */
    public FishCode setRudderDirection(double degrees) {
    	Vector direction = new Vector(1, Math.tan(degrees));
    	direction = direction.normalize();    	
        rudderDirection = direction;
        return FishCode.OK;
    }
    
    public FishCode setRudderDirection(double x, double y) {
    	rudderDirection = new Vector(x, y);
    	rudderDirection.normalize();
    	return FishCode.OK;
    }

    public FishCode setSpeed(double speed) {
        this.speed = speed;
        return FishCode.OK;
    } 
    
    public FishCode reproduce() {
    	//TODO
    	return FishCode.OK;
    }
    
    public FishCode eat() {
    	//TODO
    	return FishCode.OK;
    }
    
    /* Senses */
    public Vector getPosition() {
		return position;
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
	
	public double getRudderDirection() {
        return Math.tan(rudderDirection.y / rudderDirection.x);
    }
	
	public Vector getRudderVector() {
		return rudderDirection;
	}

    public double getSpeed() {
        return speed;
    } 
	
    /* Engine controls */
    protected void setPosition(Vector position) {
		this.position = position;
	}

	protected void setNutrients(double nutrients) {
		this.nutrients = nutrients;
	}

	protected void setAlive(boolean alive) {
		this.alive = alive;
	}
}

