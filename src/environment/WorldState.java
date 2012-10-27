package environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class WorldState {
    public long seqID;
    protected Hashtable<Fish, FishState> fishStates;
    protected ArrayList<Food> food;

    // TODO: this is a security hole!
    public Set<Fish> getFish() {
    	return fishStates.keySet();
    }
    
    public Set<Food> getFood() {
    	return new HashSet<Food>(food);
    }
    
    public FishState getState (Fish f) {
    	return fishStates.get(f);
    }

    public WorldState(long ID) {
        seqID = ID;

        fishStates = new Hashtable<Fish, FishState>();
        food = new ArrayList<Food>();
    }
}

