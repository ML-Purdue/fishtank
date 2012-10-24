package control;
import java.util.Vector;

import environment.Engine;
import environment.Fish;

public abstract class FishAI implements Runnable {
	public Vector<Fish> myFish = null;
    Engine engine;
    
    public static int START_NUTRIENTS; 
    
    public FishAI (Engine engine) {
    	this.engine = engine;
    	this.myFish = new Vector<Fish>();
    }
}

