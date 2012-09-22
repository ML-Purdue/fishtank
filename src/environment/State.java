package environment;

import java.util.ArrayList;

public class State {
    public long seqID;
    public ArrayList<Fish> fishList;

    public double xDim;
    public double yDim;

    public State(long ID) {
        seqID = ID;

        fishList = new ArrayList<Fish>();
    }
}

