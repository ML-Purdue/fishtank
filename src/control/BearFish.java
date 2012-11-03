package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.Food;
import environment.Rules;
import environment.Vector;

public class BearFish extends FishAI {

	public BearFish(Engine engine) {
		super(engine, 1);
	}

	@Override
	protected void iterate() {
		ArrayList<Fish> removeList = new ArrayList<Fish>();
		for (Fish f : myFish) {
			FishState fs = current.getState(f.id);
			
			// Find the closest object in the world that is smaller than us
			double min_dist = 100000;
			Vector dest = null;
			for (FishState target : current.getFish()) {
				if (target.fish_id == fs.fish_id) continue;  // Ignore self
				if (target.getNutrients() > fs.getNutrients()) continue;  // Ignore bigger fish
				
				double dist = target.getPosition().minus(fs.getPosition()).length();
				if (dist < min_dist) {
					min_dist = dist;
					dest = target.getPosition();
				}
			}
			
			for (Food target : current.getFood()) {
				double dist = target.position.minus(fs.getPosition()).length();
				if (dist < min_dist) {
					min_dist = dist;
					dest = target.position;
				}
			}
			
			if (dest != null) {
				f.setRudderDirection(dest.minus(fs.getPosition()));
				f.setSpeed(Rules.MAX_SPEED);
			} else {
				f.setSpeed(0);
			}
			
		}
		

	}

}
