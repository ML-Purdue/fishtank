package environment;

import control.FishAI;

public class Fish {
	protected FishState requested_state;
	protected int id;
	public static int max_id;
	protected FishAI controller;
	
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
	public Fish(FishAI controller) {
		this.controller = controller;
		requested_state = new FishState();
		id = ++max_id;
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
			if (speed > Rules.maxSpeed) {
				requested_state.speed = Rules.maxSpeed;
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

