package environment;

public class Rules {
	public static final int xWidth = 1024;
	public static final int yWidth = 1024;
	public static final int minFish = 15;
	public static final double startingNutrients = 2000.0;
	
	public static final double TIME_DECAY = .05;
	public static final double SPEED_DECAY = .1;
	public static final double SIZE_DECAY = .0003;
	
	public static final double MAX_NUTRIENTS = startingNutrients;
	public static final double MAX_SPEED = 5; 
	
	public static double decay(FishState fs) {
		double decay = TIME_DECAY;
		decay += SPEED_DECAY * fs.getSpeed();
		decay += SIZE_DECAY * fs.getNutrients();
		return decay;
	}
	
	public static double maxSpeed(FishState fs) {
		// TODO: make this depend of fish size
		return MAX_SPEED;
	}
}
