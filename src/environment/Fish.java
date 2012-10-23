package environment;

public class Fish {
	public RuleSet rules;
	protected FishState requested_state;
	protected int id;
	public static int max_id;
	
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

	/* the usual methods */

	// constructor
	public Fish() {
		requested_state = new FishState();
		synchronized (requested_state) {
			this.rules = RuleSet.dflt_rules();
			id = ++max_id;
		}
	}

	// constructor
	public Fish(RuleSet rules, double rudderDirection, double speed) {
		requested_state = new FishState();
		synchronized (requested_state) {
			this.rules = rules;
			setRudderDirection(rudderDirection);
			setSpeed(speed);
			id = ++max_id;
		}
	}

	/* fish action methods */

	// degrees are standard math textbook: from the right side counterclockwise
	public FishCode setRudderDirection(double degrees) {
		Vector direction = new Vector(Math.cos(degrees * 2 * Math.PI / 360), Math.sin(degrees * 2 * Math.PI / 360));
		synchronized (requested_state) {
			requested_state.heading = direction;
		}
		return FishCode.OK;
	}

	// set rudder direction using the given two-dimensional vector
	public FishCode setRudderDirection(double x, double y) {
		synchronized(requested_state) {
			requested_state.heading = new Vector(x, y);
			requested_state.heading = requested_state.heading.normalize();
			return FishCode.OK;
		}
	}

	// set the fish's speed
	public FishCode setSpeed(double speed) {
		synchronized(requested_state) {
			if (speed > rules.max_speed) {
				requested_state.speed = rules.max_speed;
				return FishCode.OUT_OF_BOUNDS;
			} else if (speed < 0) {
				requested_state.speed = 0;
				return FishCode.OUT_OF_BOUNDS;
			} else {
				requested_state.speed = speed;
				return FishCode.OK;
			}
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
	
	public int getID () {
		return id;
	}
}

