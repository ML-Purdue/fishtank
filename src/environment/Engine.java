 package environment;

import java.util.ArrayList;

import control.FishAI;

public class Engine implements Runnable {
    private State backState;
    private State frontState;
    private Object stateLock;
    private ArrayList<FishAI> controllers;

    public Engine() {
        frontState = new State(0);
        stateLock = new Object();
        //Add initial fish to the simulation
    }

    public State getState() {
        State rtn = null;

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
    	for (FishAI ai : controllers) {
    		for (Fish f : ai.myFish) {
    			Vector pos = f.getPosition();
    			Vector dir = f.getRudderVector();
    			double speed = f.getSpeed();
    			double x = pos.x + speed * dir.x;
    			double y = pos.y + speed * dir.y;
    			f.setPosition(new Vector(x, y));
    		}
    	}
    	/*
        //for demonstration purposes only...
        for(int i = 0; i < frontState.fishLocs.size(); i++){
            FishLocation cur = frontState.fishLocs.get(i);
            if(cur.fish.getRudderDirection() > 1) {
                backState.fishLocs.add(new FishLocation(cur.fish, new Vector(cur.position.x + 1, cur.position.y + 1)));
            } else if(cur.fish.getRudderDirection() > 0) {
                backState.fishLocs.add(new FishLocation(cur.fish, new Vector(cur.position.x + 1, cur.position.y - 1)));
            } else if(cur.fish.getRudderDirection() > -1) {
                backState.fishLocs.add(new FishLocation(cur.fish, new Vector(cur.position.x - 1, cur.position.y + 1)));
            } else {
                backState.fishLocs.add(new FishLocation(cur.fish, new Vector(cur.position.x - 1, cur.position.y - 1)));
            }
        }
        */
    }

    private void collideFish() {
    }

    private void spawnFish() {
    }

    public void run() {
        long numStates = 0;
        while(true) {
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

