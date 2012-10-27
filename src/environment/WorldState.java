package environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Collection;

public class WorldState {
    public long seqID;
    // Maps fish IDs to FishStates
    protected java.util.Hashtable<Integer, FishState> fishStates;
    protected ArrayList<Food> food;

    public Collection<FishState> getFish() {
    	return fishStates.values();
    }
    
    public Set<Food> getFood() {
    	return new HashSet<Food>(food);
    }
    
    public FishState getState (int fish_id) {
    	return fishStates.get(fish_id);
    }

    public WorldState(long ID) {
        seqID = ID;

        food = new ArrayList<Food>();
        fishStates = new java.util.Hashtable<Integer, FishState>();
    }
}
