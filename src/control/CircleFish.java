package control;

import java.util.HashMap;

import environment.Engine;
import environment.State;
import environment.Fish;
import environment.Vector;

public class CircleFish extends FishAI {
	private HashMap<Fish, Vector> centers;

	public CircleFish(Engine engine) {
		super(engine);
		centers = new HashMap<Fish, Vector>();
	}

	@Override
	public void run() {
		State prev = null;
		State current = engine.getState(0);
		int speed = 0;
		boolean up = true;
		while(true) {  // TODO end condition
			prev = current;
			current = engine.getState(prev.seqID);
			
			for (Fish f : myFish) {
				Vector center = centers.get(f);
				if (center == null) {	// New fish
					center = new Vector(f.getPosition().x - 100, f.getPosition().y - 100);
					centers.put(f, center);
				}
				
				f.setSpeed(speed);
				if (f.getPosition().x > engine.rules.x_width - 10) {
					f.setRudderDirection(-1, 0);
				} else if (f.getPosition().x < 10) {
					f.setRudderDirection(1, 0);
				}
			}
			
			// Set the speed for the next round
			if (up) {
				speed++;
				if (speed > 10) {
					up = false;
				}
			} else {
				speed--;
				if (speed < -5) {
					up = true;
				}
			}
			
		}

	}

}
