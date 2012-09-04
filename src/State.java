import java.util.ArrayList;

public class State {
    public long seqID;
    public ArrayList<FishLocation> fishLocs;

    public double xDim;
    public double yDim;

    public State(long ID) {
        seqID = ID;

        fishLocs = new ArrayList<FishLocation>();
    }
}

