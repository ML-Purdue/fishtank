package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.Rules;
import environment.Vector;

public class RandomFish extends FishAI {
	int speed = 0;
	boolean up = true;
	int iterate;

	public RandomFish(Engine engine) {
		super(engine, 3);
		iterate = 0;
	}

	public void iterate() {
		ArrayList<Fish> removeList = new ArrayList<Fish>();
		for (Fish f : myFish) {
			FishState fs = current.getState(f.id);
			int directionX, directionY;
			if (Math.random() > .5)
				directionX = 1;
			else
				directionX = -1;
			if (Math.random() > .5)
				directionY = -1;
			else
				directionY = 1;
			if (iterate == 10) {
				f.setRudderDirection(Math.random() * directionX, Math.random()
						* directionY);
				f.setSpeed(Math.random() * Rules.MAX_SPEED);

			}
			if (fs.getNutrients() > Rules.MAX_NUTRIENTS-10) {
				System.out.println("Fish " + f.id + " requesting reproduction");
				f.reproduce();
			}
		}
		if (iterate == 10)
			iterate = -1;
		iterate++;

	}

}