package environment;

import FishState.java;

public class Fish {
    
    public enum FishCode {
		OK,
		DEAD,
		TOO_SMALL,
		TOO_HUNGRY,
		OUT_OF_BOUNDS,
		COLLISION,
		NOT_DEFINED
    }

    /* member variables */
    private FishState fishState;
	
    /* the usual methods */

    // constructor
    public Fish() {
	fishState.rules = RuleSet.dflt_rules();
	fishState.radius = 10;
	fishState.id = ++FishState.max_id;
    }
    
    // constructor
    public Fish(RuleSet rules, double rudderDirection, double speed, int radius) {
        fishState.rules = rules;
	fishState.rudderDirection = rudderDirection;
	fishState.speed = speed;
	fishState.radius = radius;
	fishState.id = ++FishState.maxID;
    }

    /* fish action methods */
   
    // degrees are standard math textbook: from the right side counterclockwise
    public FishCode setRudderDirection(double degrees) {
    	Vector direction = new Vector(1, Math.tan(degrees));
    	direction = direction.normalize();    	
        fishState.rudderDirection = direction;
        return FishState.FishCode.OK;
    }
    
    // set rudder direction using the given two-dimensional vector
    public FishCode setRudderDirection(double x, double y) {
    	fishState.rudderDirection = new Vector(x, y);
    	fishState.rudderDirection = rudderDirection.normalize();
    	return FishState.FishCode.OK;
    }

    // set the fish's speed
    public FishCode setSpeed(double speed) {
    	if (speed > rules.max_speed) {
    		fishState.speed = fishState.rules.max_speed;
    		return FishState.FishCode.OUT_OF_BOUNDS;
    	} else if (speed < 0) {
    		fishState.speed = 0;
    		return FishState.FishCode.OUT_OF_BOUNDS;
    	} else {
    		fishState.speed = speed;
    		return FishState.FishCode.OK;
    	}
    } 
    
    // reproduce the fish
    public FishCode reproduce() {
    	//TODO
    	return FishCode.NOT_DEFINED;
    }
    
    // let the fish eat
    public FishCode eat() {
    	//TODO
    	return FishCode.NOT_DEFINED;
    }
    
    /* fish sense methods */

    // accessor
    public double getNutrients() {
	return fishState.nutrients;
    }

    // accessor
    public boolean isAlive() {
	return fishState.alive;
    }

    // accessor
    public int getRadius() {
	return fishState.radius;
    }
	
    // accessor
    public double getRudderDirection() {
        return Math.tan(fishState.rudderDirection.y / fishState.rudderDirection.x);
    }
	
    // accessor
    public Vector getRudderVector() {
	return fishState.rudderDirection;
    }

    // accessor
    public double getSpeed() {
        return fishState.speed;
    } 
	
    /* engine control methods */

    // mutator
    protected void setNutrients(double nutrients) {
	fishState.nutrients = nutrients;
    }

    // mutator
    protected void setAlive(boolean alive) {
	fishState.alive = alive;
    }
}

