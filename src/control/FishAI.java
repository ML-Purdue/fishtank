package control;
import java.util.Vector;

import environment.Engine;
import environment.Fish;

public abstract class FishAI implements Runnable {
	public Vector<Fish> myFish = null;
    Engine engine;
    
    // Represents the number fish the AI will start with
    // Initial nutrients will be divided among all fish
    public static int startFish;
    
    public FishAI (Engine engine, int startFish) {
    	this.startFish= startFish;
    	this.engine = engine;
    	this.myFish = new Vector<Fish>();
    }
}

