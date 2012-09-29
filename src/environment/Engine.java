 package environment;

import java.util.ArrayList;
import java.util.Random;

import control.CircleFish;
import control.FishAI;

public class Engine implements Runnable {
	public final RuleSet rules;
    private State backState;
    private State frontState;
    private Object stateLock;
    private ArrayList<FishAI> controllers;
    private Random rng;

    public Engine() {
    	this.rules = RuleSet.dflt_rules();
        frontState = new State(0);
        stateLock = new Object();
        controllers = new ArrayList<FishAI>();
        rng = new Random();
        //Add initial fish to the simulation
    }

    public State getState(long ID) {
        State rtn = null;

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
	    			Vector pos = f.getPosition();
	    			Vector dir = f.getRudderVector();
	    			double speed = f.getSpeed();
	    			double x = pos.x + speed * dir.x;
	    			double y = pos.y + speed * dir.y;
	
					// TODO: better collision handling
	    			if (x > rules.x_width || x < 0) {
	    				x = pos.x;
	    			}
	    			if (y > rules.y_width || y < 0) {
	    				y = pos.y;
	    			}
	    			System.out.println("Moving fish " + f.id + " to " + x + ", " + y);
	    			f.setPosition(x, y);
	    		}
	    	}
    	}
    }

    private void collideFish() {
    }

    private void spawnFish() {
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
	    	Fish f = new Fish(rules, 0, 0, 0);
	    	f.setPosition(rng.nextInt(rules.x_width - 150)+75, 
	    			rng.nextInt(rules.y_width - 150) + 75);
	    	ai.myFish.add(f);
    	}
    }

    public void run() {
        long numStates = 0;
        while(true) {
        	System.out.println("iteration - state id is " + numStates);
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            backState = new State(numStates++);

            //Calculate the next state from frontState into the backState
            moveFish();

            collideFish();

            spawnFish();

            //Push backState to be the new frontState
            flipStates();
        }
    }
}

