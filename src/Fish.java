public class Fish {
    private double rudderDirection;
    private double speed;
    public int radius;
    
    public Fish() {
    	radius = 10;
    }
    
    public Fish(double rudderDirection, double speed, int radius) {
    	this.rudderDirection = rudderDirection;
    	this.speed = speed;
    	this.radius = radius;
    }

    public double getRudderDirection() {
        return rudderDirection;
    }

    public double getSpeed() {
        return speed;
    } 

    public void setRudderDirection(double direction) {
        rudderDirection = direction;
    } 

    public void setSpeed(double speed) {
        this.speed = speed;
    } 
}

