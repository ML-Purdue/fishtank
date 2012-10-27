package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.Rules;
import environment.Vector;
import environment.WorldState;

public class RandomFish extends FishAI {
	//private HashMap<Fish, Vector> centers;
	private int i;
	public RandomFish(Engine engine) {
		super(engine, 3);
		i=0;
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
				if(i==10){
					int direction;
					if(Math.random()>.5)
						direction=1;
					else
						direction = -1;					
					f.setSpeed(Math.random()*5);
					f.setRudderDirection(Math.random()*direction, Math.random()*direction);
					i=0;
				}
				else
					i++;
				
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
