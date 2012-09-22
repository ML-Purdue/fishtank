package control;
import java.util.ArrayList;

import environment.Engine;
import environment.Fish;

public abstract class FishAI implements Runnable {
    public ArrayList<Fish> myFish = null;
    Engine engine;
    
    public FishAI (Engine engine) {
    	this.engine = engine;
    }
}

