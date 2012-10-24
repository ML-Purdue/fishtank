package control;
import java.awt.Color;
import java.util.Vector;

import environment.Engine;
import environment.Fish;
import environment.WorldState;

public abstract class FishAI implements Runnable {
	public Vector<Fish> myFish = null;
    public Engine engine;
    public Color color = new Color(255, 100, 0);
    
    // Represents the number fish the AI will start with
    // Initial nutrients will be divided among all fish
    public int startFish;
    
    public FishAI (Engine engine, int startFish) {
    	this.startFish= startFish;
    	this.engine = engine;
    	this.myFish = new Vector<Fish>();
    }
    
    public void run() {
    	while (liveFish() > 0) {
    		// TODO: Figure out how to time this, so that we can break out if needed 
    		iterate();
    	}
    }
    
    protected abstract void iterate();
    
    private int liveFish() {
    	WorldState st = engine.getState(0);
    	int numFish = 0;
    	for (Fish f : myFish) {
    		if (st.getState(f).isAlive()) numFish++;
    	}
    	return numFish;
    }
}

