package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.WorldState;

public class RandomFish extends FishAI {
	// private HashMap<Fish, Vector> centers;
	private int i;

	public RandomFish(Engine engine) {
		super(engine, 3);
		i = 0;
		// centers = new HashMap<Fish, Vector>();
	}

	@Override
	public void iterate() {
		WorldState prev = null;
		WorldState current = engine.getState(0);
		int speed = 0;
		boolean up = true;
		while (true) {
			prev = current;
			current = engine.getState(prev.seqID);
			ArrayList<Fish> removeList = new ArrayList<Fish>();
			for (Fish f : myFish) {
				FishState fs = current.getState(f.id);
				if (!fs.isAlive()) {
					removeList.add(f);
					continue;
				}
				if (i == 10) {
					int directionX, directionY;
					if (Math.random() > .5)
						directionX = 1;
					else
						directionX = -1;
					if (Math.random() > .5)
						directionY = -1;
					else
						directionY = 1;
					f.setSpeed(Math.random() * 5);
					f.setRudderDirection(Math.toDegrees(Math.atan2(
							Math.random() * directionY, Math.random()
									* directionX)));
					i = 0;
				} else
					i++;

				// Reproduce
				if (fs.getNutrients() > 800) {

					System.out.println("Fish " + f.id
							+ " requesting reproduction");
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
