package control;

import environment.Engine;
import environment.State;

public class CircleFish extends FishAI {

	public CircleFish(Engine engine) {
		super(engine);
	}

	@Override
	public void run() {
		State prev = null;
		State current = null;
		while(true) {  // TODO end condition
			prev = current;
			current = engine.getState();
			if (current.seqID == prev.seqID) {
				Thread.yield();
				continue;
			}
			
		}

	}

}
