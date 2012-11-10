package environment;

public class AIStats {
	protected final long birthTick;
	protected long deathTick = 0;
	protected double maxNutrients = 0;
	protected double avgNutrients = 0;
	protected int maxFish = 0;
	protected double avgFish = 0;
	protected final String name;
	protected final int controller_id;
	
	public AIStats (long birthTick, String name, int controler_id) {
		this.birthTick = birthTick;
		this.name = name;
		this.controller_id = controler_id;
	}
}
