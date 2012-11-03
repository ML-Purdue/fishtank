package environment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class WorldState {
    public long seqID;
    // Maps fish IDs to FishStates
    protected java.util.Hashtable<Integer, FishState> fishStates;
    protected ArrayList<Food> food;
    
    // Leader stats for the visualizer
    protected int numFish;
    protected int numControllers;
    protected int maxFish;
    protected double maxNutrients;
    // TODO security problem
    public Vector mousePosition;

    public Collection<FishState> getFish() {
    	return fishStates.values();
    }
    
    public Set<Food> getFood() {
    	return new HashSet<Food>(food);
    }
    
    public FishState getState (int fish_id) {
    	return fishStates.get(fish_id);
    }
 
    public int getNumFish() {
		return numFish;
	}
    
    public int getNumControllers() {
    	return numControllers;
    }

	public int getMaxFish() {
		return maxFish;
	}

	public double getMaxNutrients() {
		return maxNutrients;
	}

	public WorldState(long ID) {
        seqID = ID;

        food = new ArrayList<Food>();
        fishStates = new java.util.Hashtable<Integer, FishState>();
    }
}
