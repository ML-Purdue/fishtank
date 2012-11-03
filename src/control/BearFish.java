package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.Food;
import environment.Rules;
import environment.Vector;
import environment.WorldState;

public class BearFish extends FishAI {
	long prev_seqID = 0;

	public BearFish(Engine engine) {
		super(engine, 1);
	}

	@Override
	protected void iterate() {
		WorldState current = engine.getState(prev_seqID);
		prev_seqID = current.seqID;

		ArrayList<Fish> removeList = new ArrayList<Fish>();
		for (Fish f : myFish) {
			FishState fs = current.getState(f.id);
			
			// Clean up dead fish
			if (!fs.isAlive()) {
				removeList.add(f);
				continue;
			}
			
			// Find the closest object in the world that is smaller than us
			double min_dist = 100000;
			Vector attract = new Vector(), repel = new Vector();
			for (FishState target : current.getFish()) {
				if (target.fish_id == fs.fish_id) continue;  // Ignore self
				if (target.getNutrients() > fs.getNutrients()) continue;  // Ignore bigger fish
				Vector r = target.getPosition().minus(fs.getPosition());
				Vector rhat = r.normalize();
				double rmag = r.length();
				if (target.controller_id == fs.controller_id) {
					repel = repel.plus(rhat.negative().times(1000/(rmag*rmag))); // repel fish of same type
					continue;
				}
				
				if (rmag < min_dist) {
					min_dist = rmag;
					attract = rhat;
				}
			}
			
			for (Food target : current.getFood()) {
				Vector r = target.position.minus(fs.getPosition());
				Vector rhat = r.normalize();
				double rmag = r.length();
				if (rmag < min_dist) {
					min_dist = rmag;
					attract = rhat;
				}
			}
			
			f.setRudderDirection(attract.plus(repel));
			f.setSpeed(Rules.MAX_SPEED);
			
			if (fs.getNutrients() > 100) {
				f.reproduce();
			}
			
		}
		
		// Get rid of dead fish
		for (Fish f : removeList) {
			myFish.remove(f);
		}
		

	}

}
