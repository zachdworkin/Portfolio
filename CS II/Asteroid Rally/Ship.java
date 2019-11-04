public class Ship {
    private double dx;
    private double dy;
    private double angle;
    private Extent extent;
    /** constructs a ship */
    public Ship(double a, double b,double angle1){
        this.extent = new Extent(a,b,0.025);
        this.angle=angle;
    }

    public Extent getExtent() {
        return extent;
    }

    public void accelerate(double v) {
        dx+=v*Math.cos(angle);
        dy+=v*Math.sin(angle);
    }

    public void drift() {
        extent.move(dx,dy);
    }

    public void rotate(double v) {
        angle=angle+v;
    }

    public double getAngle() {
        return angle;
    }
}
