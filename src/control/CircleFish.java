package control;

import java.util.HashMap;

import environment.Engine;
import environment.FishState;
import environment.WorldState;
import environment.Fish;
import environment.Vector;

public class CircleFish extends FishAI {
	//private HashMap<Fish, Vector> centers;

	public CircleFish(Engine engine) {
		super(engine);
		//centers = new HashMap<Fish, Vector>();
	}

	@Override
	public void run() {
		WorldState prev = null;
		WorldState current = engine.getState(0);
		int speed = 0;
		boolean up = true;
		while(true) {  // TODO end condition
			prev = current;
			current = engine.getState(prev.seqID);
			
			for (Fish f : myFish) {
				System.out.println("Handling fish " + f.getID() + " Speed is " + speed);
				FishState fs = current.get_state(f);
				
				f.setSpeed(speed);
				if (fs.getPosition().x > engine.rules.x_width - 10) {
					f.setRudderDirection(-1, 0);
				} else if (fs.getPosition().x < 10) {
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
