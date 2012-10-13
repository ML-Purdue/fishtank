package environment;
public class Fish {
	private FishState requested_state;
	private final RuleSet rules;
    private Vector rudderDirection;  // Will always be normalized
    private double speed;
	private double nutrients;
    private boolean alive;
    public int radius;
    public int id;
    public static int max_id = 0;
    
    public enum FishCode {
		OK,
		DEAD,
		TOO_SMALL,
		TOO_HUNGRY,
		OUT_OF_BOUNDS,
		COLLISION,
		NOT_DEFINED
	}
    
    public Fish() {
    	this.rules = RuleSet.dflt_rules();
    	radius = 10;
    	this.id = ++max_id;
    }
    
    public Fish(RuleSet rules, double rudderDirection, double speed, int radius) {
    	this.rules = rules;
    	this.setRudderDirection(rudderDirection);
    	this.speed = speed;
    	this.radius = radius;
    	this.id = ++max_id;
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
    	rudderDirection = rudderDirection.normalize();
    	return FishCode.OK;
    }

    public FishCode setSpeed(double speed) {
    	if (speed > rules.max_speed) {
    		this.speed = rules.max_speed;
    		return FishCode.OUT_OF_BOUNDS;
    	} else if (speed < 0) {
    		this.speed = 0;
    		return FishCode.OUT_OF_BOUNDS;
    	} else {
    		this.speed = speed;
    		return FishCode.OK;
    	}
    } 
    
    public FishCode reproduce() {
    	//TODO
    	return FishCode.NOT_DEFINED;
    }
    
    public FishCode eat() {
    	//TODO
    	return FishCode.NOT_DEFINED;
    }
    
    /* Senses */
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

	protected void setNutrients(double nutrients) {
		this.nutrients = nutrients;
	}

	protected void setAlive(boolean alive) {
		this.alive = alive;
	}
}

