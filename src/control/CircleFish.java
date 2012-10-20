package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.WorldState;

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
			
			ArrayList<Fish> removeList = new ArrayList<Fish>();
			for (Fish f : myFish) {
				System.out.println("Handling fish " + f.getID() + " Speed is " + speed);
				FishState fs = current.get_state(f);
				if (!fs.isAlive()) {
					removeList.add(f);
					continue;
				}
				
				f.setSpeed(speed);
				if (fs.getPosition().x > engine.rules.x_width - 10) {
					f.setRudderDirection(-1, 0);
				} else if (fs.getPosition().x < 10) {
					f.setRudderDirection(1, 0);
				}
			}
			// Get rid of dead fish
			for (Fish f : removeList) {
				myFish.remove(f);
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
