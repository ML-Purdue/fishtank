package environment;

import java.util.Set;

public class WorldState {
    public long seqID;
    protected java.util.Hashtable<Fish, Vector> fish_locations;

    public double xDim;
    public double yDim;
    
    public Set<Fish> get_fish() {
    	return fish_locations.keySet();
    }
    
    public Vector get_location(Fish f) {
    	return fish_locations.get(f);
    }

    public WorldState(long ID) {
        seqID = ID;

        fish_locations = new java.util.Hashtable<Fish, Vector>();
    }
}

