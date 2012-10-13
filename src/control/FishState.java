package environment;

//////////////////////////////////////////////////////
//  A c-style struct for fish state information
//////////////////////////////////////////////////////

public class FishState {

	public RuleSet rules;
	public Vector rudderDirection;
	public double speed;
	public double nutrients;
	public boolean alive;
	public int radius;
	public int id;
    
        public static int maxID;
}
