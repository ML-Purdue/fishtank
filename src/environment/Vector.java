package environment;

/*
 * Note that vectors are immutable - if you want to change it, you'll need to
 * create an entirely new vector
 */
public class Vector {
    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector clone() {
    	return new Vector(x, y);
    }
    
    public Vector normalize() {
    	double len = x * x + y * y;
    	len = Math.sqrt(len);
    	return new Vector(x/len, y/len);
    }
    
    @Override
    public String toString () {
    	return "(" + x + ", " + y + ")";
    }
}

