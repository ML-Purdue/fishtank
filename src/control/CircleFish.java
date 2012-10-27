package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.Rules;
import environment.Vector;
import environment.WorldState;

public class CircleFish extends FishAI {
	//private HashMap<Fish, Vector> centers;

	public CircleFish(Engine engine) {
		super(engine, 3);
		//centers = new HashMap<Fish, Vector>();
	}

	@Override
	public void iterate() {
		WorldState prev = null;
		WorldState current = engine.getState(0);
		int speed = 0;
		boolean up = true;
		while(true) {  // TODO end condition
			prev = current;
			current = engine.getState(prev.seqID);
			
			ArrayList<Fish> removeList = new ArrayList<Fish>();
			for (Fish f : myFish) {
				FishState fs = current.getState(f);
				if (!fs.isAlive()) {
					removeList.add(f);
					continue;
				}
				
				f.setSpeed(speed);
				Vector heading = fs.getRudderVector();
				if (fs.getPosition().x > Rules.tankWidth - 10) {
					f.setRudderDirection(-1, 0);
				} else if (fs.getPosition().x < 10) {
					f.setRudderDirection(1, 0);
					
				} else if (!heading.equals(new Vector(1, 0)) && !heading.equals(new Vector(-1, 0))) {
					if (heading.x > 0) f.setRudderDirection(1, 0);
					else f.setRudderDirection(-1, 0);
				}
				
				// Reproduce
				if (fs.getNutrients() > 800) {
					System.out.println("Fish " + f.getID() + " requesting reproduction");
					f.reproduce();
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
