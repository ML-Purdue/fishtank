package environment;

import java.awt.Point;
import java.util.ArrayList;

public class PlantCell {
	protected Point pos;
	protected double nutrients;
	protected static PlantCell cells[][];
	public static final double threshold = 10;
	
	public PlantCell (int x, int y) {
		this.pos = new Point(x, y);
		this.nutrients = 0;
	}
	
	protected void grow() {
		if (nutrients < threshold) return;
		
		// Check that we can reproduce
		ArrayList<Point> targets = getNeighbors();
		if (targets.size() <= 0) return;
		
		// Choose a point to grow to
		// TODO: come up with a clever algorithm for this
		Point bud = targets.get(Engine.rng.nextInt(targets.size()));
		
		// Create a cell there
		cells[bud.x][bud.y] = new PlantCell(bud.x, bud.y) ;
	}
	
	private ArrayList<Point> getNeighbors() {
		ArrayList<Point> neighbors = new ArrayList<Point>();
		// Find adjacent points that exist
		if (pos.x > 0) {
			neighbors.add(new Point(pos.x-1, pos.y));
			if (pos.y > 0) neighbors.add(new Point(pos.x-1, pos.y-1));
			if (pos.y < Rules.tankHeight) neighbors.add(new Point(pos.x-1, pos.y+1));
		}
		if (pos.y > 0) neighbors.add(new Point(pos.x, pos.y-1));
		if (pos.y < Rules.tankHeight) neighbors.add(new Point(pos.x, pos.y+1));
		if (pos.x < Rules.tankWidth) {
			neighbors.add(new Point(pos.x+1, pos.y));
			if (pos.y > 0) neighbors.add(new Point(pos.x+1, pos.y-1));
			if (pos.y < Rules.tankHeight) neighbors.add(new Point(pos.x+1, pos.y+1));
		}
		
		// Remove ones that are full
		for (int i = 0; i < neighbors.size(); i++) {
			Point p = neighbors.get(i);
			if (cells[p.x][p.y] != null)
				neighbors.remove(i--);
		}
		return neighbors;
	}

}
