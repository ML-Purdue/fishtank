package environment;

public class Food {
	public Vector position;
	public double nutrients;
	
	public Food(Vector position, double nutrients) {
		this.position = position.clone();
		this.nutrients = nutrients;
	}
	
	public int getRadius() {
		return (int)Math.sqrt(nutrients);
	}
}
