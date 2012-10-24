 package environment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import control.CircleFish;
import control.FishAI;

public class Engine implements Runnable {
	private WorldState backState;
	private WorldState frontState;
	private Object stateLock;
	private ArrayList<FishAI> controllers;
	private ArrayList<Fish> reproducers;
	private Random rng;

	public Engine() {
		frontState = new WorldState(0);
		stateLock = new Object();
		controllers = new ArrayList<FishAI>();
		rng = new Random();
		reproducers = new ArrayList<Fish>();
		//Add initial fish to the simulation
	}

	public WorldState getState(long ID) {
		WorldState rtn = null;

		//Busy-wait until a new state is available
		while(frontState.seqID <= ID){
			Thread.yield();
		}

		synchronized(stateLock) {
			rtn = frontState;
		}

		return rtn;
	}

	private void flipStates() {
		synchronized(stateLock) {
			frontState = backState;
		}
	}

	private void moveFish() {
		synchronized (controllers) {
			for (FishAI ai : controllers) {
				for (Fish f : ai.myFish) {
					synchronized (f.requested_state) {
						FishState old_fs = frontState.getState(f);
						FishState requested_fs = f.requested_state;
						Vector pos = old_fs.getPosition();
						Vector dir = requested_fs.getRudderVector();

						if (old_fs == null || requested_fs == null) {
							System.err.println("Oh noes!");
						}

						double speed = requested_fs.getSpeed();
						double x = pos.x + speed * dir.x;
						double y = pos.y + speed * dir.y;

						// TODO: better collision handling
		    			if (x > Rules.xWidth || x < 0) {
		    				x = pos.x;
		    			}
		    			if (y > Rules.yWidth || y < 0) {
		    				y = pos.y;
		    			}

		    			FishState new_fs = old_fs.clone();
		    			new_fs.position = new Vector(x, y);
		    			new_fs.heading = dir;
		    			new_fs.speed = speed;
		    			//System.out.println("Moving fish " + f.id + " to " + new_fs.getPosition() +
		    			//", old pos was " + pos + ", speed was " + speed + ", dir was " + dir);
		    			backState.fishStates.put(f, new_fs);
	    			}
	    		}
	    	}
    	}
    }

	private void collideFish() {
		synchronized(controllers) {
			for (FishState f1 : backState.fishStates.values()) {
				for (FishState f2 : backState.fishStates.values()) {
                    if(f1 == f2 || !f1.alive || !f2.alive) continue;

					double dist = Math.sqrt(Math.pow((f1.position.x - f2.position.x), 2)
							+ Math.pow((f1.position.y - f2.position.y), 2));
					if(dist < f1.getRadius() + f2.getRadius()){
						if(f1.nutrients > f2.nutrients){
							f2.alive = false;
							f1.nutrients += (f2.nutrients * 0.8);
						}else if(f2.nutrients > f1.nutrients){
							f1.alive = false;
							f2.nutrients += (f1.nutrients * 0.8);
						}else{
							if(Math.random() > 0.5){
								f2.alive = false;
								f1.nutrients += (f2.nutrients * 0.8);
							}else{
								f1.alive = false;
								f2.nutrients += (f1.nutrients * 0.8);
							}
						}
					}

				}
			}
		}
	}

    private void decayFish() {
    	for (FishState fs : backState.fishStates.values()) {
			// -1 per fish per round
    		// In the future, we might want to make this dependent on fish size
    		fs.nutrients -= 1;

    		// -1 per unit of speed
    		fs.nutrients -= (int) fs.speed;

    		if (fs.nutrients <= 0) {
    			fs.alive = false;
    		}
    	}
    }
    
    protected void requestRepro (Fish f) {
    	synchronized(reproducers) {
    		reproducers.add(f);
    	}
    }

    private void spawnFish() {
        synchronized(reproducers){
            for (Fish parent : reproducers) {
            	FishState parentState = backState.getState(parent);
            	backState.getState(parent).nutrients *= .4;		// Each baby fish gets 40% of the original food
            	
            	// Child fish should spawn behind the parent fish,
            	// facing the opposite direction (to prevent accidental eating)
            	FishState childState = parentState.clone();
            	double childX = parentState.position.x - 2 * parentState.heading.x * parentState.getRadius();
            	double childY = parentState.position.y - 2 * parentState.heading.y * parentState.getRadius();
            	childState.position = new Vector(childX, childY);
            	childState.heading = new Vector(-1 * parentState.heading.x, -1 * parentState.heading.y);
            	
            	Fish child = new Fish(parent.controller);
            	child.requested_state = childState;
                
                parent.controller.myFish.add(child);
                backState.fishStates.put(child, childState);
            }
            reproducers.clear();
        }
    }

    /* Temporary function - will need to remove later */
    public void add () {
    	synchronized (controllers) {
	    	if (controllers.isEmpty()) {
	    		FishAI ai = new CircleFish(this);
	    		controllers.add(ai);
	    		Thread ai_thread = new Thread(ai);
	    		ai_thread.start();
	    	}
	    	FishAI ai = controllers.get(0);
	    	FishState fs = new FishState();
	    	fs.position = new Vector(rng.nextInt(Rules.xWidth - 150)+75,
	    			rng.nextInt(Rules.yWidth - 150) + 75);
	    	fs.heading = new Vector(0, 0);
	    	fs.speed = 0;

    	}
    }

    public void run() {
        long iter_time = 0;
        long min_iter_time = 100000000L; //100ms

        long numStates = 0;

        //Add an initial fish
        add();

        while(true) {
            iter_time = System.nanoTime();

        	System.out.println("iteration - state id is " + numStates);
			backState = new WorldState(numStates++);

			//Calculate the next state from frontState into the backState
			moveFish();

            collideFish();

            decayFish();

			spawnFish();

			//Push backState to be the new frontState
			flipStates();

            iter_time = System.nanoTime() - iter_time;
            if(iter_time < min_iter_time){
                try {
                    Thread.sleep((min_iter_time - iter_time) / 1000000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
		}
	}
}

