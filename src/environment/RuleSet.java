package environment;

public class RuleSet {
	public final int max_speed;
	public final int x_width;
	public final int y_width;
	
	public RuleSet (int x_width, int y_width, int max_speed) {
		this.x_width = x_width;
		this.y_width = y_width;
		this.max_speed = max_speed;
	}
	
	public static RuleSet dflt_rules() {
		return new RuleSet(2048, 2048, 5);
	}

}
