package environment;

import java.util.Set;

public class WorldState {
    public long seqID;
    protected java.util.Hashtable<Fish, FishState> fish_states;

    public double xDim;
    public double yDim;
    
    public Set<Fish> get_fish() {
    	return fish_states.keySet();
    }
    
    public FishState get_state (Fish f) {
    	return fish_states.get(f);
    }

    public WorldState(long ID) {
        seqID = ID;

        fish_states = new java.util.Hashtable<Fish, FishState>();
    }
}

