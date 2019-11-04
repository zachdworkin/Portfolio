public class Extent {
    private double x;
    private double y;
    private double radius;

    public Extent(double a, double b, double rad){
        this.x=a;
        this.y=b;
        this.radius=rad;
    }
    /** returns the value of x*/
    public double getX(){
        return x;
    }
    /** returns the value of y*/
    public double getY(){
        return y;
    }
    /** returns the value of the Radius*/
    public double getRadius(){
        return radius;
    }

    /** moves x and y*/
    public void move(double a, double b){
        x=x+a;
        while(x>1.0){//makes it appear on the other side if it wraps around
            x=x-1.0;
        }
        while(x<0.0){//makes it appear on the other side if it wraps around
            x=x+1.0;
        }
        y=y+b;
        while(y>1.0){//makes it appear on the other side if it wraps around
            y=y-1.0;
        }
        while(y<0.0){//makes it appear on the other side if it wraps around
            y=y+1.0;
        }
    }

    /** calculates distance to another object using its dimensions (held in another extent) */
    public double distanceTo(Extent b) {
        double distance = Math.sqrt((this.x - b.x) * (this.x - b.x) + (this.y - b.y)*(this.y - b.y));//distance formula
        return distance;
    }

    /** returns true if object overlaps another, otherwise returns false */
    public boolean overlaps(Extent overlap) {
        if ((this.radius + overlap.radius) >= distanceTo(overlap)) {//checks to see if the distance between the two points is greater than both of the radius combined
            return true;
        }
        return false;//returns false if they are not overlapping
    }
}
