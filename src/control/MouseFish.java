package control;

import java.util.ArrayList;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.WorldState;

public class MouseFish extends FishAI {
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
		for (Fish f : myFish) {
			FishState fs = current.getState(f.id);
			f.setRudderDirection(current.mousePosition.x - fs.getPosition().x, current.mousePosition.y - fs.getPosition().y);
			f.setSpeed(current.mousePosition.minus(fs.getPosition()).length() / 100);
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
