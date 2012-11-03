package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.Rules;
import environment.WorldState;

public class MouseFish extends FishAI {
	WorldState current = null;
	int speed = 0;
	boolean up = true;
	private int i;

	public MouseFish(Engine engine) {
		super(engine, 3);
		i = 0;
		// centers = new HashMap<Fish, Vector>();
	}

	@Override
	public void iterate() {
		current = engine.getState(current != null ? current.seqID : 0);
		for (Fish f : myFish) {
			FishState fs = current.getState(f.id);
			f.setRudderDirection(current.mousePosition.x - fs.getPosition().x, current.mousePosition.y - fs.getPosition().y);
			f.setSpeed(Rules.MAX_SPEED * current.mousePosition.minus(fs.getPosition()).length() / 100);
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
