package environment;

import java.util.Collection;
import java.util.TreeMap;
import java.util.Set;

public class WorldState {
    public long seqID;
    
    // Maps fish IDs to FishStates
    protected java.util.Hashtable<Integer, FishState> fishStates;

    public Collection<FishState> getFish() {
    	return fishStates.values();
    }
    
    public FishState getState (int fish_id) {
    	return fishStates.get(fish_id);
    }

    public WorldState(long ID) {
        seqID = ID;

        fishStates = new java.util.Hashtable<Integer, FishState>();
    }
}