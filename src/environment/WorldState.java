package environment;

import java.util.TreeMap;
import java.util.Set;

public class WorldState {
    public long seqID;
    protected java.util.Hashtable<Fish, FishState> fishStates;

    // TODO: this is a security hole!
    public Set<Fish> getFish() {
    	return fishStates.keySet();
    }
    
    public FishState getState (Fish f) {
    	return fishStates.get(f);
    }

    public WorldState(long ID) {
        seqID = ID;

        fishStates = new java.util.Hashtable<Fish, FishState>();
    }
}

