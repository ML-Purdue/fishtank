package control;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import environment.Engine;
import environment.Fish;
import environment.FishState;
import environment.WorldState;

public abstract class FishAI implements Runnable {
	public Vector<Fish> myFish = null;
    private Engine engine;
    public Color color = new Color(255, 100, 0);
    public final int controller_id;
    private static int id_max = 0;
    private final long firstFrame;
    public WorldState current;
    private long prev_seqID = 0;
    
    // Represents the number fish the AI will start with
    // Initial nutrients will be divided among all fish
    public int startFish;
    
    public FishAI (Engine engine, int startFish) {
    	this.startFish= startFish;
    	this.engine = engine;
    	this.myFish = new Vector<Fish>();
    	this.controller_id = id_max++;
    	firstFrame = engine.getCurrentStateID();
    }
    
    public void run() {
    	while (liveFish() > 0) {
    		// TODO: Figure out how to time this, so that we can break out if needed
    		
    		
    		current = engine.getState(prev_seqID);
    		prev_seqID = current.seqID;
    		synchronized(myFish) {
    			// Clean up dead fish
    			ArrayList<Fish> removeList = new ArrayList<Fish>();
    			for (Fish f : myFish) {
    				FishState fs = current.getState(f.id);
    				if (fs == null || !fs.isAlive()) {
    					removeList.add(f);
    					continue;
    				}
    			}
    			for (Fish f : removeList) {
    				myFish.remove(f);
    			}
    			
    			iterate();
    		}
    	}
    	System.out.println("Fish " + controller_id + " empty, ending execution");
    	engine.registerDeath(this);
    }
    
    protected abstract void iterate();
    
    public int liveFish() {
    	WorldState st = engine.getState(firstFrame);
    	int numFish = 0;
    	synchronized(myFish) {
    		for (Fish f : myFish) {
    			if (st.getState(f.id) != null && st.getState(f.id).isAlive()){
    				numFish++;
    			}
    		}
    	}
    	return numFish;
    }
}

