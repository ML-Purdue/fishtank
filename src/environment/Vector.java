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
    
    public Vector() {
    	this(0, 0);
    }
    
    public double length() {
    	return Math.sqrt(x*x + y*y);
    }
    
    public Vector normalize() {
    	double length = length();
    	return new Vector(x/length, y/length);
    }

    public Vector plus(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector minus(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public Vector times(double scalar) {
    	return new Vector(scalar * this.x, scalar * this.y);
    }

    public Vector divide(double scalar) {
    	if (scalar == 0) return new Vector();
    	else return new Vector(x / scalar, y / scalar);
    }

    public double dot(Vector other) {
        return this.x*other.x + this.y*other.y;
    }
    
    public String toString () {
    	return "(" + x + ", " + y + ")";
    }
}

